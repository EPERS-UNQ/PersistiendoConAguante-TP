package ar.edu.unq.epers.woe.backend.service.leaderboard;

import java.util.List;

import ar.edu.unq.epers.woe.backend.hibernateDAO.HibernateCombateDAO;
import ar.edu.unq.epers.woe.backend.hibernateDAO.Runner;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class LeaderboardService {
	
	private HibernateCombateDAO daoCombates;

	public LeaderboardService() {
		daoCombates = new HibernateCombateDAO();
	}
	
	public List<Personaje> campeones(){
		return Runner.runInSession(() -> {
			return daoCombates.conMasBatallasGanadas();
		});
	}

	public Personaje masFuerte() {
		return
				Runner.runInSession(()->{
					daoCombates.personajeMayorDanho();
					return null;
				});
	}
}