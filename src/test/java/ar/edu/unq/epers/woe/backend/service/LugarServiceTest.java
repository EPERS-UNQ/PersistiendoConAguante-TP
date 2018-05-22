package ar.edu.unq.epers.woe.backend.service;

import ar.edu.unq.epers.woe.backend.hibernateDAO.*;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Taberna;
import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;
import ar.edu.unq.epers.woe.backend.model.mision.IrALugar;
import ar.edu.unq.epers.woe.backend.model.mision.Mision;
import ar.edu.unq.epers.woe.backend.model.mision.Recompensa;
import ar.edu.unq.epers.woe.backend.model.personaje.Atributo;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;
import ar.edu.unq.epers.woe.backend.service.data.ServiciosDB;
import ar.edu.unq.epers.woe.backend.service.lugar.LugarService;
import ar.edu.unq.epers.woe.backend.service.personaje.PersonajeService;
import ar.edu.unq.epers.woe.backend.service.raza.ServiciosRaza;
import org.junit.Before;
import org.junit.Test;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class LugarServiceTest {

    private Personaje pj;
    private LugarService ls = new LugarService();
    private PersonajeService serviceP = new PersonajeService();
    private HibernatePersonajeDAO pjhd = new HibernatePersonajeDAO();
    private HibernateRazaDAO rhd = new HibernateRazaDAO();
    private HibernateItemDAO ihd = new HibernateItemDAO();
    private HibernateMisionDAO imd = new HibernateMisionDAO();
    private HibernateLugarDAO ild = new HibernateLugarDAO();
    private ServiciosRaza sr = new ServiciosRaza();
    private Item i;
    private Raza r;
    private ServiciosDB dbServ = new ServiciosDB();
    private int idItem;
    private Taberna tab;


    @Before
    public void crearModelo() {
        SessionFactoryProvider.destroy();

        Taberna t1 = new Taberna("tab0");
        Tienda t2 = new Tienda("tie1");
        Runner.runInSession(() -> { this.ild.guardar(t1); return null; });
        Runner.runInSession(() -> { this.ild.guardar(t2); return null; });

        HashSet<Mision> mis = new HashSet<Mision>();
        mis.add(new IrALugar("ia1", new Recompensa(), t1));
        mis.add(new IrALugar("ia2", new Recompensa(), t2));
        this.tab = new Taberna("tab1", mis);
        Runner.runInSession(() -> { this.ild.guardar(this.tab); return null; });

        Set<Clase> cls = new HashSet<>();
        cls.add(Clase.MAGO);
        Set<Atributo> ats = new HashSet<>();
        ats.add(new Vida(5f));
        i = new Item("plateMail", "torso", "espada", cls, new Requerimiento(),
                     5, 1, ats);
        Runner.runInSession(() -> { this.ihd.guardar(i);
        this.idItem = ihd.recuperarPorNombre(i.getNombre()).getIdItem(); return null; });

        this.r = new Raza("r1");
        this.r.setClases(cls);
        sr.crearRaza(this.r);

        this.pj = new Personaje(this.r, "tstPJ0", Clase.MAGO);
        this.pj.getMochila().agregarItem(i);
        this.pj.setLugar(this.tab);
        Runner.runInSession(() -> { this.pjhd.guardar(this.pj); return null; });

    }

    @Test
    public void pjEnUnaTabernaVeMisionesDisponibles() {
        List<Mision> lmis = ls.listarMisiones("tstPJ0");
        IrALugar ia0 = (IrALugar) lmis.get(0);
        IrALugar ia1 = (IrALugar) lmis.get(1);
        assertEquals(lmis.size(), 2);
        assertTrue(lmis.get(0).esIrALugar());
        assertTrue(lmis.get(1).esIrALugar());
        assertTrue((ia0.getDestino().getClass() == Taberna.class) || (ia0.getDestino().getClass() == Tienda.class));
        assertTrue((ia1.getDestino().getClass() == Taberna.class) || (ia1.getDestino().getClass() == Tienda.class));
    }

    @Test
    public void pjEnUnaTabernaPuedeAceptarMisionDisponible() {
        this.ls.aceptarMision("tstPJ0", "ia1");
        Personaje pjr = Runner.runInSession(() -> {
            return pjhd.recuperar(this.pj.getNombre());
        });
        assertTrue(pjr.getMisionesAceptadas().contains("ia1"));
        assertTrue(pjr.getMisionesEnCurso().iterator().next().esIrALugar());
    }

    @Test
    public void alMoverAlPJAUnLugarYPreguntarleSuLugarRetornaElNuevoLugar() {
        this.ls.mover(this.pj.getNombre(), "tie1");
        Personaje pjr = Runner.runInSession(() -> {
            return pjhd.recuperar(this.pj.getNombre());
        });
        assertEquals(pjr.getLugar().getClass(), Tienda.class);
        assertEquals(pjr.getLugar().getNombre(), "tie1");
    }

    @Test
    public void pjQEstaEnUnaTiendaPuedeConsultarItemsDisponibles() {
        Tienda t = new Tienda("tie2");
        HashSet<Item> is = new HashSet<Item>();
        is.add(this.i);
        t.setItems(is);
        Runner.runInSession(() -> { this.ild.guardar(t); return null; });
        this.ls.mover(this.pj.getNombre(), "tie2");
        List<Item> li = this.ls.listarItems(this.pj.getNombre());
        assertEquals(li.size(), 1);
        assertEquals(li.iterator().next().getNombre(), this.i.getNombre());
        assertEquals(li.iterator().next().getAtributos().iterator().next().getValor(),
                this.i.getAtributos().iterator().next().getValor());
    }

    @Test
    public void pjQEstaEnUnaTiendaYTieneDineroPuedeComprarItemDisponible() {
        Tienda t = new Tienda("tie2");
        HashSet<Item> is = new HashSet<Item>();
        is.add(this.i);
        t.setItems(is);
        Runner.runInSession(() -> { this.ild.guardar(t); return null; });
        Personaje pjn = new Personaje(this.r, "tstPJ1", Clase.MAGO);
        pjn.setBilletera(500f);
        pjn.setLugar(t);
        Runner.runInSession(() -> { this.pjhd.guardar(pjn); return null; });
        ls.comprarItem("tstPJ1", this.idItem);
        Personaje pjr = Runner.runInSession(() -> {
            return pjhd.recuperar(pjn.getNombre());
        });
        assertTrue(pjr.getBilletera().floatValue() == 495f);
        assertEquals(pjr.getMochila().getItems().iterator().next().getNombre(), this.i.getNombre());
        assertEquals(pjr.getMochila().getItems().iterator().next().getAtributos().iterator().next().getValor(),
                     this.i.getAtributos().iterator().next().getValor());
    }

    @Test
    public void pjQEstaEnUnaTiendaYVendeUnItemSeLeAcreditaElValorDeVenta() {
        this.ls.mover(this.pj.getNombre(), "tie1");
        this.ls.venderItem(this.pj.getNombre(), this.idItem);
        Personaje pjr = Runner.runInSession(() -> {
            return pjhd.recuperar(this.pj.getNombre());
        });
        assertTrue(pjr.getBilletera().floatValue() == 1f);
        assertFalse(pjr.getMochila().tieneElItem(this.i));
    }
}
