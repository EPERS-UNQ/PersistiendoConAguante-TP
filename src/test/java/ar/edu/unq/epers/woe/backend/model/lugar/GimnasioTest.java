package ar.edu.unq.epers.woe.backend.model.lugar;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GimnasioTest {

	private Gimnasio gimnasio;
	
	@Before
	public void setUp() {
		gimnasio = new Gimnasio("Gimnasio Verde");
	}
	
	@Test
	public void unGimnasioEsUnGimnasio() {
		assertTrue(gimnasio.getClass().equals(Gimnasio.class));
		assertEquals(gimnasio.getNombre(), "Gimnasio Verde");
	}
}
