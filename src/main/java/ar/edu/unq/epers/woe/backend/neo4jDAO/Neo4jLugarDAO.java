package ar.edu.unq.epers.woe.backend.neo4jDAO;

import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import org.neo4j.driver.v1.*;
import java.util.ArrayList;
import java.util.List;

public class Neo4jLugarDAO {

    private Driver driver;

    public Neo4jLugarDAO() {
        this.driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "qwerty" ) );
    }

    public void create(Lugar lugar) {
        Session session = this.driver.session();
        try {
            String query = "MERGE (n:Lugar { nombre: {elNombre} }) SET n.nombre = {elNombre}";
            session.run(query, Values.parameters("elNombre", lugar.getNombre()));
        } finally {
            session.close();
        }
    }

    public Boolean existeLugar(String nombreLugar) {
        Session session = this.driver.session();
        try {
            String query = "MATCH (l:Lugar { nombre: {elNombre} }) " +
                           "RETURN l";
            StatementResult result = session.run(query, Values.parameters("elNombre", nombreLugar));
            return result.list().size() == 1;
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
            String query = "MATCH (p:Lugar { nombre: {elNombreP} }) " +
                           "MATCH (l:Lugar { nombre: {elNombreL} }) " +
                           "MERGE (p)-[:conectadoCon {tipoCamino: {tipoCamino}}]->(l)";
            session.run(query, Values.parameters("elNombreP", lugarPartida,
                    "elNombreL", lugarLlegada, "tipoCamino", tipoCamino));
        } finally {
            session.close();
        }
    }

    public List<String> conectadosCon(String nombreLugar, String tipoCamino) {
        Session session = this.driver.session();
        try {
            List<String> res = new ArrayList<String>();
            String query = "MATCH(l:Lugar)-[r:conectadoCon]->(lugar) " +
                           "WHERE l.nombre = {elNombre} and r.tipoCamino = {tipoCamino} " +
                           "RETURN lugar.nombre";
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
}
