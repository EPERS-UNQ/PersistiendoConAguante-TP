import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Set;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.razadao.RazaDao;


public class RazaDAOTest {

    private RazaDao razaDAO = new RazaDao();
    private Raza raza;

    @Before
    public void crearModelo() {
        this.raza = new Raza();
        Set<Clase> clases = new java.util.HashSet<Clase>();
        clases.add(Clase.SACERDOTE);
        clases.add(Clase.MAGO);

        this.raza.setNombre("raza2");
        this.raza.setAltura(55);
        this.raza.setClases(clases);
        this.raza.setEnergiaIncial(10);
        this.raza.setPeso(50);
        this.raza.setUrlFoto("url_dest");
        this.raza.setCantidadPersonajes(0);
    }

    @Test
    public void al_guardar_primera_raza_se_obtiene_primerId_disponible() {
        this.razaDAO.crearRaza(this.raza);
        assertEquals(this.razaDAO.nextId(), new Integer(2));
    }

    @After
    public void tearDown() {
        this.razaDAO.deleteAll();
    }
}