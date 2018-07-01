package ar.edu.unq.epers.woe.backend.neo4jDAO;

import ar.edu.unq.epers.woe.backend.model.lugar.Gimnasio;
import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;
import ar.edu.unq.epers.woe.backend.service.data.ServiciosDB;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
    
    @Test(expected = RuntimeException.class)
    public void alCrearUnLugarQueYaEstaCreadoOcurreExcepcion() {
        Gimnasio g = new Gimnasio("gim0");
        this.n4jl.create(g);
        this.n4jl.create(g);
    }

    @Test
    public void seObtieneNombreDeLugaresConectados() {
        Tienda t = new Tienda("tie1");
        Tienda t1 = new Tienda("tie2");
        Gimnasio g = new Gimnasio("gim1");
        Gimnasio g1 = new Gimnasio("gim2");
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


    @Test
    public void seObtieneCostoDeRutaMasCortaEntreLugaresConectados() {
        Tienda t1 = new Tienda("tie1");
        Tienda t2 = new Tienda("tie2");
        Gimnasio g1 = new Gimnasio("gim1");
        Gimnasio g2 = new Gimnasio("gim2");
        String tipoCamino = "terrestre";
        this.n4jl.create(t1);
        this.n4jl.create(t2);
        this.n4jl.create(g1);
        this.n4jl.create(g2);
        this.n4jl.crearRelacionConectadoCon(t1.getNombre(), t2.getNombre(), tipoCamino);
        this.n4jl.crearRelacionConectadoCon(t2.getNombre(), g1.getNombre(), tipoCamino);
        this.n4jl.crearRelacionConectadoCon(t2.getNombre(), g2.getNombre(), tipoCamino);
        this.n4jl.crearRelacionConectadoCon(g1.getNombre(), g2.getNombre(), tipoCamino);
        assertEquals(this.n4jl.costoRutaMasCorta(t1.getNombre(), g2.getNombre()), (Integer) 2);
    }

    @Test
    public void alPreguntarSiExisteCaminoEntreDosNodosConectadosRespondeTrue() {
        Tienda t1 = new Tienda("tie1");
        Tienda t2 = new Tienda("tie2");
        Gimnasio g1 = new Gimnasio("gim1");
        Gimnasio g2 = new Gimnasio("gim2");
        String tipoCamino = "terrestre";
        this.n4jl.create(t1);
        this.n4jl.create(t2);
        this.n4jl.create(g1);
        this.n4jl.create(g2);
        this.n4jl.crearRelacionConectadoCon(t1.getNombre(), t2.getNombre(), tipoCamino);
        this.n4jl.crearRelacionConectadoCon(t2.getNombre(), g1.getNombre(), tipoCamino);
        assertTrue(this.n4jl.existeCaminoEntre(t1.getNombre(), g1.getNombre()));
        assertFalse(this.n4jl.existeCaminoEntre(t1.getNombre(), g2.getNombre()));
    }

    @Test
    public void alPreguntarCostoDeRutaMasBarataRetornaElDeLaMasBarata() {
        Tienda t1 = new Tienda("tie1");
        Tienda t2 = new Tienda("tie2");
        this.n4jl.create(t1);
        this.n4jl.create(t2);
        String tipoCaminoBarato = "terrestre";
        String tipoCaminoCaro = "aereo";
        this.n4jl.crearRelacionConectadoCon(t1.getNombre(), t2.getNombre(), tipoCaminoBarato);
        this.n4jl.crearRelacionConectadoCon(t1.getNombre(), t2.getNombre(), tipoCaminoCaro);
        assertEquals(this.n4jl.costoRutaMasBarata(t1.getNombre(), t2.getNombre()), (Integer) 1);
    }
    
    @Test
    public void seObtieneNombreDeLugaresConectadosConUnLugarPorCualquierCamino() {
        Tienda t = new Tienda("tie1");
        Tienda t1 = new Tienda("tie2");
        Gimnasio g = new Gimnasio("gim1");
        Gimnasio g1 = new Gimnasio("gim2");
        String tipoCamino = "terrestre";
        String tipoCamino1 = "maritimo";
        this.n4jl.create(t);
        this.n4jl.create(t1);
        this.n4jl.create(g);
        this.n4jl.create(g1);
        // 't' se relaciona con 't1', 'g', y 'g1'
        this.n4jl.crearRelacionConectadoCon(t.getNombre(), t1.getNombre(), tipoCamino);
        this.n4jl.crearRelacionConectadoCon(t.getNombre(), g.getNombre(), tipoCamino);
        this.n4jl.crearRelacionConectadoCon(t.getNombre(), g1.getNombre(), tipoCamino1);
        List<String> lugaresConectados = n4jl.conectadosCon(t.getNombre());
		assertEquals( 3, lugaresConectados.size() );
		assertTrue( lugaresConectados.contains(t1.getNombre()) );
		assertTrue( lugaresConectados.contains(g.getNombre()) );
		assertTrue( lugaresConectados.contains(g1.getNombre()) );
	}
}
