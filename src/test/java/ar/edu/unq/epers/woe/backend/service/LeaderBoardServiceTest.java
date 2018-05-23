package ar.edu.unq.epers.woe.backend.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.edu.unq.epers.woe.backend.hibernateDAO.SessionFactoryProvider;
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
	Raza razaGanador;
	Raza razaP;
	Clase claseGanador = Clase.BRUJO;
	Clase claseMayoria = Clase.CABALLERO;
	TestService testServ;
	LeaderboardService leaderboardS= new LeaderboardService();;
	
	@Before
	public void setUp(){
				
		leaderboardS = new LeaderboardService();
		testServ = new TestService();
		
		Set<Clase> clases = new HashSet<Clase>();
		clases.add(Clase.BRUJO);
		clases.add(Clase.CABALLERO);
		razaGanador = new Raza("Raza Uno");
		razaGanador.setClases(clases);
		testServ.crearEntidad(razaGanador);
		
		razaP = new Raza("Raza Dos");
		razaP.setClases(clases);
		testServ.crearEntidad(razaP);
		
		//dos combates ganados
		ganador = new Personaje(razaGanador, "Winner", claseGanador);
		ganador.setValorDanho(new Danho(500f));
		testServ.crearEntidad(ganador);

		//solo un combate ganado
		perdedor = new Personaje(razaP, "Loser", claseMayoria);
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
		
		// "razaP" raza con mayor cantidad de combates ganados
		// "clase Mayoria" clase con mayor cantidad de combates ganados
		Personaje personaje3 = new Personaje(razaP, "Persnj 3", claseMayoria);
		Personaje personaje4 = new Personaje(razaP, "Persnj 4", claseMayoria);
		testServ.crearEntidad(personaje3);
		testServ.crearEntidad(personaje4);
		
		ResultadoCombate rc3 = new ResultadoCombate();
		rc3.setGanador(personaje3); 
		rc3.setPerdedor(ganador);
		testServ.crearEntidad(rc3);
		
		ResultadoCombate rc4 = new ResultadoCombate();
		rc4.setGanador(personaje4);
		rc4.setPerdedor(ganador);
		testServ.crearEntidad(rc4);
		
	}

	@After
	public void cleanup() {
		SessionFactoryProvider.destroy();
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

	
	@Test
	public void seObtieneLaRazaQueGanoMasCombates() {
		
		assertEquals( razaP.getNombre(), leaderboardS.razaLider().getNombre() );
	}
	
	
	@Test
	public void seObtieneClaseQueGanoMasCombates() {
		
		assertEquals( claseMayoria, leaderboardS.claseLider()  );
	}
}
