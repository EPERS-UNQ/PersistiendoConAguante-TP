<<<<<<< HEAD
=======
//package java.ar.edu.unq.epers.woe.backend.model.lugar;
>>>>>>> 7b47bcba74101c77e09865fe72d367188b71ea31
package ar.edu.unq.epers.woe.backend.model.lugar;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;

public class LugarTest {

	@Test
	public void unLugarTieneUnNombre() {
		Lugar lugarA = new Lugar("Lugar A");
		assertTrue(lugarA.getNombre().equals("Lugar A") );
	}

}
