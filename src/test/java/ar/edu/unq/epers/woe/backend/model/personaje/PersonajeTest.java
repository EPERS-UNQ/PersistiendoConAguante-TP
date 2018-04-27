package ar.edu.unq.epers.woe.backend.model.personaje;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.razadao.RazaDao;
import ar.edu.unq.epers.woe.backend.service.data.ServiciosDB;
import ar.edu.unq.epers.woe.backend.service.raza.ServiciosRaza;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;

public class PersonajeTest {

//    private RazaDao razaDAO = new RazaDao();
//    private Raza raza;
//    private ServiciosRaza razaServ = new ServiciosRaza();
//    private ServiciosDB dbServ = new ServiciosDB();

    @Before
    public void crearModelo() {
//        this.dbServ.crearSetDatosIniciales();
//        this.raza = this.razaServ.getRaza(1);
    }

    @Test
    public void alSubirDeNivelPJSeIncrementanAtributos() {
        Personaje pj = new Personaje(new Raza("tstRaza"), "tstPJ", Clase.MAGO);
        pj.ganarExperiencia(110);
        assertEquals(pj.getExp(), new Integer(110));
        assertEquals(pj.getNivel(), new Integer(2));
        assertEquals(pj.getAtributo(Fuerza.class).getValor(), new Float(1.02));
        assertEquals(pj.getAtributo(Destreza.class).getValor(), new Float(1.09));
        assertEquals(pj.getAtributo(Vida.class).getValor(), new Float(1.05));
    }

    @After
    public void tearDown() {
//        this.dbServ.eliminarDatos();
    }
}