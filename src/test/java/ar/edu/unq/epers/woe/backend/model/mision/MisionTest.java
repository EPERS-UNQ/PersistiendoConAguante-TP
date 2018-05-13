package ar.edu.unq.epers.woe.backend.model.mision;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Taberna;
import ar.edu.unq.epers.woe.backend.model.personaje.Atributo;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;

public class MisionTest {

    private Personaje pj;
    private Mision mision;
    private Item i;

    @Before
    public void crearModelo() {
        pj = new Personaje(new Raza("tstRaza"), "tstPJ0", Clase.MAGO);
        mision = new Mision();
        Set<Clase> cls = new HashSet<>();
        Set<Atributo> ats = new HashSet<>();
        i = new Item("plateMail", "torso", "espada", cls, new Requerimiento(),
                5, 1, ats);
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

    @Test
    public void personajeQEstaEnUnLugarPuedeCumplirMisionDeIrAEseLugar() {
        Mision m = new IrALugar("tstIrALugar", new Recompensa(new ArrayList<Item>(), 10, 0f),
                                new Taberna("tstTaberna"));
        this.pj.getMisionesAceptadas().add("tstIrALugar");
        this.pj.setLugar(new Taberna("tstTaberna"));
        m.getRecompensa().otorgarRecompensaA(this.pj);
        assertEquals(this.pj.getExp(), new Integer(10));
        assertTrue(m.puedeAceptarMision(this.pj));
        assertTrue(m.misionAceptadaPor(this.pj));
        assertTrue(m.misionDisponiblePara(this.pj));
        assertTrue(m.puedeCompletarMision(this.pj));
    }

    @Test
    public void personajeQTieneItemPuedeCumplirMisionDeObtenerEseItem() {
        Mision m = new ObtenerItem("tstOI", new Recompensa(new ArrayList<Item>(), 10, 0f),
                this.i);
        this.pj.aceptarMision(m);
        this.pj.getMochila().agregarItem(this.i);
        assertTrue(m.puedeCompletarMision(this.pj));
    }

    @After
    public void tearDown() {

    }
}