package ar.edu.unq.epers.woe.backend.service.hibernateDAO;

import org.hibernate.Session;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;

public class HibernateRazaDAO {

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
			return raza;
		});
	}
	
}
