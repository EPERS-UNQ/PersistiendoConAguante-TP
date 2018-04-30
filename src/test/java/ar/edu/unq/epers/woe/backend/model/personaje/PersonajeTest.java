package ar.edu.unq.epers.woe.backend.model.personaje;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Taberna;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import org.junit.rules.ExpectedException;


import static org.junit.Assert.*;

public class PersonajeTest {

    private Personaje pj;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void crearModelo() {
        pj = new Personaje(new Raza("tstRaza"), "tstPJ0", Clase.MAGO);
    }

    @Test
    public void alSubirDeNivelPJSeIncrementanAtributos() {
        Personaje pj = new Personaje(new Raza("tstRaza"), "tstPJ", Clase.MAGO);
        pj.ganarExperiencia(110);
        assertEquals(pj.getExp(), new Integer(110));
        assertEquals(pj.getNivel(), new Integer(2));
        assertEquals(pj.getAtributo(Fuerza.class).getValor(), new Float(1.02));
        assertEquals(pj.getAtributo(Destreza.class).getValor(), new Float(1.09));
        assertEquals(pj.getAtributo(Vida.class).getValor(), new Float(1.05));
    }

    @Test
    public void alAgregarUnItemALaMochilaSeIncrementaElSize() {
        Item i = new Item(null, null, null, null, 0, 0);
        this.pj.getMochila().agregarItem(i);
        assertEquals(this.pj.getMochila().itemsEnMochila(), new Integer(1));
    }

    @Test
    public void alAgregarUnItemConLaMochilaLlenaSeDisparaExcepcion() {
        Item i = new Item(null, null, null, null, 0, 0);
        for(Integer ct = 0; ct <= 9; ct++) {
            this.pj.getMochila().agregarItem(i);
        }
        thrown.expect(RuntimeException.class);
        this.pj.getMochila().agregarItem(i);
    }

    @Test
    public void alIrUnPersonajeAUnLugarYPreguntarleDondeEstaRetornaElLugar() {
        this.pj.setLugar(new Taberna("Moe's"));
        assertEquals(this.pj.getLugar().getClass(), Taberna.class);
        assertSame("Moe's", this.pj.getLugar().getNombre());
    }

    @After
    public void tearDown() {

    }
}