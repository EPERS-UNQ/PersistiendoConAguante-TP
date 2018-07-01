package ar.edu.unq.epers.woe.backend.mongoDAO;

import ar.edu.unq.epers.woe.backend.model.evento.Arribo;
import ar.edu.unq.epers.woe.backend.model.evento.CompraItem;
import ar.edu.unq.epers.woe.backend.model.evento.Evento;
import ar.edu.unq.epers.woe.backend.model.evento.Ganador;
import ar.edu.unq.epers.woe.backend.model.evento.MisionAceptada;
import ar.edu.unq.epers.woe.backend.model.evento.MisionCompletada;
import ar.edu.unq.epers.woe.backend.model.evento.VentaItem;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class MongoDAOTest {

    private EventoMongoDAO<Evento> mde = new EventoMongoDAO();

    @Before
    public void setUp() {
        this.mde.eliminarDatos();
    }

    @Test
    public void seGuardaYRecuperaEvento() {
        Evento eventoGuardado = new Evento("tstPJ0", "tstLugar0");
        mde .save(eventoGuardado);
        Evento eventoRecuperado = mde.get(eventoGuardado.getIdEvento());
        assertEquals(eventoGuardado.getNombreLugar(), eventoRecuperado.getNombreLugar());
        assertEquals(eventoGuardado.getIdEvento(), eventoRecuperado.getIdEvento());
        assertEquals(eventoGuardado.getNombrePJ(), eventoRecuperado.getNombrePJ());
        assertEquals(eventoGuardado.getFecha(), eventoRecuperado.getFecha());
    }

    @Test
    public void seRecuperaEventosOrdenadosPorFechaDeMasRecienteAMasAntiguo() {
        Evento eventoGuardadoPrimero = new Evento("tstPJ4", "tstLugar4");
        mde.save(eventoGuardadoPrimero);
        Evento eventoGuardadoSegundo = new Evento("tstPJ5", "tstLugar5");
        mde.save(eventoGuardadoSegundo);
        List<Evento> le = mde.findOrderByDateDesc("");
        assertEquals(le.size(), 2);
        assertEquals(le.get(0).getFecha(), eventoGuardadoSegundo.getFecha());
        assertEquals(le.get(1).getFecha(), eventoGuardadoPrimero.getFecha());
    }

    @Test
    public void seGuardaYRecuperaEventoDeArribo() {
        Arribo eventoGuardado = new Arribo("tstPJ1", "tstLugar1", "Tienda",
                       "tstLugar2", "Taberna");
        mde.save(eventoGuardado);
        Arribo eventoRecuperado = (Arribo) mde.get(eventoGuardado.getIdEvento());
        assertEquals(eventoGuardado.getNombreLugar(), eventoRecuperado.getNombreLugar());
        assertEquals(eventoGuardado.getIdEvento(), eventoRecuperado.getIdEvento());
        assertEquals(eventoGuardado.getNombrePJ(), eventoRecuperado.getNombrePJ());
        assertEquals(eventoGuardado.getFecha(), eventoRecuperado.getFecha());
        assertEquals(eventoGuardado.getNombreLugarDestino(), eventoRecuperado.getNombreLugarDestino());
        assertEquals(eventoGuardado.getClaseLugarDestino(), eventoRecuperado.getClaseLugarDestino());
        assertEquals(eventoGuardado.getClaseLugarOrigen(), eventoRecuperado.getClaseLugarOrigen());
    }

    @Test
    public void seGuardaYRecuperaEventoDeGanador() {
        Ganador eventoGuardado = new Ganador("tstPJ2", "tstLugar3", "tstPJ3",
                            "tstClase0", "tstClase1", "tstRaza0",
                                "tstRaza1");
        mde.save(eventoGuardado);
        Evento e = mde.get(eventoGuardado.getIdEvento());

        Ganador eventoRecuperado = (Ganador) e;
        
        assertEquals(eventoGuardado.getNombreLugar(), eventoRecuperado.getNombreLugar());
        assertEquals(eventoGuardado.getIdEvento(), eventoRecuperado.getIdEvento());
        assertEquals(eventoGuardado.getNombrePJ(), eventoRecuperado.getNombrePJ());
        assertEquals(eventoGuardado.getFecha(), eventoRecuperado.getFecha());
        assertEquals(eventoGuardado.getNombreContrincante(), eventoRecuperado.getNombreContrincante());
        assertEquals(eventoGuardado.getClaseContrincante(), eventoRecuperado.getClaseContrincante());
        assertEquals(eventoGuardado.getClaseGanador(), eventoRecuperado.getClaseGanador());
        assertEquals(eventoGuardado.getRazaContrincante(), eventoRecuperado.getRazaContrincante());
        assertEquals(eventoGuardado.getRazaGanador(), eventoRecuperado.getRazaGanador());
    }
    
    @Test
    public void recuperarListaDeEventosRelacionadosAUnLugarDeFormaDescPorFecha() {
    	String lugar = "tstLugar3";
    	Evento e = new Ganador("tstPJ2", lugar, "tstPJ3",
                		"tstClase0", "tstClase1", "tstRaza0", "tstRaza1");
       	Evento e1 = new MisionAceptada("tstPJ2", lugar, "tstMision2");
     	mde.save(e); mde.save(e1); 
     	
     	//Evento mas reciente
    	Evento e2 = new MisionAceptada("tstPJ2", lugar, "tstMision1");
    	mde.save(e2);
    	
    	List<Evento> listaEventos = mde.getByLugar(lugar);
    	assertEquals(3, listaEventos.size() );
    	assertEquals(e2.getFecha(), listaEventos.get(0).getFecha() );
    }
    
    @Test
    public void recuperarListaDeEventosRelacionadosAVariosLugaresDeFormaOrdenada() {
    	String lugar = "tstLugar";
    	String lugar2 = "tstLugar2";
    	String lugar3 = "tstLugar3";
    	Evento e = new Ganador("tstPJ2", lugar, "tstPJ3",
                		"tstClase0", "tstClase1", "tstRaza0", "tstRaza1");
       	Evento e1 = new MisionAceptada("tstPJ2", lugar2, "tstMision2");
     	mde.save(e); mde.save(e1); 
     	
       	Evento e2 = new MisionAceptada("tstPJ2", lugar3, "tstMision3");
     	mde.save(e2);
    	
     	List<String> lugares = new ArrayList<String>();
     	lugares.add(lugar3); lugares.add(lugar2); lugares.add(lugar);
     	
    	List<Evento> listaEventos = mde.getByLugares(lugares);
    	
    	assertEquals(3, listaEventos.size());
    	assertEquals(e2.getFecha(), listaEventos.get(0).getFecha() ); //mas reciente
    }
    
    @Test
    public void seRecuperaListaDeEventosEnElQueEsteRelacionadoUnPersonaje() {
    	String nombrePersonaje = "tstPJ";
    	
    	//hace de ganador
		Evento e1 = new Ganador( nombrePersonaje, "tstLugar", "tstPJ2", "tstClase0", "tstClase1", "tstRaza0", "tstRaza1");
		//hace de perdedor
		Evento e2 = new Ganador( "tstPJ2", "tstLugar", nombrePersonaje, "tstClase0", "tstClase1", "tstRaza0", "tstRaza1");
		mde.save(e1); mde.save(e2);

		Evento e3 = new MisionAceptada(nombrePersonaje, "tstLugar", "tstMision3");
		Evento e4 = new MisionCompletada(nombrePersonaje, "tstLugar", "tstMision3");
		Evento e5 = new CompraItem(nombrePersonaje, "tstLugar", "tstItem", 0);
		Evento e6 = new VentaItem(nombrePersonaje, "tstLugar", "tstItem", 0);
		mde.save(e3); mde.save(e4); mde.save(e5); mde.save(e6);
		
		List<Evento> listaEventos = mde.getByPersonaje(nombrePersonaje) ;
		assertEquals( 6, listaEventos.size() );
		assertEquals(e6.getFecha(), listaEventos.get(0).getFecha() );
    }

}
