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
	Raza razaGanador;
	Raza razaMayoria;
	Clase claseGanador;
	Clase claseMayoria;
	TestService testServ = new TestService();
	LeaderboardService leaderboardS = new LeaderboardService();
	
	public void crearModelo(){
						
		// "clase Mayoria" clase con mayor cantidad de combates ganados
		claseGanador = Clase.BRUJO;
		claseMayoria = Clase.CABALLERO;

		Set<Clase> clases = new HashSet<Clase>();
		clases.add(claseGanador);
		clases.add(claseMayoria);
		//Raza del personaje mas combates ganados
		razaGanador = new Raza("Raza Uno");
		razaGanador.setClases(clases);
		testServ.crearEntidad(razaGanador);
		
		//Raza de los demas personajes con mayor cantidad total de combates ganados
		razaMayoria = new Raza("Raza Dos");
		razaMayoria.setClases(clases);
		testServ.crearEntidad(razaMayoria);
		
		//Personaje con dos combates ganados y con mayor danho
		ganador = new Personaje(razaGanador, "Winner", claseGanador);
		ganador.setValorDanho(new Danho(500f));
		testServ.crearEntidad(ganador);

		Personaje personaje2 = new Personaje(razaMayoria, "Persnaje2", claseMayoria);
		testServ.crearEntidad(personaje2);
		
		ResultadoCombate rc0 = new ResultadoCombate();
		rc0.setGanador(personaje2); 
		rc0.setPerdedor(ganador);
		testServ.crearEntidad(rc0);
		
		ResultadoCombate rc1 = new ResultadoCombate();
		rc1.setGanador(ganador);
		rc1.setPerdedor(personaje2);
		testServ.crearEntidad(rc1);
		
		ResultadoCombate rc2 = new ResultadoCombate();
		rc2.setGanador(ganador);
		rc2.setPerdedor(personaje2);
		testServ.crearEntidad(rc2);
	}

	public void crearModeloExtendido() {
		// se extiende para un total de 11 personajes con un combate ganado cada uno
		Personaje personaje3 = new Personaje(razaMayoria, "Persnj 3", claseMayoria);
		Personaje personaje4 = new Personaje(razaMayoria, "Persnj 4", claseMayoria);
		Personaje personaje5 = new Personaje(razaMayoria, "Persnj 5", claseMayoria);
		Personaje personaje6 = new Personaje(razaMayoria, "Persnj 6", claseMayoria);
		Personaje personaje7 = new Personaje(razaMayoria, "Persnj 7", claseMayoria);
		Personaje personaje8 = new Personaje(razaMayoria, "Persnj 8", claseMayoria);
		Personaje personaje9 = new Personaje(razaMayoria, "Persnj 9", claseMayoria);
		Personaje personaje10 = new Personaje(razaMayoria, "Persnj 10", claseMayoria);
		Personaje personaje11 = new Personaje(razaMayoria, "Persnj 11", claseMayoria);
		
		testServ.crearEntidad(personaje3);
		testServ.crearEntidad(personaje4);
		testServ.crearEntidad(personaje5);
		testServ.crearEntidad(personaje6);
		testServ.crearEntidad(personaje7);
		testServ.crearEntidad(personaje8);
		testServ.crearEntidad(personaje9);
		testServ.crearEntidad(personaje10);
		testServ.crearEntidad(personaje11);
		
		ResultadoCombate rc3 = new ResultadoCombate();
		rc3.setGanador(personaje3); rc3.setPerdedor(personaje4);
		testServ.crearEntidad(rc3);
		
		ResultadoCombate rc4 = new ResultadoCombate();
		rc4.setGanador(personaje4);	rc4.setPerdedor(personaje5);
		testServ.crearEntidad(rc4);
		
		ResultadoCombate rc5 = new ResultadoCombate();
		rc5.setGanador(personaje5);	rc5.setPerdedor(personaje6);
		testServ.crearEntidad(rc5);
		
		ResultadoCombate rc6 = new ResultadoCombate();
		rc6.setGanador(personaje6); rc6.setPerdedor(personaje7);
		testServ.crearEntidad(rc6);
		
		ResultadoCombate rc7 = new ResultadoCombate();
		rc7.setGanador(personaje7); rc7.setPerdedor(personaje7);
		testServ.crearEntidad(rc7);
		
		ResultadoCombate rc8 = new ResultadoCombate();
		rc8.setGanador(personaje8); rc8.setPerdedor(personaje9);
		testServ.crearEntidad(rc8);
		
		ResultadoCombate rc9 = new ResultadoCombate();
		rc9.setGanador(personaje9); rc9.setPerdedor(personaje8);
		testServ.crearEntidad(rc9);
		
		ResultadoCombate rc10 = new ResultadoCombate();
		rc10.setGanador(personaje10); rc10.setPerdedor(personaje8);
		testServ.crearEntidad(rc10);
		
		ResultadoCombate rc11 = new ResultadoCombate();
		rc11.setGanador(personaje11); rc11.setPerdedor(personaje8);
		testServ.crearEntidad(rc11);
	}

	@After
	public void cleanup() {
		SessionFactoryProvider.destroy();
	}
	
	
	@Test
	public void seObtienenDosPersonajesConMasBatallasGanadas() {
		crearModelo(); // en modelo inicial existen solamente dos personajes
		
		List<Personaje> dosCampeones = leaderboardS.campeones();
		
		assertEquals( 2, dosCampeones.size() );
	}

	
	@Test
	public void seObtienenDiezPersonajesConMasBatallasGanadasOrdenadosDesc() {
		crearModelo();
		crearModeloExtendido(); // en modelo extendido existen 11 persnajes

		List<Personaje> campeones = leaderboardS.campeones();
		
		assertEquals( ganador.getNombre(), campeones.get(0).getNombre());
		assertEquals( 10, campeones.size() ); 
	}
	
	
	@Test
	public void seObtieneElPersonajeConMasDanho() {
		crearModelo();
		assertEquals(ganador.getNombre(), leaderboardS.masFuerte().getNombre());
	}

	
	@Test
	public void seObtieneLaRazaQueGanoMasCombates() {
		crearModelo();
		crearModeloExtendido();
		assertEquals( razaMayoria.getNombre(), leaderboardS.razaLider().getNombre() );
	}
	
	
	@Test
	public void seObtieneClaseQueGanoMasCombates() {
		crearModelo();
		crearModeloExtendido();
		assertEquals( claseMayoria, leaderboardS.claseLider()  );
	}
}
