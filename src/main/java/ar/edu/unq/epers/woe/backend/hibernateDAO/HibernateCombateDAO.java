package ar.edu.unq.epers.woe.backend.hibernateDAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ar.edu.unq.epers.woe.backend.hibernateDAO.Runner;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;

public class HibernateCombateDAO {

	public int guardar(ResultadoCombate resComb) {
		Session s = Runner.getCurrentSession();
		return (int) s.save(resComb);
	}

	
	public ResultadoCombate recuperar(int id) {
		Session s = Runner.getCurrentSession();
		ResultadoCombate r = s.get(ResultadoCombate.class, id);
		if (r == null) {
			throw new RuntimeException("No existe id ResultadoCombate");
		}
		return r;
	}

	
	public Personaje personajeMayorDanho() {
		// no se aclara si el pesonaje tiene q tener un ResultadoCombate..
		Session s = Runner.getCurrentSession();
		String hql = "select d.personaje " 
				+ "from Danho d " 
				+ "where d.valor= :maximo order by d desc";
		Query query = s.createQuery(hql);

		Query<Float> queryMaxDanho = s.createQuery("select max(d.valor) from Danho d", Float.class);
		Float maxValor = queryMaxDanho.getSingleResult();

		query.setParameter("maximo", maxValor);
		query.setMaxResults(1);
		return (Personaje) query.getSingleResult();
	}
	
	
	public List<Personaje> conMasBatallasGanadas() {

		Session session = Runner.getCurrentSession();
		String hqlCount = "select r.ganador, count(*) as c " 
				+ "from ResultadoCombate r " 
				+ "group by r.ganador "
				+ "order by c desc";
		Query<Object[]> query = session.createQuery(hqlCount);
		query.setMaxResults(10);
		// query me retorna un ArrayList<Object[2]>

		return (recolectarPersonajeEnPos(query.getResultList(), 0));
	}

	
	private List<Personaje> recolectarPersonajeEnPos(List<Object[]> resultList, int posicion) {
		List<Personaje> list = new ArrayList<Personaje>();
		for (Object[] o : resultList) {
			list.add((Personaje) o[posicion]);
		}
		return list;
	}

	
	public Raza razaMasCombatesGanados() {
		Session session = Runner.getCurrentSession();
		String hqlCount = "select r.ganador.raza, count(*) as c " 
				+ "from ResultadoCombate r " 
				+ "group by r.ganador.raza "
				+ "order by c desc";
		Query<Object[]> query = session.createQuery(hqlCount);
		query.setMaxResults(1);
		
		return ((Raza) query.getSingleResult()[0]);
	}
	
	
	public Clase claseMasCombatesGanados() {
		Session session = Runner.getCurrentSession();
		String hqlCount = "select r.ganador.clase, count(*) as c " 
				+ "from ResultadoCombate r " 
				+ "group by r.ganador.clase "
				+ "order by c desc";
		Query<Object[]> query = session.createQuery(hqlCount);
		query.setMaxResults(1);
		
		return ((Clase) query.getSingleResult()[0]);
	}
}
