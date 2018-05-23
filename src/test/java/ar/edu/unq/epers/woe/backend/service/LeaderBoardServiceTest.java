package ar.edu.unq.epers.woe.backend.service;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	LeaderboardService leaderboardS= new LeaderboardService();;
	
	@Before
	public void setUp(){
				
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
		testServ.crearEntidad(r2);
		
		//tres combates ganados
		ganador = new Personaje(r, "Winner", Clase.BRUJO);
		ganador.setValorDanho(new Danho(500f));
		testServ.crearEntidad(ganador);
		
		//solo un combate ganado
		perdedor = new Personaje(r2, "Loser", Clase.CABALLERO);
		testServ.crearEntidad(perdedor);
		
		ResultadoCombate rc0 = new ResultadoCombate();
		rc0.setGanador(perdedor); 
		rc0.setPerdedor(ganador);
		testServ.crearEntidad(rc0);
		
		ResultadoCombate rc1 = new ResultadoCombate();
		rc1.setGanador(ganador);
		rc1.setPerdedor(perdedor);
		testServ.crearEntidad(rc1);
		
		ResultadoCombate rc2 = new ResultadoCombate();
		rc2.setGanador(ganador);
		rc2.setPerdedor(perdedor);
		testServ.crearEntidad(rc2);
		
		ResultadoCombate rc3 = new ResultadoCombate();
		rc3.setGanador(ganador);
		rc3.setPerdedor(perdedor);
		testServ.crearEntidad(rc3);
		
	}

	
	@Test
	public void seObtienenDiezPersonajesConMasBatallasGanadas() {
		
		// para usar este assert habria q reescribir el equals o crear metodo
		// equivalente
		// assertTrue( leaderboardS.campeones().get(0), ganador );
		
		assertEquals( leaderboardS.campeones().get(0).getNombre(), "Winner");
	}
	
	@Test
	public void seObtieneElPersonajeConMasDanho() {

		assertEquals(leaderboardS.masFuerte().getNombre(), "Winner"); //
	}

}
