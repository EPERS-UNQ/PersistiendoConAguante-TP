package ar.edu.unq.epers.woe.backend.hibernateDAO;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ar.edu.unq.epers.woe.backend.model.lugar.Gimnasio;
import ar.edu.unq.epers.woe.backend.model.mision.IrALugar;
import ar.edu.unq.epers.woe.backend.model.mision.Mision;
import ar.edu.unq.epers.woe.backend.model.mision.Recompensa;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;
import ar.edu.unq.epers.woe.backend.model.personaje.*;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;


public class HibernatePersonajeDAOTest {

	private HibernatePersonajeDAO persDao;
	private Personaje p;
	private Item i;

	@Before
	public void setUp(){

		SessionFactoryProvider.destroy();
		persDao = new HibernatePersonajeDAO();
		p = new Personaje(null, "Pepito", Clase.BRUJO);
		i = new Item("Yelmo", "cabeza", "tipo", null, new Requerimiento(), 0, 0, new HashSet<Atributo>());
	}

	@After
	public void tearDown() {
		SessionFactoryProvider.destroy();
	}
	
	@Test
	public void probemosGuardarYRecuperarAlgunosColaboradoresDeUnPersonaje() {
		//se recupera: nombre, raza, clase, lugar y atributos
		Raza r = new Raza("Elfo");
		Set<Clase> clases = new HashSet<Clase>();
			clases.add(Clase.BRUJO);
		r.setClases(clases);
		
		Lugar t = new Tienda("Tiendita");
		
		p.setRaza(r);
		p.setLugar(t);
		
		Personaje recuperado =
		    	Runner.runInSession(() -> {
		    		new HibernateRazaDAO().guardar(r); //instancia raza ya deberia estar 'guardada'
		    		new HibernateLugarDAO().guardar(t);//instancia lugar ya deberia estar 'guardada'
		    		persDao.guardar(p);

		    		return persDao.recuperar("Pepito");
		    	});
		assertEquals("Pepito", recuperado.getNombre());
		assertEquals( p.getRaza(), recuperado.getRaza());
		assertEquals( p.getClase(), recuperado.getClase() );
		assertEquals( p.getLugar(), recuperado.getLugar() );
		//todos los atributos tienen valor de 1f por default
		assertEquals( 1, recuperado.getAtributo(Fuerza.class).getValor().intValue());
		assertEquals( 1, recuperado.getAtributo(Destreza.class).getValor().intValue());
		assertEquals( 1, recuperado.getAtributo(Armadura.class).getValor().intValue());
		assertEquals( 1, recuperado.getAtributo(Danho.class).getValor().intValue());
		assertEquals( 1, recuperado.getAtributo(Vida.class).getValor().intValue());
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
		p.agregarItemAInv(i);
		Personaje recuperado =
		    	Runner.runInSession(() -> {
		    		Runner.getCurrentSession().save(i);
		    		this.persDao.guardar(p);
		    		return persDao.recuperar("Pepito");
		    	});
		assertEquals(recuperado.getItemEnUbicacion("cabeza"), i);
	}
	
	@Test
	public void seRecuperanMisionesAceptadas() {
		Personaje recuperado =
				Runner.runInSession(() -> {
					Gimnasio gim = new Gimnasio("tstGim");
					Mision m = new IrALugar("tstOI", new Recompensa(new ArrayList<Item>(), 10, 5f), gim);
					p.aceptarMision(m);
					Runner.getCurrentSession().save(gim);
					Runner.getCurrentSession().save(m);
					this.persDao.guardar(p);
					return persDao.recuperar("Pepito");
				});
		assertTrue(recuperado.getMisionesAceptadas().contains("tstOI"));
		assertTrue(recuperado.getMisionesEnCurso().iterator().next().getClass().equals(IrALugar.class));
	}

}
