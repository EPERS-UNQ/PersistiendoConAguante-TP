package ar.edu.unq.epers.woe.backend.service;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.service.PersonajeService;

public class PersonajeServiceTest {

	PersonajeService serviceP = new PersonajeService() ;
	
	@Test
	public void unServicePersonajePuedeEquiparDeUnItemAUnPersonaje() {
		Personaje personaje = new Personaje(null, null, null);
		
		Item item = new Item("item", "torso", null, null, null, 0, 0, null) ;
		serviceP.equipar(personaje, item  );
		
		assertEquals(personaje.getInventario().getEnUbicacion("torso").getItem(), item);
	}

}
