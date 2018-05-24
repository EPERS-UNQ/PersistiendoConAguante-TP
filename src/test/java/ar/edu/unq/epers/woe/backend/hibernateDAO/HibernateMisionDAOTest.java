package ar.edu.unq.epers.woe.backend.hibernateDAO;

import ar.edu.unq.epers.woe.backend.model.lugar.Taberna;
import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;
import ar.edu.unq.epers.woe.backend.model.mision.IrALugar;
import ar.edu.unq.epers.woe.backend.model.mision.Mision;
import ar.edu.unq.epers.woe.backend.model.mision.Recompensa;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HibernateMisionDAOTest {

    private HibernateMisionDAO imd = new HibernateMisionDAO();
    private HibernateLugarDAO ild = new HibernateLugarDAO();

    @Before
    public void crearModelo() {
        SessionFactoryProvider.destroy();

        Taberna t1 = new Taberna("tab0");
        Tienda t2 = new Tienda("tie1");
        Runner.runInSession(() -> {
            this.ild.guardar(t1);
            return null;
        });
        Runner.runInSession(() -> {
            this.ild.guardar(t2);
            return null;
        });

        Mision m0 = new IrALugar("ia1", new Recompensa(), t1);
        Mision m1 = new IrALugar("ia2", new Recompensa(), t2);

        Runner.runInSession(() -> {
            this.imd.guardar(m0);
            this.imd.guardar(m1);
            return null;
        });
    }

    @Test
    public void seRecuperaMisionDeIrALugarCorrectamente() {
        Mision misionr0 = Runner.runInSession(() -> {
            return this.imd.recuperar("ia1"); });
        Mision misionr1 = Runner.runInSession(() -> {
            return this.imd.recuperar("ia2"); });
        assertTrue(misionr0.esIrALugar());
        assertTrue(misionr1.esIrALugar());
        assertEquals(misionr0.getNombre(), "ia1");
        assertEquals(misionr1.getNombre(), "ia2");
    }
}
