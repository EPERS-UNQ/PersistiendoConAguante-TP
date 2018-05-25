package ar.edu.unq.epers.woe.backend.hibernateDAO;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Armadura;
import ar.edu.unq.epers.woe.backend.model.personaje.Atributo;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;

public class HibernateItemDAOTest {

	HibernateItemDAO daoIt;
	Item item;
	
	@Before
	public void setUp() {
		
		daoIt = new HibernateItemDAO();
		Set<Clase> clases = new HashSet<Clase>() ; clases.add(Clase.BRUJO);
		Set<Atributo> atrs = new HashSet<Atributo>() ;
		atrs.add(new Armadura());
		item = new Item("Yelmo", "cabeza", "casco", clases, new Requerimiento(), 25, 15, atrs);
		
	}

	@Test
	public void unItemPuedeGuardarseRecuperaPorSuNombre() {

		Item recuperado = Runner.runInSession(() -> {
			daoIt.guardar(item);
			return daoIt.recuperarPorNombre(item.getNombre());
		});
		
		assertEquals(item, recuperado);
	}

	@Test
	public void unItemPuedeRecuperarseDeSuIdAsignadaAutomcnt() {
		Item recuperado = Runner.runInSession(() -> {
			daoIt.guardar(item);
			Item rec1 = daoIt.recuperarPorNombre(item.getNombre());
			return daoIt.recuperar(rec1.getIdItem());
		});
		
		assertEquals(item, recuperado);
	}
}
