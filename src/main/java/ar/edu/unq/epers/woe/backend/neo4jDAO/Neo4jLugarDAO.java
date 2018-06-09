package ar.edu.unq.epers.woe.backend.neo4jDAO;

import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;

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
}
