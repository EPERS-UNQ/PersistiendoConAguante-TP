package ar.edu.unq.epers.woe.backend.hibernateDAO;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.service.hibernateDAO.HibernateRazaDAO;
import ar.edu.unq.epers.woe.backend.service.hibernateDAO.SessionFactoryProvider;


public class HibernateRazaDAOTest {

	HibernateRazaDAO razaDAO;
	Raza raza;
	
	@Before
	public void setUp() {
		razaDAO = new HibernateRazaDAO();
		raza = new Raza("Elfo");
	}
	
	@After
	public void cleanup() {
		SessionFactoryProvider.destroy();
	}

	
	@Test
	public void testSeRecuperaRaza() {
		razaDAO.guardar(raza);
		Raza recuperada = razaDAO.recuperar(1);
		assertEquals(recuperada.getNombre(), "Elfo");
	}
	
	@Test
	public void testSeRecuperaElSetDeClasesCorrectamente() {
		Set<Clase> clases = new HashSet<Clase>();
		clases.add(Clase.BRUJO);
		raza.setClases(clases);
		razaDAO.guardar(raza);
		Raza recuperada = razaDAO.recuperar(1);

		assertTrue( clases.containsAll(recuperada.getClases()) );	
	}

}