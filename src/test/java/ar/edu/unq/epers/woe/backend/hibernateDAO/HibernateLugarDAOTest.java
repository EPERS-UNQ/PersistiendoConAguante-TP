package ar.edu.unq.epers.woe.backend.hibernateDAO;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;
import ar.edu.unq.epers.woe.backend.model.personaje.Atributo;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;


public class HibernateLugarDAOTest {

    private HibernateLugarDAO lugarDAO;
    private Tienda tienda;
    private Item i;
    private Item ii;

    @Before
    public void setUp() {
        lugarDAO = new HibernateLugarDAO();
        tienda = new Tienda("tstTienda");
        Set<Clase> cls = new HashSet<>();
        Set<Atributo> ats = new HashSet<>();
        this.i = new Item("plateMail", "torso", "espada", cls, new Requerimiento(),
                5, 1, ats);
        this.ii = new Item("plateMail", "torso", "espada", cls, new Requerimiento(),
                500, 1, ats);
    }

    @Test
    public void testSeRecuperaLugar() {
        lugarDAO.guardar(this.tienda);
        assertEquals(lugarDAO.recuperar(this.tienda.getNombre()).getNombre(), "tstTienda");
    }

    @Test
    public void testSeRecuperaElSetDeItemsCorrectamente() {
        Set<Item> items = new HashSet<Item>();
        items.add(this.i);
        items.add(this.ii);
        Tienda tb = new Tienda("t2");
        tb.setItems(items);
        lugarDAO.guardar(tb);
        Tienda tr = (Tienda) lugarDAO.recuperar(tb.getNombre());
        assertTrue(items.containsAll(tr.getItems()));
    }

    @After
    public void cleanup() {
        SessionFactoryProvider.destroy();
    }


}