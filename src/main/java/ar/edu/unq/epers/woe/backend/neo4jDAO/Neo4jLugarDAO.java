package ar.edu.unq.epers.woe.backend.neo4jDAO;

import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import org.neo4j.driver.v1.*;
import java.util.ArrayList;
import java.util.List;

public class Neo4jLugarDAO {

    private Driver driver;
    private String labelNombre;
    private String labelRelacion;
    private String labelTipoCamino;
    private String labelCostoCamino;
    private String labelTipoNodo;

    public Neo4jLugarDAO() {
        this.driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "qwerty" ) );
        this.labelNombre = "nombre";
        this.labelRelacion = "conectadoCon";
        this.labelTipoCamino = "tipoCamino";
        this.labelCostoCamino = "costoCamino";
        this.labelTipoNodo = "Lugar";
    }

    public void create(Lugar lugar) {
        Session session = this.driver.session();
        if(existeLugar(lugar.getNombre())) {
        	throw new RuntimeException("Ya existe Lugar con ese nombre");
        }
        try {
            String query = "MERGE (n:%s { nombre: {elNombre} }) SET n.%s = {elNombre}";
            query = String.format(query, this.labelTipoNodo, this.labelNombre);
            session.run(query, Values.parameters("elNombre", lugar.getNombre()));
        } finally {
            session.close();
        }
    }

    public Boolean existeLugar(String nombreLugar) {
        Session session = this.driver.session();
        try {
            String query = "MATCH (l:%s { %s: {elNombre} }) " +
                           "RETURN l";
            query = String.format(query, this.labelTipoNodo, this.labelNombre);
            StatementResult result = session.run(query, Values.parameters("elNombre", nombreLugar));
            return result.list().size() == 1;
        } finally {
            session.close();
        }
    }

    public Boolean existeCaminoEntre(String lugarPartida, String lugarLlegada) {
        Session session = this.driver.session();
        try {
            String query = "MATCH(l:%s {%s: {elNombreP}})-[:%s*]->(d:%s {%s: {elNombreL}}) " +
                           "RETURN d";
            query = String.format(query, this.labelTipoNodo, this.labelNombre, this.labelRelacion, this.labelTipoNodo, this.labelNombre);
            StatementResult result = session.run(query, Values.parameters("elNombreP", lugarPartida,
                                                 "elNombreL", lugarLlegada));
            return result.list().size() >= 1;
        } finally {
            session.close();
        }
    }

    public void eliminarDatos() {
        Session session = this.driver.session();
        try {
            String query = "MATCH (n) DETACH DELETE n";
            session.run(query);
        } finally {
            session.close();
        }
    }

    public void crearRelacionConectadoCon(String lugarPartida, String lugarLlegada, String tipoCamino) {
        Session session = this.driver.session();
        try {
            Integer costoCamino = null;
            switch(tipoCamino) {
                case "terrestre": costoCamino = 1; break;
                case "maritimo": costoCamino = 2; break;
                case "aereo": costoCamino = 5; break;
            }
            String query = "MATCH (p:%s { %s: {elNombreP} }) " +
                           "MATCH (l:%s { %s: {elNombreL} }) " +
                           "MERGE (p)-[:%s {%s: {tipoCamino}, %s: {costoCamino}}]->(l)";
            query = String.format(query, this.labelTipoNodo, this.labelNombre, this.labelTipoNodo, this.labelNombre,
                                  this.labelRelacion, this.labelTipoCamino, this.labelCostoCamino);
            session.run(query, Values.parameters("elNombreP", lugarPartida,
                    "elNombreL", lugarLlegada, "tipoCamino", tipoCamino, "costoCamino", costoCamino));
        } finally {
            session.close();
        }
    }

    public List<String> conectadosCon(String nombreLugar, String tipoCamino) {
        Session session = this.driver.session();
        try {
            List<String> res = new ArrayList<String>();
            String query = "MATCH(l:%s)-[r:%s]->(lugar) " +
                           "WHERE l.%s = {elNombre} and r.%s = {tipoCamino} " +
                           "RETURN lugar.%s";
            query = String.format(query, this.labelTipoNodo, this.labelRelacion, this.labelNombre,  this.labelTipoCamino, this.labelNombre);
            StatementResult result = session.run(query, Values.parameters("elNombre", nombreLugar,
                                                 "tipoCamino", tipoCamino));
            for(Record r : result.list()) {
                res.add(r.get(0).asString());
            }
            return res;
        } finally {
            session.close();
        }
    }

    public Integer costoRutaMasCorta(String lugarPartida, String lugarLlegada) {
        Session session = this.driver.session();
        try {
            String query = "MATCH (p:%s { %s: {elNombreP} }) " +
                           "MATCH (l:%s { %s: {elNombreL} }) " +
                           "MATCH ds=shortestPath((p)-[:%s*]->(l)) " +
                           "RETURN reduce(costoTotal=0, rels in relationships(ds) |  costoTotal+rels.%s)";
            query = String.format(query, this.labelTipoNodo, this.labelNombre,  this.labelTipoNodo, this.labelNombre,
                                  this.labelRelacion, this.labelCostoCamino);
            return session.run(query, Values.parameters("elNombreP", lugarPartida,
                               "elNombreL", lugarLlegada)).next().get(0).asInt();
        } finally {
            session.close();
        }
    }

    public Integer costoRutaMasBarata(String lugarPartida, String lugarLlegada) {
        Session session = this.driver.session();
        try {
            String query = "MATCH (p:%s { %s: {elNombreP} }) " +
                           "MATCH (l:%s { %s: {elNombreL} }) " +
                           "MATCH ds=(p)-[:%s*]->(l) " +
                           "RETURN reduce(costoTotal=0, r in relationships(ds) | costoTotal+r.%s) AS costoTotal " +
                           "order by costoTotal limit 1";
            query = String.format(query, this.labelTipoNodo, this.labelNombre,  this.labelTipoNodo, this.labelNombre,
                                  this.labelRelacion, this.labelCostoCamino);
            return session.run(query, Values.parameters("elNombreP", lugarPartida,
                               "elNombreL", lugarLlegada)).next().get(0).asInt();
        } finally {
            session.close();
        }
    }

	public List<String> conectadosCon(String nombreLugar) {
        Session session = this.driver.session();
        try {
            List<String> res = new ArrayList<String>();
            String query = "MATCH(l:%s)-[:%s]->(lugar) " +
                           "WHERE l.%s = {elNombre} " +
                           "RETURN lugar.%s";
            query = String.format(query, this.labelTipoNodo, this.labelRelacion, this.labelNombre, this.labelNombre);
            StatementResult result = session.run(query, Values.parameters("elNombre", nombreLugar) );
            for(Record r : result.list()) {
                res.add(r.get(0).asString());
            }
            return res;
        } finally {
            session.close();
        }
	}
}
