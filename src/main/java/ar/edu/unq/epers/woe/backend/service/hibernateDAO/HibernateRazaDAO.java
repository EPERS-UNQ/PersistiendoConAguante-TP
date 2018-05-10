package ar.edu.unq.epers.woe.backend.service.hibernateDAO;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.service.raza.RazaNoExistente;

public class HibernateRazaDAO {
	
	public HibernateRazaDAO() {
		
	}

	public void guardar(Raza r) {
		Runner.runInSession(() -> {
			Session session = Runner.getCurrentSession();
			session.save(r);
			return null;
		});
	}
	
	public Raza recuperar(int idRaza) {
		return Runner.runInSession(() -> {
			Session session = Runner.getCurrentSession();
			Raza raza = session.get(Raza.class, idRaza);
			if(raza==null) {
				throw new RazaNoExistente(idRaza);
			}
			return raza;
		});
	}
	
	public List<Raza> agregarRazasOrdenadas(){
		List<Raza>list;
		Session session = SessionFactoryProvider.getInstance().createSession();
		Transaction tx = session.beginTransaction();
				
		String hql = "from Raza r "
				+ "order by r.nombre asc";
		Query<Raza> query = session.createQuery(hql,  Raza.class);

		list = query.getResultList();
		
		tx.commit();

		return list;
	}

	public void incrementarPjs(Integer razaId) {
		Session session = SessionFactoryProvider.getInstance().createSession();
		Transaction tx = session.beginTransaction();
		Raza raza = recuperar(razaId);
		raza.setCantidadPersonajes(raza.getCantidadPersonajes()+1);
		session.update(raza);
		
//		        .executeUpdate();
		tx.commit();
		session.close();
		
	}
	
}
