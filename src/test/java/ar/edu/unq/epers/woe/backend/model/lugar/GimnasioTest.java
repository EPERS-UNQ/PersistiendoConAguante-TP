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
	public void unGimnasioEsUnGimnasio() {
		assertTrue(gimnasio.esGimnasio());
	}
}
