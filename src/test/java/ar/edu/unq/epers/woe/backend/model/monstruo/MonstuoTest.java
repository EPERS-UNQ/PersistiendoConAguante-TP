package ar.edu.unq.epers.woe.backend.model.monstruo;

import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import static org.junit.Assert.*;

import org.junit.Before;


public class MonstuoTest {
	Monstruo monstruo;
	
	@Before 
	public void setUp() {
		monstruo = new Monstruo("MonsterT", new Vida(10f), new Danho (10f), "hogro");

	}
	
	@Test
	public void unMonstuoRecienCreado() {
		assertTrue(monstruo.getDanhoTotal().getValor() == 10f);
		assertTrue(monstruo.getVida().getValor() == 10f);
		assertTrue(monstruo.getTipo() == "hogro");
	}
	
	@Test
	public void unMonstuoRecibeUnAtaqueConunDanhoDe5fYSeleRestanLaVidaSegunElDanhoRecibido() {
		monstruo.recibirAtaque(new Danho (5f));
		assertTrue(monstruo.getVida().getValor() == 5f);
	}

}
