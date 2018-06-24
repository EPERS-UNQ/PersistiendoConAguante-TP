package ar.edu.unq.epers.woe.backend.mongoDAO;

import ar.edu.unq.epers.woe.backend.model.evento.Arribo;
import ar.edu.unq.epers.woe.backend.model.evento.Evento;
import ar.edu.unq.epers.woe.backend.model.evento.Ganador;
import org.junit.Test;
import org.junit.Before;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MongoDAOTest {

    private GenericMongoDAO<Evento> mde = new GenericMongoDAO<>(Evento.class);
    private GenericMongoDAO<Arribo> mda = new GenericMongoDAO<>(Arribo.class);
    private GenericMongoDAO<Ganador> mdg = new GenericMongoDAO<>(Ganador.class);

    @Before
    public void setUp() {
        this.mde.eliminarDatos();
    }

    @Test
    public void seGuardaYRecuperaEvento() {
        Evento eventoGuardado = new Evento("tstPJ0", "tstLugar0");
        mde.save(eventoGuardado);
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
        mda.save(eventoGuardado);
        Arribo eventoRecuperado = mda.get(eventoGuardado.getIdEvento());
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
        mdg.save(eventoGuardado);
        Ganador eventoRecuperado = mdg.get(eventoGuardado.getIdEvento());
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


}
