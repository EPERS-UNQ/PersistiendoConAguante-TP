package ar.edu.unq.epers.woe.backend.model.lugar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.lugar.Gimnasio;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class GimnasioTest {

	private Gimnasio gimnasio;
	
	@Before
	public void setUp() {
		gimnasio = new Gimnasio("Gimnasio Verde");
	}
	
	@Test
	public void unGimnasioTienePersonajesEsperandoAPelear() {
		
		//no ingreso ningun personaje
		assertTrue(gimnasio.getPersonajes().isEmpty());
	}

	@Test
	public void unGimnasioDejaIngresarAPersonaje() {
		Personaje p = new Personaje(null, null, null);
		gimnasio.ingresaPersonaje(p );
		assertTrue( gimnasio.getPersonajes().contains(p) );
	}
}
