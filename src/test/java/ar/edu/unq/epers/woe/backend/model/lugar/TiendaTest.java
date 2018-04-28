package lugar;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;

public class TiendaTest {

	private Tienda tienda;
	
	@Before
	public void setUp() {
		tienda = new Tienda("Tiendita");
	}

	@Test
	public void unaTiendaTieneUnSetDeItemsDisponibles() {
		Set<Item> listaVaciaDeItems = new HashSet<Item>();
		tienda.setItems(listaVaciaDeItems);
		
		assertTrue(tienda.getItems().isEmpty());
	}

}
