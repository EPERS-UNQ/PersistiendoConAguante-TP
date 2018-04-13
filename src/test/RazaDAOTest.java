import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.razadao.RazaDao;

import java.util.List;


public class RazaDAOTest {

    private RazaDao razaDAO = new RazaDao();
    private Raza raza;

    @Before
    public void crearModelo() {
        this.razaDAO.crearSetDatosIniciales();
        this.raza = new Raza().getRaza(1);
    }

    @Test
    public void luego_de_guardar_raza_se_obtiene_primerId_disponible() {
        assertEquals(this.razaDAO.nextId(), new Integer(3));
    }

    @Test
    public void al_recuperar_una_raza_se_crea_una_instancia_con_atributos_correctos() {
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
    public void al_recuperar_la_lista_de_razas_estan_ordenadas_alfabeticamente() {
        List<Raza> razas = this.raza.getAllRazas();
        assertEquals(razas.get(0).getNombre(), "xRaza1");
        assertEquals(razas.get(1).getNombre(), "yRaza2");
    }

    @Test
    public void al_crear_pj_se_incrementan_pjs_de_la_raza() {
        Integer cantPrevia = this.raza.getCantidadPersonajes();
        this.raza.crearPersonaje(this.raza.getId(), "Seiya", Clase.CABALLERO);
        assertEquals(this.raza.getRaza(this.raza.getId()).getCantidadPersonajes(), cantPrevia + 1);
    }

    @After
    public void tearDown() {
        this.razaDAO.deleteAll();
    }
}