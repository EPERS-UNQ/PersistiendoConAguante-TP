package ar.edu.unq.epers.woe.backend.model.personaje;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.raza.Clase;

public class RetribucionAdicionalPersonajeTest {

	RetribucionAdicionalPersonaje retAdicional;
	Personaje p;
	
	@Before
	public void setUp(){
		p = new Personaje(null, "Sample", null );
		// Todos los atributos de un personaje comienzan con 1f
	}

	@Test
	public void atributosSubenSegunClasePicaro() {
		p.setClase(Clase.PICARO);
		RetribucionAdicionalPersonaje.incrementarSegunClase(p);

		assertTrue( p.getAtributo(Fuerza.class).getValor().equals(1.06f) );		
	}
	
	@Test
	public void atributosSubenSegunClasePaladin() {
		p.setClase(Clase.PALADIN);
		RetribucionAdicionalPersonaje.incrementarSegunClase(p);
		
		assertTrue( p.getAtributo(Vida.class).getValor().equals(1.02f) );		
		assertTrue( p.getAtributo(Fuerza.class).getValor().equals(1.04f) );
	}

	
	@Test
	public void atributosSubenSegunClaseCazador() {
		p.setClase(Clase.CAZADOR);
		RetribucionAdicionalPersonaje.incrementarSegunClase(p);

		assertTrue( p.getAtributo(Vida.class).getValor().equals(1.05f) );		

	}
	
	
	@Test
	public void atributosSubenSegunClaseMago() {
		p.setClase(Clase.MAGO);
		RetribucionAdicionalPersonaje.incrementarSegunClase(p);
		
		assertTrue( p.getAtributo(Fuerza.class).getValor().equals(1.02f) );
		assertTrue( p.getAtributo(Destreza.class).getValor().equals(1.09f) );		
		assertTrue( p.getAtributo(Vida.class).getValor().equals(1.05f) );		
	}
	
	@Test
	public void atributosSubenSegunClaseMonje() {
		p.setClase(Clase.MONJE);
		RetribucionAdicionalPersonaje.incrementarSegunClase(p);

		assertTrue( p.getAtributo(Fuerza.class).getValor().equals(1.02f) );
		assertTrue( p.getAtributo(Destreza.class).getValor().equals(1.09f) );
	}
	
	@Test
	public void atributosSubenSegunClaseGuerrero() {
		p.setClase(Clase.GUERRERO);
		RetribucionAdicionalPersonaje.incrementarSegunClase(p);
		
		assertTrue( p.getAtributo(Fuerza.class).getValor().equals(1.04f) );
		assertTrue( p.getAtributo(Destreza.class).getValor().equals(1.01f) );
	}
	
	@Test
	public void atributosSubenSegunClaseCaballero() {
		p.setClase(Clase.CABALLERO);
		RetribucionAdicionalPersonaje.incrementarSegunClase(p);
		
		assertTrue( p.getAtributo(Fuerza.class).getValor().equals(1.1f) );
		assertTrue( p.getAtributo(Destreza.class).getValor().equals(1.03f) );
		assertTrue( p.getAtributo(Vida.class).getValor().equals(1.08f) );
	}
	
	@Test
	public void atributosSubenSegunClaseSacerdote() {
		p.setClase(Clase.SACERDOTE);
		RetribucionAdicionalPersonaje.incrementarSegunClase(p);

		assertTrue( p.getAtributo(Vida.class).getValor().equals(1.02f) );
	}
	
	@Test
	public void atributosSubenSegunClaseDruida() {
		p.setClase(Clase.DRUIDA);
		RetribucionAdicionalPersonaje.incrementarSegunClase(p);

		assertTrue( p.getAtributo(Vida.class).getValor().equals(1.06f) );
	}
	
	
	@Test
	public void atributosSubenSegunClaseBrujo() {
		p.setClase(Clase.BRUJO);
		RetribucionAdicionalPersonaje.incrementarSegunClase(p);
		
		assertTrue( p.getAtributo(Destreza.class).getValor().equals(1.02f) );
	}
}
