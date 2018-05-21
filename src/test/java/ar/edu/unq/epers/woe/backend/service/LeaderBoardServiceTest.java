package ar.edu.unq.epers.woe.backend.service;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.edu.unq.epers.woe.backend.hibernateDAO.SessionFactoryProvider;
import ar.edu.unq.epers.woe.backend.model.combate.Luchador;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.service.leaderboard.LeaderboardService;

public class LeaderBoardServiceTest {

	List<ResultadoCombate> combates;
	Personaje ganador;
	Personaje perdedor;
	Raza r;
	Raza r2;
	TestService testServ;
	LeaderboardService leaderboardS;
	
	@Before
	public void setUp(){
		
		SessionFactoryProvider.destroy();//cleanup
		
		leaderboardS = new LeaderboardService();
		testServ = new TestService();
		
		r = new Raza();
		Set<Clase> clases = new HashSet<Clase>();
		clases.add(Clase.BRUJO);
		clases.add(Clase.CABALLERO);
		r.setClases(clases);
		testServ.crearEntidad(r);
		
		r2 = new Raza();
		r2.setClases(clases);
		
		ganador = new Personaje(r, "Winner", Clase.BRUJO);
		ganador.setValorDanho(new Danho(500f));
		testServ.crearEntidad(ganador);
		
		perdedor = new Personaje(r2, "Loser", Clase.CABALLERO);
		testServ.crearEntidad(perdedor);
		
		ResultadoCombate rc1 = new ResultadoCombate();
		rc1.setGanador(ganador);
		rc1.setPerdedor(perdedor);
		testServ.crearEntidad(rc1);
		
		ResultadoCombate rc2 = new ResultadoCombate();
		rc2.setGanador(ganador);
		rc2.setPerdedor(perdedor);
		testServ.crearEntidad(rc2);
		
		ResultadoCombate rc3 = new ResultadoCombate();
		rc2.setGanador(ganador);
		rc2.setPerdedor(perdedor);
		testServ.crearEntidad(rc2);
		
	}

	
	@Test
	public void seObtienenDiezPersonajesConMasBatallasGanadas() {
		
//		assertEquals(ganador, leaderboardS.campeones().get(0));
	}
	
	@Test
	public void seObtieneElPersonajeConMasDanho() {
		
		assertEquals(ganador, leaderboardS.masFuerte());
		
	}

}
