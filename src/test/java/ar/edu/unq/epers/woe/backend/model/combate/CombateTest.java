package ar.edu.unq.epers.woe.backend.model.combate;

import ar.edu.unq.epers.woe.backend.hibernateDAO.SessionFactoryProvider;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.monstruo.Monstruo;
import ar.edu.unq.epers.woe.backend.model.personaje.*;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;
import org.junit.Before;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CombateTest {

	private Raza r;
	private Personaje pj;
	private Combate combate = new Combate();

	@Before
	public void crearModelo() {
		SessionFactoryProvider.destroy();
		Set<Clase> cls = new HashSet<>();
		cls.add(Clase.MAGO);
		Set<Atributo> ats = new HashSet<>();
		ats.add(new Vida(5f));
		this.r = new Raza("r1");
		this.r.setClases(cls);
		this.pj = new Personaje(this.r, "tstPJ0", Clase.MAGO);
	}

	@Test
	public void danhoYDefensaSeCalculaCorrectamente() {
		float danhoTotal = this.pj.getDanhoTotal().getValor();
		float danhoAmortiguado = this.pj.calcularDanhoRecibido(new Danho(10f)).getValor();
		assertTrue(danhoTotal == 1.0001f);
		assertTrue(danhoAmortiguado == 9f);

	}
           
	@Test
	public void unCombateConDosPersonajesP1yP2ganaP2() {
		Personaje pjii = new Personaje(this.r, "tstPJ1", Clase.MAGO);
		pjii.setVida(new Vida(10f));
		pjii.getAtributo(Fuerza.class).setValor(200f);
		ResultadoCombate rc = this.combate.combatir(this.pj, pjii);
		Personaje ganador = (Personaje) rc.getGanador();
		assertTrue(rc.getClass().equals(ResultadoCombate.class));
		assertTrue(rc.getGanador().sosPersonaje());
		assertEquals(ganador.getNombre(), pjii.getNombre());
	}

	@Test
	public void unCombateConUnPersonajesyUnMonstruoMasFuerteGanaElMonstruo() {
		Monstruo monstruo = new Monstruo("tstMonstruo1", new Vida(500f),
				                          new Danho(100f), "dragon");
		Personaje pjii = new Personaje(this.r, "tstPJ1", Clase.MAGO);
		pjii.setVida(new Vida(10f));
		pjii.getAtributo(Fuerza.class).setValor(200f);
		ResultadoCombate rc = this.combate.combatir(monstruo, pjii);
		Monstruo ganador = (Monstruo) rc.getGanador();
		assertTrue(rc.getClass().equals(ResultadoCombate.class));
		assertTrue(rc.getGanador().sosMonstruo());
	}

	@Test
	public void pjConArmaHaceMasDanho() {
		Set<Clase> cls = new HashSet<>();
		cls.add(Clase.MAGO);
		Set<Atributo> ats = new HashSet<>();
		ats.add(new Danho(1f));
		Item i = new Item("espada0", "derecha", "espada", cls,
				          new Requerimiento(0, new HashSet<Atributo>()),1, 1, ats);
		Item ii = new Item("espada1", "izquierda", "espada", cls,
				           new Requerimiento(0, new HashSet<Atributo>()),1, 1, ats);
		Float danhoAnterior = this.pj.getDanhoTotal().getValor();
		this.pj.getInventario().setItemEnUnaUbicacion(i, this.pj);
		this.pj.getInventario().setItemEnUnaUbicacion(ii, this.pj);
		Personaje pjii = new Personaje(this.r, "tstPJ1", Clase.MAGO);
		ResultadoCombate rc = this.combate.combatir(this.pj, pjii);
		assertEquals(rc.getDetalle().iterator().next().getDanhoAtante().getValor(), this.pj.getDanhoTotal().getValor());
		assertTrue(rc.getDetalle().iterator().next().getDanhoAtante().getValor() > danhoAnterior);
	}

	@Test
	public void pjConArmaduraRecibeMenosDanho() {
		Set<Clase> cls = new HashSet<>();
		cls.add(Clase.MAGO);
		Set<Atributo> ats = new HashSet<>();
		ats.add(new Armadura(1f));
		Item i = new Item("armadura0", "torso", "armadura", cls,
				new Requerimiento(0, new HashSet<Atributo>()),1, 1, ats);
		Item ii = new Item("armadura1", "izquierda", "armadura", cls,
				new Requerimiento(0, new HashSet<Atributo>()),1, 1, ats);
		Float defAnterior = this.pj.calcularDanhoRecividoConDefensa(new Danho(1.0001f)).getValor();
		this.pj.getInventario().setItemEnUnaUbicacion(i, this.pj);
		this.pj.getInventario().setItemEnUnaUbicacion(ii, this.pj);
		Personaje pjii = new Personaje(this.r, "tstPJ1", Clase.MAGO);
		ResultadoCombate rc = this.combate.combatir(pjii, this.pj);
		assertEquals(rc.getDetalle().iterator().next().getDanhoRecibido().getValor(),
				     this.pj.calcularDanhoRecividoConDefensa(new Danho(1.0001f)).getValor());
		assertTrue(this.pj.calcularDanhoRecividoConDefensa(new Danho(1.0001f)).getValor() < defAnterior);
	}
}
