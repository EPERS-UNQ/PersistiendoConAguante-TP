package ar.edu.unq.epers.woe.backend.service;

import static org.junit.Assert.*;

import ar.edu.unq.epers.woe.backend.hibernateDAO.*;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.lugar.Gimnasio;
import ar.edu.unq.epers.woe.backend.model.personaje.Atributo;
import ar.edu.unq.epers.woe.backend.model.personaje.Fuerza;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;
import ar.edu.unq.epers.woe.backend.service.data.ServiciosDB;
import ar.edu.unq.epers.woe.backend.service.lugar.LugarService;
import ar.edu.unq.epers.woe.backend.service.raza.ServiciosRaza;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.service.personaje.PersonajeService;
import java.util.HashSet;
import java.util.Set;

public class PersonajeServiceTest {

	private PersonajeService serviceP = new PersonajeService();
	private HibernatePersonajeDAO pjhd = new HibernatePersonajeDAO();
	private HibernateRazaDAO rhd = new HibernateRazaDAO();
	private HibernateItemDAO ihd = new HibernateItemDAO();
	private HibernateLugarDAO ild = new HibernateLugarDAO();
	private ServiciosRaza sr = new ServiciosRaza();
	private LugarService lr = new LugarService();
	private Personaje pj;
	private Item i;
	private Raza r;
	private ServiciosDB dbServ = new ServiciosDB();
	private int idItem;

	@Before
	public void crearModelo() {
		SessionFactoryProvider.destroy();
		Set<Clase> cls = new HashSet<>();
		cls.add(Clase.MAGO);
		Set<Atributo> ats = new HashSet<>();
		ats.add(new Vida(5f));
		i = new Item("plateMail", "torso", "espada", cls, new Requerimiento(),
				5, 1, ats);
		Runner.runInSession(() -> { this.ihd.guardar(i);
		idItem = ihd.recuperarPorNombre(i.getNombre()).getIdItem(); return null; });
		this.r = new Raza("r1");
		this.r.setClases(cls);
		sr.crearRaza(this.r);
		this.pj = new Personaje(this.r, "tstPJ0", Clase.MAGO);
		this.pj.getMochila().agregarItem(i);
		Runner.runInSession(() -> {
			this.pjhd.guardar(this.pj); return null; });
		}
	
	@Test
	public void unServicePersonajePuedeEquiparDeUnItemAUnPersonaje() {
		
		serviceP.equipar(this.pj.getNombre(), idItem);
		Personaje pjr = Runner.runInSession(() -> { return pjhd.recuperar(this.pj.getNombre()); });
		assertEquals(pjr.getInventario().getEnUbicacion(this.i.getUbicacion()).getItem().getNombre(), this.i.getNombre());
		assertEquals(pjr.getInventario().getEnUbicacion(this.i.getUbicacion()).getItem().getAtributos().iterator().next().getValor(),
				this.i.getAtributos().iterator().next().getValor());
		assertEquals(pjr.getInventario().getEnUbicacion(this.i.getUbicacion()).getItem().getUbicacion(), this.i.getUbicacion());
	}

	@Test
	public void alEquiparUnItemSeIncrementaAtributo() {
		serviceP.equipar(this.pj.getNombre(), 1);
		Personaje pjr = Runner.runInSession(() -> { return pjhd.recuperar(this.pj.getNombre()); });
		assertEquals(pjr.getAtributo(Vida.class).getValor(), new Float(6f));
	}

	@Test
	public void alCombatirDosPjsQEstanEnUnGimnasioSeObtieneElResultadoCombate() {
		Gimnasio gim = new Gimnasio("tstGim0");
		Runner.runInSession(() -> { this.ild.guardar(gim); return null; });
		Personaje pjii = new Personaje(this.r, "tstPJ1", Clase.MAGO);
		pjii.cambiarDeLugar(gim);
		pjii.setVida(new Vida(10f));
		pjii.getAtributo(Fuerza.class).setValor(200f);
		Runner.runInSession(() -> {
			this.pjhd.guardar(pjii); return null; });
		this.lr.moverPermisivo(this.pj.getNombre(), gim.getNombre());
		ResultadoCombate rc = this.serviceP.combatir(this.pj.getNombre(), pjii.getNombre());
		assertTrue(rc.getClass().equals(ResultadoCombate.class));
		assertTrue(! rc.getDetalle().isEmpty());
		assertTrue(rc.getGanador().sosPersonaje());
		assertTrue(rc.getPerdedor().sosPersonaje());
	}

	@After
	public void cleanup() {
		//SessionFactoryProvider.destroy();
	}

}
