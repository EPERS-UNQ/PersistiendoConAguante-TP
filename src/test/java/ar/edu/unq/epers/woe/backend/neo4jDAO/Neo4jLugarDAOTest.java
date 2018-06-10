package ar.edu.unq.epers.woe.backend.neo4jDAO;

import ar.edu.unq.epers.woe.backend.model.lugar.Gimnasio;
import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;
import ar.edu.unq.epers.woe.backend.service.data.ServiciosDB;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Neo4jLugarDAOTest {

    private Neo4jLugarDAO n4jl = new Neo4jLugarDAO();
    private ServiciosDB dbServ = new ServiciosDB();

    @Before
    public void crearModelo() {
        this.dbServ.eliminarDatosNeo4j();
    }

    @Test
    public void alCrearNuevoLugarSeGuardaBien() {
        Tienda t = new Tienda("tie0");
        Gimnasio g = new Gimnasio("gim0");
        this.n4jl.create(t);
        this.n4jl.create(g);
        assertTrue(this.n4jl.existeLugar(t.getNombre()));
        assertTrue(this.n4jl.existeLugar(g.getNombre()));
    }

    @Test
    public void seObtieneNombreDeLugaresConectados() {
        Tienda t = new Tienda("tie1");
        Tienda t1 = new Tienda("tie2");
        Gimnasio g = new Gimnasio("gim0");
        Gimnasio g1 = new Gimnasio("gim0");
        String tipoCamino = "terrestre";
        String tipoCamino1 = "maritimo";
        this.n4jl.create(t);
        this.n4jl.create(t1);
        this.n4jl.create(g);
        this.n4jl.create(g1);
        this.n4jl.crearRelacionConectadoCon(t.getNombre(), t1.getNombre(), tipoCamino);
        this.n4jl.crearRelacionConectadoCon(t.getNombre(), g.getNombre(), tipoCamino);
        this.n4jl.crearRelacionConectadoCon(t1.getNombre(), g1.getNombre(), tipoCamino1);
        assertTrue(this.n4jl.conectadosCon(t.getNombre(), tipoCamino).contains(t1.getNombre()));
        assertTrue(this.n4jl.conectadosCon(t.getNombre(), tipoCamino).contains(g.getNombre()));
        assertTrue(this.n4jl.conectadosCon(t1.getNombre(), tipoCamino1).contains(g1.getNombre()));
    }

}
