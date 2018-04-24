package ar.edu.unq.epers.woe.backend.razadao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.service.raza.ClaseInvalida;
import ar.edu.unq.epers.woe.backend.service.raza.RazaNoExistente;


import java.util.List;


public class RazaDAOTest {

    private RazaDao razaDAO = new RazaDao();
    private Raza raza;
    
	@Rule
	public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void crearModelo() {
        this.razaDAO.crearSetDatosIniciales();
        this.raza = new Raza().getRaza(1);
    }

    @Test
    public void luegoDeGuardarRazaSeObtienePrimerIdDisponible() {
        Integer nextId = this.raza.getId() + 1;
        assertEquals(nextId, new Integer(2));
    }

    @Test
    public void alRecuperarUnaRazaSeCreaUnaInstanciaConAtributosCorrectos() {
        Raza raza = this.raza.getRaza(1);
        assertEquals(raza.getId(), new Integer(1));
        assertEquals(raza.getNombre(), this.raza.getNombre());
        assertEquals(raza.getClases(), this.raza.getClases());
        assertEquals(raza.getEnergiaInicial(), this.raza.getEnergiaInicial());
        assertEquals(raza.getPeso(), this.raza.getPeso());
        assertEquals(raza.getUrlFoto(), this.raza.getUrlFoto());
        assertEquals(raza.getCantidadPersonajes(), this.raza.getCantidadPersonajes());
    }

    @Test
    public void alRecuperarLaListaDeRazasEstanOrdenadasAlfabeticamente() {
        List<Raza> razas = this.raza.getAllRazas();
        assertEquals(razas.get(0).getNombre(), "xRaza1");
        assertEquals(razas.get(1).getNombre(), "yRaza2");
    }

    @Test
    public void alRecuperarLaListaDeRazasLaCantidadCoincideConLasAlmacenadas() {
        List<Raza> razas = this.raza.getAllRazas();
        assertTrue(!razas.isEmpty());
        assertEquals(2, razas.size());
    }

    @Test
    public void alCrearPjSeIncrementanPjsDeLaRaza() {
        Integer cantPrevia = this.raza.getCantidadPersonajes();
        this.raza.crearPersonaje(this.raza.getId(), "Seiya", Clase.SACERDOTE);
        assertEquals(this.raza.getRaza(this.raza.getId()).getCantidadPersonajes(), cantPrevia + 1);
    }
    
    @Test 
    public void testAlRecuperarRazaConIdInvalidoOcurreExcepcion() {
		int idInvalido = 123456;
		thrown.expect(RazaNoExistente.class);
		this.razaDAO.recuperarRaza(idInvalido, this.raza);
    }
    
    @Test
    public void alCrearPersonajeConClaseQueNoCorrespondeOcurreExcepcion() {
    	Clase claseNoIncluidaEnRaza = Clase.PALADIN;
    	assertFalse( this.raza.getClases().contains(claseNoIncluidaEnRaza) );
    	
    	thrown.expect(ClaseInvalida.class);
		this.raza.crearPersonaje(this.raza.getId(), "Seiya", claseNoIncluidaEnRaza);
    }


    @After
    public void tearDown() {
        this.razaDAO.eliminarDatos();
    }
}