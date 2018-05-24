package ar.edu.unq.epers.woe.backend.hibernateDAO;

import ar.edu.unq.epers.woe.backend.model.monstruo.Monstruo;
import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HibernateMonstruoDAOTest {

    private HibernateMonstruoDAO hmd = new HibernateMonstruoDAO();
    private Monstruo m0;
    private Monstruo m1;

    @Before
    public void crearModelo() {
        SessionFactoryProvider.destroy();
        this.m0 = new Monstruo("tstMonstruo0", new Vida(500f),
                new Danho(100f), "dragon");
        this.m1 = new Monstruo("tstMonstruo1", new Vida(400f),
                new Danho(200f), "malboro");
        Runner.runInSession(() -> {
            this.hmd.guardar(m0);
            this.hmd.guardar(m1);
            return null;
        });
    }

    @Test
    public void seRecuperaMonstruoCorrectamente() {
        Monstruo mr0 = Runner.runInSession(() -> {
            return this.hmd.recuperar("tstMonstruo0"); });
        Monstruo mr1 = Runner.runInSession(() -> {
            return this.hmd.recuperar("tstMonstruo1"); });
        assertTrue(mr0.sosMonstruo());
        assertTrue(mr1.sosMonstruo());
        assertEquals(mr0.getNombre(), "tstMonstruo0");
        assertEquals(mr1.getNombre(), "tstMonstruo1");
        assertEquals(mr0.getDanhoTotal().getValor(), new Float(100));
        assertEquals(mr1.getDanhoTotal().getValor(), new Float(200));
        assertEquals(mr0.getVida().getValor(), new Float(500));
        assertEquals(mr1.getVida().getValor(), new Float(400));
    }

}
