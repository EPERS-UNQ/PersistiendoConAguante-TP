package ar.edu.unq.epers.woe.backend.model.raza;

import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.service.raza.ClaseInvalida;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.HashSet;
import static junit.framework.TestCase.assertTrue;
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

    @Test
    public void razaPermiteModificarAtributosDeLaRaza() {
        this.raza.setEnergiaIncial(50);
        this.raza.setId(90);
        this.raza.setAltura(22);
        this.raza.setPeso(50);
        this.raza.setCantidadPersonajes(3);
        this.raza.setNombre("anm0");
        assertEquals(this.raza.getEnergiaInicial(), 50);
        assertEquals(this.raza.getId(), new Integer(90));
        assertEquals(this.raza.getAltura(),22);
        assertEquals(this.raza.getPeso(), 50);
        assertEquals(this.raza.getCantidadPersonajes(), 3);
        assertEquals(this.raza.getNombre(), "anm0");
    }

    @Test
    public void razaPermiteRecuperarPersonajesDeLaRaza() {
        Personaje pj0 = new Personaje(this.raza, "pj0", Clase.SACERDOTE);
        Personaje pj1 = new Personaje(this.raza, "pj1", Clase.SACERDOTE);
        HashSet<Personaje> pjs = new HashSet<Personaje>();
        pjs.add(pj0);
        pjs.add(pj1);
        this.raza.setPersonajes(pjs);
        assertEquals(this.raza.getPersonajes().size(), 2);
        assertTrue(this.raza.getPersonajes().containsAll(pjs));
    }

    @After
    public void tearDown() {}
}