package ar.edu.unq.epers.woe.backend.model.mision;

import ar.edu.unq.epers.woe.backend.model.combate.Combate;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Gimnasio;
import ar.edu.unq.epers.woe.backend.model.lugar.Taberna;
import ar.edu.unq.epers.woe.backend.model.monstruo.Monstruo;
import ar.edu.unq.epers.woe.backend.model.personaje.*;
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
    private Combate combate = new Combate();

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
        this.pj.cambiarDeLugar(new Taberna("tstTaberna"));
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
        this.pj.cambiarDeLugar(new Taberna("tstTaberna"));
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
        assertTrue(m.misionCumplidaPor(this.pj));
    }

    @Test
    public void personajeQTieneVictoriasSuficientesPuedeCumplirMisionDeVencerA() {
        Mision m = new VencerA("tstOI", new Recompensa(new ArrayList<Item>(), 10, 0f),
                this.pj, 0);
        this.pj.aceptarMision(m);
        assertTrue(m.puedeCompletarMision(this.pj));
    }

    @Test
    public void personajeQGanaBatallaContraRivalIncrementaBatallasGanadas() {
        Mision m = new VencerA("tstOI", new Recompensa(new ArrayList<Item>(), 10, 0f),
                this.pj, 1);
        this.pj.aceptarMision(m);
        ResultadoCombate res = new ResultadoCombate();
        res.setGanador(this.pj);
        res.setPerdedor(this.pj);
        m.incrementarVictoriasActualesSiPuede(res);
        assertTrue(m.puedeCompletarMision(this.pj));
    }

    @Test
    public void personajeQGanaBatallaContraMonstruoIncrementaBatallasGanadas() {
        Monstruo monstruo = new Monstruo("tstM1", new Vida(1f), new Danho(0f),
                                         "dragon");
        Mision m = new VencerA("tstOI", new Recompensa(new ArrayList<Item>(), 10, 0f),
                               monstruo, 1);
        this.pj.aceptarMision(m);
        ResultadoCombate res = new ResultadoCombate();
        res.setGanador(this.pj);
        res.setPerdedor(monstruo);
        m.incrementarVictoriasActualesSiPuede(res);
        assertTrue(m.puedeCompletarMision(this.pj));
    }

    @Test
    public void personajeQCumpleMisionVencerARecibeRecompensa() {
        Monstruo monstruo = new Monstruo("tstM1", new Vida(1f), new Danho(0f),
                "dragon");
        Mision m = new VencerA("tstOI", new Recompensa(new ArrayList<Item>(), 10, 5f),
                monstruo, 1);
        this.pj.setVida(new Vida(500f));
        this.pj.getAtributo(Fuerza.class).setValor(500f);
        this.pj.aceptarMision(m);
        ResultadoCombate res = this.combate.combatir(this.pj, monstruo);
        assertTrue(res.getGanador().equals(this.pj));
        assertTrue(m.misionCumplidaPor(this.pj));
        assertEquals(this.pj.getExp(), new Integer(m.getRecompensa().getExp() + 10));
        assertTrue(this.pj.getBilletera() == 5f);
        assertFalse(this.pj.getMisionesEnCurso().contains(m));
        assertFalse(this.pj.getMisionesAceptadas().contains(m.getNombre()));
    }

    @Test
    public void personajeQCumpleMisionIrALugarRecibeRecompensa() {
        Gimnasio gim = new Gimnasio("tstGim");
        Mision m = new IrALugar("tstOI", new Recompensa(new ArrayList<Item>(), 10, 5f), gim);
        this.pj.aceptarMision(m);
        this.pj.cambiarDeLugar(gim);
        assertTrue(this.pj.getLugar().equals(gim));
        assertTrue(m.misionCumplidaPor(this.pj));
        assertEquals(this.pj.getExp(), m.getRecompensa().getExp());
        assertTrue(this.pj.getBilletera() == 5f);
        assertFalse(this.pj.getMisionesEnCurso().contains(m));
        assertFalse(this.pj.getMisionesAceptadas().contains(m.getNombre()));
    }

    @Test
    public void personajeQCumpleMisionObtenerItemRecibeRecompensa() {
        Mision m = new ObtenerItem("tstOI", new Recompensa(new ArrayList<Item>(), 10, 5f), this.i);
        this.pj.aceptarMision(m);
        this.pj.getMochila().agregarItem(this.i);
        assertTrue(this.pj.getMochila().tieneElItem(this.i));
        assertTrue(this.pj.getMisionesCumplidas().contains(m.getNombre()));
        assertEquals(this.pj.getExp(), m.getRecompensa().getExp());
        assertTrue(this.pj.getBilletera() == 5f);
        assertFalse(this.pj.getMisionesEnCurso().contains(m));
        assertFalse(this.pj.getMisionesAceptadas().contains(m.getNombre()));
    }

    @After
    public void tearDown() {

    }
}