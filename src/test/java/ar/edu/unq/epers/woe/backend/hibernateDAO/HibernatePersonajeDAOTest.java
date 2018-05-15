package ar.edu.unq.epers.woe.backend.hibernateDAO;

import static org.junit.Assert.*;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Atributo;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;
import ar.edu.unq.epers.woe.backend.service.hibernateDAO.HibernatePersonajeDAO;
import ar.edu.unq.epers.woe.backend.service.hibernateDAO.HibernateRazaDAO;
import ar.edu.unq.epers.woe.backend.service.hibernateDAO.Runner;
import ar.edu.unq.epers.woe.backend.service.hibernateDAO.SessionFactoryProvider;

public class HibernatePersonajeDAOTest {

	HibernatePersonajeDAO persDao;
	Personaje p;
	Item i;

	@Before
	public void setUp(){
		persDao = new HibernatePersonajeDAO();
		p = new Personaje(null, "Pepito", null);
		i = new Item("Yelmo", "cabeza", "tipo", null, new Requerimiento(), 0, 0, new HashSet<Atributo>());
	}

	@After
	public void tearDown() {
		SessionFactoryProvider.destroy();
	}
	
	@Test
	public void probemosGuardarYRecuperarAlgunosColaboradoresDeUnPersonaje() {
		//se recupera: nombre, raza, lugar
		Raza r = new Raza("Elfo");
		p.setRaza(r);
		Personaje recuperado =
		    	Runner.runInSession(() -> {
		    		new HibernateRazaDAO().guardar(r); //instancia raza ya deberia estar guardada
		    		this.persDao.guardar(p);

		    		return persDao.recuperar("Pepito");
		    	});
		assertEquals("Pepito", recuperado.getNombre());
		assertEquals( p.getRaza(), recuperado.getRaza());
		assertEquals( recuperado.getLugar(), null );
	}
	
	@Test
	public void recuperamosLaMochilaDeUnPersonaje() {

		p.getMochila().agregarItem(i);
	
		Personaje recuperado =
		    	Runner.runInSession(() -> {
		    		Runner.getCurrentSession().save(i);//guardo item a mano, por ahora  		
		    		this.persDao.guardar(p);
		    		return persDao.recuperar("Pepito");
		    	});		
		assertTrue(recuperado.getMochila().tieneElItem(i) );
	}
	
	@Test
	public void seRecuperaElInventarioDeUnPersonaje() {
		p.agregarItem(i);
		Personaje recuperado =
		    	Runner.runInSession(() -> {
		    		Runner.getCurrentSession().save(i);
		    		this.persDao.guardar(p);
		    		return persDao.recuperar("Pepito");
		    	});
		assertEquals(recuperado.getItemEnUbicacion("cabeza"), i);
	}
}
