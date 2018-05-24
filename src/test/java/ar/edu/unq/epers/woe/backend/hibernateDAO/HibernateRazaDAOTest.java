package ar.edu.unq.epers.woe.backend.hibernateDAO;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import ar.edu.unq.epers.woe.backend.service.data.ServiciosDB;
import ar.edu.unq.epers.woe.backend.service.raza.ServiciosRaza;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;


public class HibernateRazaDAOTest {

	HibernateRazaDAO razaDAO;
	Raza raza;
	private ServiciosRaza razaServ = new ServiciosRaza();
	private ServiciosDB dbServ = new ServiciosDB();
	
	@Before
	public void setUp() {
		SessionFactoryProvider.destroy();
		razaDAO = new HibernateRazaDAO();
		raza = new Raza("Elfo");
	}

	@After
	public void cleanup() {

	}

	@Test
	public void alRecuperarUnaRazaSeCreaUnaInstanciaConAtributosCorrectos() {
		this.dbServ.crearSetDatosIniciales();
		this.raza = Runner.runInSession(() -> { return this.razaDAO.recuperarPorNombre("xRaza1"); });
		Raza razaATestear = this.raza;
		assertEquals(razaATestear.getId(), new Integer(1));
		assertEquals(razaATestear.getNombre(), this.raza.getNombre());
		assertEquals(razaATestear.getClases(), this.raza.getClases());
		assertEquals(razaATestear.getEnergiaInicial(), this.raza.getEnergiaInicial());
		assertEquals(razaATestear.getPeso(), this.raza.getPeso());
		assertEquals(razaATestear.getUrlFoto(), this.raza.getUrlFoto());
		assertEquals(razaATestear.getCantidadPersonajes(), this.raza.getCantidadPersonajes());
	}

	@Test
	public void alCrearPjSeIncrementanPjsDeLaRazaRecuperada() {
		this.dbServ.crearSetDatosIniciales();
		this.raza = Runner.runInSession(() -> { return this.razaDAO.recuperarPorNombre("xRaza1"); });
		Integer cantPrevia = this.raza.getCantidadPersonajes();
		this.razaServ.crearPersonaje(this.raza.getId(), "Seiya", Clase.SACERDOTE);
		assertEquals(this.razaServ.getRaza(this.raza.getId()).getCantidadPersonajes(), cantPrevia + 1);
	}

	
	@Test
	public void testSeRecuperaRaza() {
		Raza recuperada =
	    	Runner.runInSession(() -> {
	    		int id = this.razaDAO.guardar(raza);
	    		
	    		return razaDAO.recuperar(id);
	    	});

		assertEquals(recuperada.getNombre(), "Elfo");
	}
	
	@Test
	public void testSeRecuperaElSetDeClasesCorrectamente() {
		Set<Clase> clases = new HashSet<Clase>();
		clases.add(Clase.BRUJO);
		raza.setClases(clases);
		
		Raza recuperada =
		    	Runner.runInSession(() -> {
		    		int id = this.razaDAO.guardar(raza);
		    		
		    		return razaDAO.recuperar(id);
		    	});

		assertTrue( clases.containsAll(recuperada.getClases()) );	
	}

}