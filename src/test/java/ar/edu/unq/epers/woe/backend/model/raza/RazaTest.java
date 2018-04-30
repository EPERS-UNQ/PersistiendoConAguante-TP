package ar.edu.unq.epers.woe.backend.model.raza;

import ar.edu.unq.epers.woe.backend.service.data.ServiciosDB;
import ar.edu.unq.epers.woe.backend.service.raza.ClaseInvalida;
import ar.edu.unq.epers.woe.backend.service.raza.ServiciosRaza;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class RazaTest {

    private Raza raza;
    private ServiciosRaza razaServ = new ServiciosRaza();
    private ServiciosDB dbServ = new ServiciosDB();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void crearModelo() {
        this.dbServ.crearSetDatosIniciales();
        this.raza = this.razaServ.getRaza(1);
    }

    @Test
    public void alRecuperarUnaRazaSeCreaUnaInstanciaConAtributosCorrectos() {
        Raza raza = this.razaServ.getRaza(1);
        assertEquals(raza.getId(), new Integer(1));
        assertEquals(raza.getNombre(), this.raza.getNombre());
        assertEquals(raza.getClases(), this.raza.getClases());
        assertEquals(raza.getEnergiaInicial(), this.raza.getEnergiaInicial());
        assertEquals(raza.getPeso(), this.raza.getPeso());
        assertEquals(raza.getUrlFoto(), this.raza.getUrlFoto());
        assertEquals(raza.getCantidadPersonajes(), this.raza.getCantidadPersonajes());
    }

    @Test
    public void alCrearPjSeIncrementanPjsDeLaRaza() {
        Integer cantPrevia = this.raza.getCantidadPersonajes();
        this.razaServ.crearPersonaje(this.raza.getId(), "Seiya", Clase.SACERDOTE);
        assertEquals(this.razaServ.getRaza(this.raza.getId()).getCantidadPersonajes(), cantPrevia + 1);
    }

    @Test
    public void alCrearPersonajeConClaseQueNoCorrespondeOcurreExcepcion() {
        Clase claseNoIncluidaEnRaza = Clase.PALADIN;
        assertFalse( this.raza.getClases().contains(claseNoIncluidaEnRaza) );

        thrown.expect(ClaseInvalida.class);
        this.razaServ.crearPersonaje(this.raza.getId(), "Seiya", claseNoIncluidaEnRaza);
    }

    @After
    public void tearDown() {
        this.dbServ.eliminarDatos();
    }
}