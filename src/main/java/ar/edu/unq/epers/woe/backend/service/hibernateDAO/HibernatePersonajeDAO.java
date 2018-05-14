package ar.edu.unq.epers.woe.backend.service.hibernateDAO;

import org.hibernate.Session;
import ar.edu.unq.epers.woe.backend.service.hibernateDAO.Runner;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class HibernatePersonajeDAO {
	
	public HibernatePersonajeDAO() {}
	
	public void guardar(Personaje p) {
			Session session = Runner.getCurrentSession();
			session.save(p.getMochila()); //parece que es necesario guardarlo...
			session.save(p);
	}
	
	public Personaje recuperar(String nombre) {
			Session session = Runner.getCurrentSession();
			Personaje personajeR = session.get(Personaje.class, nombre);
			return personajeR;
	}

}
