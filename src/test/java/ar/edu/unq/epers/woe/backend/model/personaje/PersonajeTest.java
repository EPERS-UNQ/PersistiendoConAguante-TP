package ar.edu.unq.epers.woe.backend.model.personaje;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Taberna;
import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import org.junit.rules.ExpectedException;


import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

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
        Item i = new Item(null, null, null, null, null, 0, 0, null);
        this.pj.getMochila().agregarItem(i);
        assertEquals(this.pj.getMochila().itemsEnMochila(), new Integer(1));
    }

    @Test
    public void alAgregarUnItemConLaMochilaLlenaSeDisparaExcepcion() {
        Item i = new Item(null, null, null, null, null, 0, 0, null);
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

    @Test
    public void alAgregarUnItemAlInventarioElItemEstaEnEsaPosicion() {
        Item i = new Item(null, "torso", null, null, null, 0, 0, null);
        this.pj.getInventario().setItemEnUnaUbicacion(i, this.pj);
        assertEquals(this.pj.getInventario().getEnUbicacion("torso").getItem(), i);
    }

    @Test
    public void alAgregarUnItemAlInventarioConOtroItemElItemEstaEnEsaPosicionYElOtroEnLaMochila() {
        Item i = new Item("plateMail", "torso", null, null, null, 0, 0, null);
        Item ii = new Item(null, "torso", null, null, null, 0, 0, null);
        this.pj.getInventario().setItemEnUnaUbicacion(i, this.pj);
        this.pj.getInventario().setItemEnUnaUbicacion(ii, this.pj);
        assertEquals(this.pj.getInventario().getEnUbicacion("torso").getItem(), ii);
        assertEquals(this.pj.getMochila().getItems().get(0), i);
    }
    
    @Test
    public void unPersonajeIngresaAunaTiendaYCompraUnItem() {
    	//items de la tienda
    	Set<Item> itemsTienda = new HashSet<Item>();
    	Item i = new Item("plateMail", "torso", null, null, 5, 1); 
    	itemsTienda.add(i);
    	
    	Tienda tienda = new Tienda("tstTienda");
    	tienda.setItems(itemsTienda);
    	
    	Float billeteraPrevia = 100f;
    	pj.setBilletera(billeteraPrevia);
    	pj.setLugar(tienda);
    	pj.comprar(i);
    	
    	//pj tiene menos plata en billetera
    	assertTrue(pj.getBilletera()< billeteraPrevia);
    	//pj cuenta con ese item en esa ubicacion
    	assertEquals(this.pj.getInventario().getEnUbicacion("torso").getItem(), i);
    }
    
    @Test
    public void unPersonajeVendeUnItemEnUnaTienda() {
    	Tienda tienda = new Tienda("tstTienda");
    	tienda.setItems(new HashSet<Item>());
    	
    	Item i = new Item("plateMail", "torso", null, null, 5, 1);
    	pj.agregarItem(i);
    	
    	Float billeteraPrevia = pj.getBilletera();
    	pj.setLugar(tienda);
    	pj.vender(i);
    	
    	//pj tiene mas plata en billetera
    	assertTrue(pj.getBilletera()>billeteraPrevia);
    	//pj ya no cuenta con ese item en la mochila
    	assertFalse(this.pj.getMochila().getItems().contains(i) );
    }

    @After
    public void tearDown() {

    }
}