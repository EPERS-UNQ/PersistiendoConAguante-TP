package ar.edu.unq.epers.woe.backend.hibernateDAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ar.edu.unq.epers.woe.backend.hibernateDAO.Runner;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class HibernateCombateDAO {
	
//	public HibernateCombateDAO() {}
//
//	public List<ResultadoCombate> mejoresCombatientesMax(int n) {
//		Session session = Runner.getCurrentSession();
//		
//		String hql = "select ganador p"
//				+ "from ResultadoCombate rc ";
//	
//		Query<ResultadoCombate> query = session.createQuery(hql, ResultadoCombate.class);
//		
//		query.setMaxResults(n);
//		return query.getResultList();
//	}
//
//	public Personaje personajeMayorDanho() {
//		Session s = Runner.getCurrentSession();
//		String hql = "como fuck hago un join?"
//				+ "order by danho asc";
//		
//		Query<Personaje> query = s.createQuery(hql);
//		return query.getResultList().get(0) ;
//		
//	}

}
