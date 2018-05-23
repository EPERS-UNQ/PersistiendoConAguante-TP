package ar.edu.unq.epers.woe.backend.model.raza;

import ar.edu.unq.epers.woe.backend.service.data.ServiciosDB;
import ar.edu.unq.epers.woe.backend.service.raza.ClaseInvalida;
import ar.edu.unq.epers.woe.backend.service.raza.ServiciosRaza;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class RazaTest {

    private Raza raza;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void crearModelo() {
        HashSet<Clase> cls = new HashSet<Clase>();
        cls.add(Clase.SACERDOTE);
        this.raza = new Raza("Elfo");
        this.raza.setClases(cls);
    }

    @Test
    public void alCrearPjSeIncrementanPjsDeLaRaza() {
        Integer cantPrevia = this.raza.getCantidadPersonajes();
        this.raza.crearPersonaje("Seiya", Clase.SACERDOTE);
        assertEquals(this.raza.getCantidadPersonajes(), cantPrevia + 1);
    }

    @Test
    public void alCrearPersonajeConClaseQueNoCorrespondeOcurreExcepcion() {
        Clase claseNoIncluidaEnRaza = Clase.PALADIN;
        assertFalse( this.raza.getClases().contains(claseNoIncluidaEnRaza) );
        thrown.expect(ClaseInvalida.class);
        this.raza.crearPersonaje("Seiya", claseNoIncluidaEnRaza);
    }

    @After
    public void tearDown() {}
}