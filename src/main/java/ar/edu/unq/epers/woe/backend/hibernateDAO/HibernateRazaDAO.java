package ar.edu.unq.epers.woe.backend.hibernateDAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.service.raza.RazaNoExistente;

public class HibernateRazaDAO {
	
	public HibernateRazaDAO() {
		
	}

	public void guardar(Raza r) {
			Session session = Runner.getCurrentSession();
			session.save(r);
	}
	
	public Raza recuperar(int idRaza) {
		Session session = Runner.getCurrentSession();
		Raza raza = session.get(Raza.class, idRaza);
		if(raza==null) {
			throw new RazaNoExistente(idRaza);
		}
		return raza;
	}
	
	public List<Raza> agregarRazasOrdenadas(){
		List<Raza>list;
		Session session = Runner.getCurrentSession();
		
		String hql = "from Raza r "
				+ "order by r.nombre asc";
		
		Query<Raza> query = session.createQuery(hql,  Raza.class);
		return query.getResultList();
	}

	public void incrementarPjs(Integer razaId, Integer n) {
		Session session = Runner.getCurrentSession();
		String hql = "update Raza "
				+ "set cantidadPersonajes= :cantIncrementada "
				+ "where id= :id";
		Query query = session.createQuery(hql);
		query.setParameter("cantIncrementada", n);
		query.setParameter("id", razaId);
		query.executeUpdate();
	}
	
}
