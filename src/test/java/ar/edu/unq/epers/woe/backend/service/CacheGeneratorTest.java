package ar.edu.unq.epers.woe.backend.service;

import ar.edu.unq.epers.woe.backend.hibernateDAO.*;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.evento.CompraItem;
import ar.edu.unq.epers.woe.backend.model.evento.Evento;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Gimnasio;
import ar.edu.unq.epers.woe.backend.model.lugar.Taberna;
import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;
import ar.edu.unq.epers.woe.backend.model.mision.*;
import ar.edu.unq.epers.woe.backend.model.personaje.*;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;
import ar.edu.unq.epers.woe.backend.mongoDAO.EventoMongoDAO;
import ar.edu.unq.epers.woe.backend.redisDAO.RedisDAO;
import ar.edu.unq.epers.woe.backend.service.cache.CacheGenerator;
import ar.edu.unq.epers.woe.backend.service.data.ServiciosDB;
import ar.edu.unq.epers.woe.backend.service.leaderboard.LeaderboardService;
import ar.edu.unq.epers.woe.backend.service.lugar.LugarService;
import ar.edu.unq.epers.woe.backend.service.personaje.PersonajeService;
import ar.edu.unq.epers.woe.backend.service.raza.ServiciosRaza;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class CacheGeneratorTest {

    private CacheGenerator cg = new CacheGenerator();
    private RedisDAO rd = new RedisDAO();
    private ServiciosDB dbServ = new ServiciosDB();
    private EventoMongoDAO emd = new EventoMongoDAO();
    private ServiciosRaza sr = new ServiciosRaza();
    private Raza r;
    private HibernatePersonajeDAO pjhd = new HibernatePersonajeDAO();
    private Personaje pj;
    private LeaderboardService ls = new LeaderboardService();
    private PersonajeService pjs = new PersonajeService();
    private Item i;
    private int idItem;
    private HibernateItemDAO ihd = new HibernateItemDAO();
    private PersonajeService serviceP = new PersonajeService();
    private HibernateLugarDAO ild = new HibernateLugarDAO();
    private LugarService lr = new LugarService();
    private LugarService lsv = new LugarService();
    private HibernateMisionDAO imd = new HibernateMisionDAO();

    @Before
    public void setUp() {
        this.rd.clear();
        this.dbServ.eliminarDatos();
        this.dbServ.eliminarDatosNeo4j();
        this.emd.eliminarDatos();

        Set<Clase> cls = new HashSet<>();
        cls.add(Clase.MAGO);
        Set<Atributo> ats = new HashSet<>();
        ats.add(new Fuerza(50f));
        i = new Item("plateMail", "torso", "espada", cls, new Requerimiento(),
                5, 1, ats);
        Runner.runInSession(() -> { this.ihd.guardar(i);
            this.idItem = ihd.recuperarPorNombre(i.getNombre()).getIdItem(); return null; });

        this.r = new Raza("r1");
        this.r.setClases(cls);
        sr.crearRaza(this.r);
        this.pj = new Personaje(this.r, "tstPJ0", Clase.MAGO);
        this.pj.getMochila().agregarItem(i);
        Runner.runInSession(() -> {
            this.pjhd.guardar(this.pj); return null; });
    }

    @Test
    public void puedeGuardarseYRecuperarseNombrePJDeLaCache() {
        this.cg.setCacheMasFuerte(this.pj.getNombre());
        assertEquals(this.pj.getNombre(), this.cg.getMasFuerte().getNombre());
    }

    @Test
    public void seGuardaYRecuperaNombreDePjDeMayorDanho() {
        this.cg.setCacheMasFuerte(this.pj.getNombre());
        Personaje pjii = new Personaje(this.r, "tstPJ1", Clase.MAGO);
        pjii.getAtributo(Fuerza.class).setValor(200f);
        Runner.runInSession(() -> {
            this.pjhd.guardar(pjii); return null; });
        this.cg.setCacheMasFuerte(pjii.getNombre());
        assertEquals(pjii.getNombre(), this.cg.getMasFuerte().getNombre());
    }

    @Test
    public void siCambiaMasFuerteLuegoDeEquiparItemSeRecuperaElUltimoPj() {
        Personaje pjii = new Personaje(this.r, "tstPJ1", Clase.MAGO);
        pjii.getAtributo(Fuerza.class).setValor(20f);
        Runner.runInSession(() -> {
            this.pjhd.guardar(pjii); return null; });
        this.cg.setCacheMasFuerte(pjii.getNombre());
        this.serviceP.equipar(this.pj.getNombre(), this.idItem);
        assertEquals(this.pj.getNombre(), this.cg.getMasFuerte().getNombre());
    }

    @Test
    public void siCambiaMasFuerteLuegoDeSubirDeNivelAlCombatirSeRecuperaElUltimoPj() {
        this.cg.setCacheMasFuerte(this.pj.getNombre());
        Gimnasio gim = new Gimnasio("tstGim0");
        Runner.runInSession(() -> { this.ild.guardar(gim); return null; });
        Personaje pjii = new Personaje(this.r, "tstPJ1", Clase.MAGO);
        pjii.setExp(99);
        pjii.cambiarDeLugar(gim);
        pjii.setVida(new Vida(10f));
        Runner.runInSession(() -> {
            this.pjhd.guardar(pjii); return null; });
        this.lr.moverPermisivo(this.pj.getNombre(), gim.getNombre());
        ResultadoCombate rc = this.serviceP.combatir(this.pj.getNombre(), pjii.getNombre());
        assertEquals(pjii.getNombre(), this.cg.getMasFuerte().getNombre());
    }

    @Test
    public void siCambiaMasFuerteLuegoDeSubirDeNivelAlCompletarMisionIrALugarSeRecuperaElUltimoPj() {
        this.cg.setCacheMasFuerte(this.pj.getNombre());
        Taberna t = new Taberna("tab99");
        Tienda t1 = new Tienda("tie100");
        String tipoCamino = "terrestre";
        this.lsv.crearUbicacion(t);
        this.lsv.crearUbicacion(t1);
        this.lsv.conectar(t.getNombre(), t1.getNombre(), tipoCamino);
        IrALugar ial = new IrALugar("tstIAL", new Recompensa(new ArrayList<Item>(), 110, 0f), t1);
        Personaje pjn = new Personaje(this.r, "tstPJ1", Clase.MAGO);
        pjn.aceptarMision(ial);
        pjn.setBilletera(500f);
        pjn.cambiarDeLugar(t);
        Runner.runInSession(() -> { this.pjhd.guardar(pjn); return null; });
        this.lsv.mover(pjn.getNombre(), t1.getNombre());
        assertEquals(pjn.getNombre(), this.cg.getMasFuerte().getNombre());
    }

    @Test
    public void siCambiaMasFuerteLuegoDeSubirDeNivelAlCompletarMisionObtenerItemSeRecuperaElUltimoPj() {
        this.cg.setCacheMasFuerte(this.pj.getNombre());
        ObtenerItem oi = new ObtenerItem("tstOI", new Recompensa(new ArrayList<Item>(), 110, 0f), this.i);
        Tienda t = new Tienda("tie2");
        HashSet<Item> is = new HashSet<Item>();
        is.add(this.i);
        t.setItems(is);
        Runner.runInSession(() -> { this.ild.guardar(t); return null; });
        Personaje pjn = new Personaje(this.r, "tstPJ1", Clase.MAGO);
        pjn.aceptarMision(oi);
        pjn.setBilletera(500f);
        pjn.cambiarDeLugar(t);
        Runner.runInSession(() -> { this.pjhd.guardar(pjn); return null; });
        this.lsv.comprarItem("tstPJ1", this.idItem);
        assertEquals(pjn.getNombre(), this.cg.getMasFuerte().getNombre());
    }

    @Test
    public void siCambiaMasFuerteLuegoDeSubirDeNivelAlCompletarMisionVencerASeRecuperaElUltimoPj() {
        this.cg.setCacheMasFuerte(this.pj.getNombre());
        Gimnasio gim = new Gimnasio("tstGim0");
        Mision mis = new VencerA("tstMision", new Recompensa(new ArrayList<Item>(), 110, 0f), this.pj, 1);
        Runner.runInSession(() -> {
            this.ild.guardar(gim);
            this.imd.guardar(mis);
            return null;
        });
        Personaje pjii = new Personaje(this.r, "tstPJ1", Clase.MAGO);
        pjii.cambiarDeLugar(gim);
        pjii.setVida(new Vida(10f));
        pjii.getAtributo(Fuerza.class).setValor(200f);
        pjii.aceptarMision(mis);
        Runner.runInSession(() -> {
            this.pjhd.guardar(pjii);
            return null;
        });
        this.lr.moverPermisivo(this.pj.getNombre(), gim.getNombre());
        ResultadoCombate rc = this.serviceP.combatir(this.pj.getNombre(), pjii.getNombre());
        assertEquals(pjii.getNombre(), this.cg.getMasFuerte().getNombre());
    }
    
    @Test
    public void seGuardanYRecuperanUnaListaDeEventos() {
    	
    	String nombreP = "tstPersonaje";
		Evento e1 = new CompraItem(nombreP, "tstLugar", "tstIt", 0);
    	Evento e2 = new CompraItem(nombreP, "tstLugar", "tstIt2", 0);
    	emd.save(e1); emd.save(e2);
    	
    	List<Evento> eventos = emd.getByPersonaje(nombreP);

    	cg.setCacheEventosDeLugar(nombreP, eventos);
    	
    	List<Evento> eventosRec = cg.getEventosDeLugar(nombreP);
    	assertEquals( 2, eventosRec.size() );

    }

}