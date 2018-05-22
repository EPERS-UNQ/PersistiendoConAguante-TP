package ar.edu.unq.epers.woe.backend.hibernateDAO;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.service.TestService;

public class HibernateResultadoCombateDAOTest {

	HibernateCombateDAO resCombateDao;
	ResultadoCombate resComb;
	Personaje g;
	Personaje p;
	
	@Before
	public void setUp() {
		
		resCombateDao = new HibernateCombateDAO(); //cambiamos a "ResultadoCombateDao"

		g = new Personaje(null, "Pepe", null);
		p = new Personaje(null, "Pepito", null );
		resComb = new ResultadoCombate();
		resComb.setGanador(g);
		resComb.setPerdedor(p);
		
	}

	@Test
	public void seGuardanyRecuperanElGanadorYPerdedorDeUnResultadoCombate() {

		ResultadoCombate recuperado = 
			Runner.runInSession(()->{
				int id = resCombateDao.guardar(resComb);
				return resCombateDao.recuperar(id);
			});
		
		assertEquals(g, recuperado.getGanador());
		assertEquals(p, recuperado.getPerdedor());
	}

}
