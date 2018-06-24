package ar.edu.unq.epers.woe.backend.mongoDAO;

import ar.edu.unq.epers.woe.backend.model.evento.Evento;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MongoDAOTest {

    private GenericMongoDAO<Evento> mde = new GenericMongoDAO<>(Evento.class);

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


}
