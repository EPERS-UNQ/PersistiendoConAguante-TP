package ar.edu.unq.epers.woe.backend.service.leaderboard;

import java.util.List;

import ar.edu.unq.epers.woe.backend.hibernateDAO.HibernateCombateDAO;
import ar.edu.unq.epers.woe.backend.hibernateDAO.Runner;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.service.cache.CacheGenerator;

public class LeaderboardService {
	
	private HibernateCombateDAO daoCombates;
	private CacheGenerator cg = new CacheGenerator();

	public LeaderboardService() {
		daoCombates = new HibernateCombateDAO();
	}
	
	public List<Personaje> campeones(){
		return Runner.runInSession(() -> {
			return daoCombates.conMasBatallasGanadas();
		});
	}

	public Personaje masFuerte() {
		Personaje pj;
		if(this.cg.getMasFuerte() == null) {
			pj = Runner.runInSession(() -> {
				return daoCombates.personajeMayorDanho();
			});
			this.cg.setCacheMasFuerte(pj.getNombre());
		} else { pj = this.cg.getMasFuerte(); }
		return pj;
	}

	public Raza razaLider() {
		return Runner.runInSession(() -> {
			return daoCombates.razaMasCombatesGanados();
		});
	}

	public Clase claseLider() {
		return Runner.runInSession(()-> {
			return daoCombates.claseMasCombatesGanados();
		});
	}
}