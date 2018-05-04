package ar.edu.unq.epers.woe.backend.service.hibernateDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryProvider {
	
	private static SessionFactoryProvider INSTANCE;

	private SessionFactory sessionFactory;
	
	public static SessionFactoryProvider getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SessionFactoryProvider();
		}
		return INSTANCE;
	}
	
	public static void destroy() {
		if (INSTANCE != null && INSTANCE.sessionFactory != null) {
			INSTANCE.sessionFactory.close();
		}
		INSTANCE = null;
	}
	
	private SessionFactoryProvider() {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		this.sessionFactory = configuration.buildSessionFactory();
	}
	
	public Session createSession() {
		return this.sessionFactory.openSession();
	}

}