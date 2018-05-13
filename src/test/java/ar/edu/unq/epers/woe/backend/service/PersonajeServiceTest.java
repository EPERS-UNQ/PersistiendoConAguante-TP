package ar.edu.unq.epers.woe.backend.service;

import static org.junit.Assert.*;
import ar.edu.unq.epers.woe.backend.model.personaje.Atributo;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;
import org.junit.Before;
import org.junit.Test;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.service.personaje.PersonajeService;
import java.util.HashSet;
import java.util.Set;

public class PersonajeServiceTest {

	PersonajeService serviceP = new PersonajeService() ;
	private Personaje pj;
	private Item i;

	@Before
	public void crearModelo() {
		pj = new Personaje(new Raza("tstRaza"), "tstPJ0", Clase.MAGO);
		Set<Clase> cls = new HashSet<>();
		Set<Atributo> ats = new HashSet<>();
		i = new Item("plateMail", "torso", "espada", cls, new Requerimiento(),
				5, 1, ats);
	}
	
	@Test
	public void unServicePersonajePuedeEquiparDeUnItemAUnPersonaje() {
		this.pj.getMochila().agregarItem(i);
		serviceP.equipar(this.pj, this.i  );
		assertEquals(this.pj.getInventario().getEnUbicacion("torso").getItem(), this.i);
	}

	@Test
	public void alEquiparUnItemSeIncrementaAtributo() {
		HashSet<Atributo> atts = new HashSet<>();
		atts.add(new Vida(5f));
		Item i = new Item(null, "torso", null, null, new Requerimiento(),
				0, 0, atts);
		this.pj.getMochila().agregarItem(i);
		this.serviceP.equipar(this.pj, i);
		assertEquals(this.pj.getAtributo(Vida.class).getValor(), new Float(6f));
	}

}
