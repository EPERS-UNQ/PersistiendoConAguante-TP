package ar.edu.unq.epers.woe.backend.model.mision;

import ar.edu.unq.epers.woe.backend.model.lugar.Taberna;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;


import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class MisionTest {

    private Personaje pj;
    private Mision mision;

    @Before
    public void crearModelo() {
        pj = new Personaje(new Raza("tstRaza"), "tstPJ0", Clase.MAGO);
        mision = new Mision();
    }

    @Test
    public void personajeEnUnaTabernaPuedeAceptarMision() {
        this.pj.setLugar(new Taberna("tstTaberna"));
        assertTrue(this.mision.puedeAceptarMision(this.pj));
    }

    @Test
    public void personajeQCumplioMisionAnteriorTieneDisponibleLaQLaRequiere() {
        Set<String> preReqs = new HashSet<>();
        preReqs.add("tstPre");
        this.mision.setPrereqs(preReqs);
        Boolean pre = this.mision.misionDisponiblePara(this.pj);
        this.pj.setMisionesCumplidas(preReqs);
        assertFalse(pre);
        assertTrue(this.mision.misionDisponiblePara(this.pj));
    }

    @After
    public void tearDown() {

    }
}