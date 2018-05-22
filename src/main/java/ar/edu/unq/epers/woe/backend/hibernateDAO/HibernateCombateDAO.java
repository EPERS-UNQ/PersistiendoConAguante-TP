package ar.edu.unq.epers.woe.backend.hibernateDAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ar.edu.unq.epers.woe.backend.hibernateDAO.Runner;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class HibernateCombateDAO {

	public Personaje personajeMayorDanho() {
		// metodo en construccion...
		Session s = Runner.getCurrentSession();
		String hql = "select p from Personaje p join Atributo a "
				+ "where aType = :atr order by a.valor asc";
		
		Query query = s.createQuery(hql,  Personaje.class);
		query.setParameter("atr", "DANHO");
		List list = query.getResultList();

		return (Personaje) list.get(0) ;
	}

	public int guardar(ResultadoCombate resComb) {
		Session s = Runner.getCurrentSession();
		return (int) s.save(resComb);
	}

	public ResultadoCombate recuperar(int id) {
		Session s = Runner.getCurrentSession();
		ResultadoCombate r = s.get(ResultadoCombate.class, id);
		if(r==null) {
			throw new RuntimeException("No existe id ResultadoCombate");
		}
		return r;
	}
}
