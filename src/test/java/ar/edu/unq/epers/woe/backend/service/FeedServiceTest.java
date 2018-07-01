package ar.edu.unq.epers.woe.backend.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.evento.Arribo;
import ar.edu.unq.epers.woe.backend.model.evento.CompraItem;
import ar.edu.unq.epers.woe.backend.model.evento.Evento;
import ar.edu.unq.epers.woe.backend.model.evento.Ganador;
import ar.edu.unq.epers.woe.backend.model.evento.MisionAceptada;
import ar.edu.unq.epers.woe.backend.model.evento.MisionCompletada;
import ar.edu.unq.epers.woe.backend.model.evento.VentaItem;
import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.mongoDAO.EventoMongoDAO;
import ar.edu.unq.epers.woe.backend.neo4jDAO.Neo4jLugarDAO;
import ar.edu.unq.epers.woe.backend.service.feed.FeedService;

public class FeedServiceTest {

	FeedService feedServ;
	EventoMongoDAO eventoDao;

	@Before
	public void setUp(){
		feedServ = new FeedService();
		eventoDao = new EventoMongoDAO(); eventoDao.eliminarDatos();
	}

	@Test
	public void seRecuperanSeisEventosRelacionadosAUnPersonaje() {

		Personaje p = new Personaje(null, "testP1", null);
		
    	Evento e = new Ganador(p.getNombre(), "testL", null, "tstClase0", "tstClase1", "tstRaza0", "tstRaza1");
       	Evento e2 = new MisionAceptada(p.getNombre(), "testL", "tstMision1");
       	Evento e3 = new MisionCompletada(p.getNombre(), "testL", "tstMision2");
       	Evento e4 = new Arribo(p.getNombre(), "tLugarOrg", "tClaseLOrg", "tLugarDest", "tClaseLDest");
       	Evento e5 = new CompraItem(p.getNombre(), "testL", "testItem", 0);
       	Evento e6 = new VentaItem(p.getNombre(), "testL", "testItem", 0);

       	Evento eNonRelated = new CompraItem( "testP2", "testL", "testItem", 0);

       	eventoDao.save(e); eventoDao.save(e2); eventoDao.save(e3);
       	eventoDao.save(e4); eventoDao.save(e5); eventoDao.save(e6);
       	eventoDao.save(eNonRelated);
       	  	
       	List<Evento> eventos = feedServ.feedPersonaje( p.getNombre() );
     	assertEquals( 6, eventos.size() );
     	assertEquals( e6.getFecha(), eventos.get(0) .getFecha() ); //mas reciente
     	assertEquals( e.getFecha(), eventos.get(5) .getFecha() ); //mas antiguo
	}
	
	@Test
	public void deUnPersonajeSinNingunEventoSeObtieneUnaListaVacia() {
		Personaje p = new Personaje(null, "testP1", null);
		
		Evento eNonRelated = new CompraItem( "tstPjX", "testLugar", "testItem", 0);
		eventoDao.save(eNonRelated);
		
		assertTrue( feedServ.feedPersonaje( p.getNombre() ).isEmpty() );
	}
	
	@Test
	public void seRecuperanSeisEventosRelacionadosAUnLugarYASusConexiones() {
		
		Lugar lugar = new Tienda("testLugar");
		Lugar lugar2 = new Tienda("testLugar2");
		Lugar lugar3 = new Tienda("testLugar3");
		
		Neo4jLugarDAO lugarDao = new Neo4jLugarDAO(); 
		lugarDao.eliminarDatos(); //clean Neo4J
		lugarDao.create(lugar); lugarDao.create(lugar2); lugarDao.create(lugar3);
		String tipoCamino = "terrestre";
		lugarDao.crearRelacionConectadoCon(lugar.getNombre(), lugar2.getNombre(), tipoCamino );
		lugarDao.crearRelacionConectadoCon(lugar.getNombre(), lugar3.getNombre(), tipoCamino );
		
		//eventos con lugar
    	Evento e = new Ganador("tstPj", lugar.getNombre(), null, "tstClase0", "tstClase1", "tstRaza0", "tstRaza1");
    	//eventos con lugar2
    	Evento e2 = new MisionAceptada("tstPj", lugar2.getNombre(), "tstMision1");
       	Evento e3 = new MisionCompletada( "tstPj", lugar2.getNombre(), "tstMision1");
       	Evento e4 = new Arribo( "tstPj", lugar2.getNombre(), "tClaseLOrg", "tLugarDest", "tClaseLDest");
       	//eventos con lugar3
       	Evento e5 = new CompraItem( "tstPj", lugar3.getNombre(), "testItem", 0);
       	Evento e6 = new VentaItem( "tstPj", lugar3.getNombre(), "testItem", 0);

       	Evento eNonRelated = new CompraItem( "tstPj", "testLugarX", "testItem", 0);

       	eventoDao.save(e); eventoDao.save(e2); eventoDao.save(e3);
       	eventoDao.save(e4); eventoDao.save(e5); eventoDao.save(e6);
       	eventoDao.save(eNonRelated);
       	
       	List<Evento> eventos = feedServ.feedLugar( lugar.getNombre() );
		assertEquals( 6, eventos.size() );
		assertEquals( e6.getFecha(), eventos.get(0).getFecha() );//mas reciente
		assertEquals( e5.getFecha(), eventos.get(1).getFecha() );//segundo mas reciente
	}
	
	@Test
	public void deUnLugarSinNingunEventoSeObtieneUnaListaVacia() {
		Lugar lugar = new Tienda("testLugar");
		Neo4jLugarDAO lugarDao = new Neo4jLugarDAO(); 
		lugarDao.eliminarDatos(); //clean Neo4J
		lugarDao.create(lugar);
		
		Evento eNonRelated = new CompraItem( "tstPj", "testLugarX", "testItem", 0);
		eventoDao.save(eNonRelated);
		
		assertTrue( feedServ.feedLugar(lugar.getNombre()) .isEmpty() );
	}

}
