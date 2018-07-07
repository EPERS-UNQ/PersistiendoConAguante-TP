package ar.edu.unq.epers.woe.backend.service;

import ar.edu.unq.epers.woe.backend.hibernateDAO.*;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.evento.CompraItem;
import ar.edu.unq.epers.woe.backend.model.evento.Evento;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Gimnasio;
import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
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
import ar.edu.unq.epers.woe.backend.service.feed.FeedService;
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
    private Item i;
    private int idItem;
    private HibernateItemDAO ihd = new HibernateItemDAO();
    private PersonajeService serviceP = new PersonajeService();
    private HibernateLugarDAO ild = new HibernateLugarDAO();
    private LugarService lsv = new LugarService();
    private HibernateMisionDAO imd = new HibernateMisionDAO();
	private FeedService fServ = new FeedService();

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
        Lugar l = new Tienda("tstLugar0"); lsv.crearUbicacion(l);
        this.pj = new Personaje(this.r, "tstPJ0", Clase.MAGO);
        this.pj.setLugar(l);
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
        assertEquals(pjii.getNombre(), this.ls.masFuerte().getNombre());
    }

    @Test
    public void siCambiaMasFuerteLuegoDeEquiparItemSeInvalidaCache() {
        Personaje pjii = new Personaje(this.r, "tstPJ1", Clase.MAGO);
        Tienda lugar = new Tienda("tstT"); lsv.crearUbicacion(lugar);
		pjii.setLugar( lugar );
        pjii.getAtributo(Fuerza.class).setValor(20f);
        Runner.runInSession(() -> {
            this.pjhd.guardar(pjii); return null; });
        this.cg.setCacheMasFuerte(pjii.getNombre());
        this.serviceP.equipar(this.pj.getNombre(), this.idItem);
        assertNull(this.cg.getMasFuerte());
    }

    @Test
    public void siCambiaMasFuerteLuegoDeSubirDeNivelAlCombatirSeInvalidaCache() {
        this.cg.setCacheMasFuerte(this.pj.getNombre());
        Gimnasio gim = new Gimnasio("tstGim0");
        Runner.runInSession(() -> { this.ild.guardar(gim); return null; });
        Personaje pjii = new Personaje(this.r, "tstPJ1", Clase.MAGO);
        pjii.setExp(99);
        pjii.cambiarDeLugar(gim);
        pjii.setVida(new Vida(10f));
        Runner.runInSession(() -> {
            this.pjhd.guardar(pjii); return null; });
        this.lsv.moverPermisivo(this.pj.getNombre(), gim.getNombre());
        ResultadoCombate rc = this.serviceP.combatir(this.pj.getNombre(), pjii.getNombre());
        assertNull(this.cg.getMasFuerte());
    }

    @Test
    public void siCambiaMasFuerteLuegoDeSubirDeNivelAlCompletarMisionIrALugarSeInvalidaCache() {
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
        assertNull(this.cg.getMasFuerte());
    }

    @Test
    public void siCambiaMasFuerteLuegoDeSubirDeNivelAlCompletarMisionObtenerItemSeInvalidaCache() {
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
        assertNull(this.cg.getMasFuerte());
    }

    @Test
    public void siCambiaMasFuerteLuegoDeSubirDeNivelAlCompletarMisionVencerASeInvalidaCache() {
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
        this.lsv.moverPermisivo(this.pj.getNombre(), gim.getNombre());
        ResultadoCombate rc = this.serviceP.combatir(this.pj.getNombre(), pjii.getNombre());
        assertNull(this.cg.getMasFuerte());
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
    
    @Test
    public void seInvalidaLoCacheadoDeUnLugar() {
    	String nombreL = "tstLugar";
		Evento e1 = new CompraItem("tstPersonaje", nombreL, "tstIt", 0);
    	Evento e2 = new CompraItem("tstPersonaje", nombreL, "tstIt2", 0);
    	emd.save(e1); emd.save(e2);
    	
    	List<Evento> eventos = new ArrayList<Evento>();
    	eventos.add(e1); eventos.add(e2);
    	
    	cg.setCacheEventosDeLugar(nombreL, eventos);
    	cg.invalidarClaveLugar(nombreL);
    	
    	assertNull( cg.getEventosDeLugar(nombreL) );
    }
    
    @Test
    public void conCacheVaciaUnFeedServiceObtieneEventosDeUnLugar() {
    	String nombreL = "tstLugar";
    	
    	FeedService feedS = new FeedService();    	
    	assertNull( cg.getEventosDeLugar(nombreL) );
    	
		Evento e1 = new CompraItem("tstPersonaje", nombreL, "tstIt", 0);
    	Evento e2 = new CompraItem("tstPersonaje", nombreL, "tstIt2", 0);
    	emd.save(e1); emd.save(e2);
    	feedS.feedLugar(nombreL); //se cachea
    	
    	assertEquals( 2, cg.getEventosDeLugar(nombreL).size() );
    }

    @Test
    public void siSeCreaUnNuevoEventoVenderItemSeInvalidaLoGuardadoDeEseLugar() {
    	String nombreP = "tstp";
    	String nombreL = "testL";
    	Lugar l = new Tienda(nombreL); lsv.crearUbicacion(l); 
    	Personaje p = new Personaje(null, nombreP, null); 
    	p.setLugar(l); p.setBilletera(1000f);
    	Runner.runInSession(()->{ pjhd.guardar(p); return null;});
    	
    	lsv.comprarItem(nombreP, idItem); // evento CompraItem
    	
    	fServ.feedLugar(nombreL);
    	assertEquals( 1, cg.getEventosDeLugar(nombreL).size() );    	
    	
    	lsv.venderItem(nombreP, idItem); // evento VentaItem
    	assertNull( cg.getEventosDeLugar(nombreL) );
    }
    
    @Test
    public void eventoArriboInvalidaCacheGuardada() {
       	String nombreP = "tstp";
        Taberna t1 = new Taberna("testTab1");
        Tienda t2 = new Tienda("testT2");
        lsv.crearUbicacion(t1); lsv.crearUbicacion(t2);
        lsv.conectar(t1.getNombre(), t2.getNombre(), "terrestre");
    	
    	Personaje p = new Personaje(null, nombreP, null);
    	p.setLugar(t1); p.setBilletera(1000f);
    	Runner.runInSession(()->{ 
    		pjhd.guardar(p); return null;});

    	fServ.feedLugar(t1.getNombre()); // no hay eventos pero se guarda en cache
    	assertTrue( cg.getEventosDeLugar(t1.getNombre()).isEmpty() );
    	
    	lsv.mover(nombreP, t2.getNombre()); // nuevo evento invalida cache
    	assertNull( cg.getEventosDeLugar(t1.getNombre()) );
    	
    	fServ.feedLugar(t1.getNombre()); // nuevo llamado guarda en cache
    	assertFalse( cg.getEventosDeLugar(t1.getNombre()).isEmpty() );
    }
    
    @Test
    public void unEventoMisionCompletadaInvalidaCacheGuardadaPreviamente() {
    	
       	String nombreP = "tstp";
        Taberna t1 = new Taberna("testTab1");
        Tienda t2 = new Tienda("testT2");
        String nombreMis = "tstMision1";
		IrALugar m = new IrALugar(nombreMis, new Recompensa(), t2);
        lsv.crearUbicacion(t1); lsv.crearUbicacion(t2);
        lsv.conectar(t1.getNombre(), t2.getNombre(), "terrestre");
		t1.agregar( m );
    	
    	Personaje p = new Personaje(null, nombreP, null);
    	p.setLugar(t1); p.setBilletera(1000f);
    	Runner.runInSession(()->{ 
    		pjhd.guardar(p); imd.guardar(m); return null;});

    	lsv.aceptarMision(nombreP, nombreMis);
    	fServ.feedLugar(t1.getNombre()); // guarda en cache evento misionAceptada
    	assertEquals( 1, cg.getEventosDeLugar(t1.getNombre()).size() );
    	
    	// nuevo evento MisionCompletada invalida cache
    	lsv.mover(nombreP, t2.getNombre());
    	assertNull( cg.getEventosDeLugar(t1.getNombre()) );

    }
    
    @Test
    public void AlCombatirSeCreaEventoGanadorYSiSePideFeedServiceSeGuardaEventoEnCache() {
    	String nombreL = "testGim";
    	Lugar gim = new Gimnasio(nombreL); lsv.crearUbicacion(gim); 
    	Personaje p = new Personaje(r, "testP1", Clase.MAGO); p.setLugar(gim); 
    	Personaje p2 = new Personaje(r, "testP2", Clase.MAGO); p2.setLugar(gim);
    	Runner.runInSession(()->{ 
    		pjhd.guardar(p); pjhd.guardar(p2); return null;});
    	
    	serviceP.combatir(p.getNombre(), p2.getNombre());
    	fServ.feedLugar(nombreL); // guarda eventos en cache
    	assertEquals( 1, cg.getEventosDeLugar(nombreL).size() );
    	
    	//evento nuevo invalida cache
    	serviceP.combatir(p.getNombre(), p2.getNombre());
    	assertNull( cg.getEventosDeLugar(nombreL) );
    	
    	//nuevo llamado guarda en cache
    	fServ.feedLugar(nombreL);
    	assertEquals( 2, cg.getEventosDeLugar(nombreL).size() );
    }
    
}
