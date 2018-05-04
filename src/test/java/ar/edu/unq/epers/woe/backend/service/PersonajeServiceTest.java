package ar.edu.unq.epers.woe.backend.service;

import static org.junit.Assert.*;

import ar.edu.unq.epers.woe.backend.model.personaje.Atributo;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.service.PersonajeService;

import java.util.HashSet;

public class PersonajeServiceTest {

	PersonajeService serviceP = new PersonajeService() ;
	private Personaje pj;

	@Before
	public void crearModelo() {
		pj = new Personaje(new Raza("tstRaza"), "tstPJ0", Clase.MAGO);
	}
	
	@Test
	public void unServicePersonajePuedeEquiparDeUnItemAUnPersonaje() {
		HashSet<Atributo> atts = new HashSet<>();
		atts.add(new Vida(5f));
		Personaje personaje = new Personaje(null, null, null);
		
		Item item = new Item("item", "torso", null, null, null, 0, 0, atts) ;
		serviceP.equipar(personaje, item  );
		
		assertEquals(personaje.getInventario().getEnUbicacion("torso").getItem(), item);
	}

	@Test
	public void alEquiparUnItemSeIncrementaAtributo() {
		HashSet<Atributo> atts = new HashSet<>();
		atts.add(new Vida(5f));
		Item i = new Item(null, "torso", null, null, null, 0, 0,
				atts);
		this.pj.getMochila().agregarItem(i);
		this.pj.getInventario().setItemEnUnaUbicacion(i, this.pj);
		assertEquals(this.pj.getAtributo(Vida.class).getValor(), new Float(6f));
	}

}
