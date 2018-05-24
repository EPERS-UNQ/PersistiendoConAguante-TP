package ar.edu.unq.epers.woe.backend.hibernateDAO;

import java.util.function.Supplier;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Runner {

	private static final ThreadLocal<Session> CONTEXTO = new ThreadLocal<>();

	public static <T> T runInSession(Supplier<T> bloque) {
		// permite anidar llamadas a Runner sin abrir una nueva
		// Sessino cada vez (usa la que abrio la primera vez)
		if (CONTEXTO.get() != null) {
			return bloque.get();
		}

		try {
			beginTransaction();

			// codigo de negocio
			T resultado = bloque.get();

			commit();
			return resultado;
		} catch (RuntimeException e) {
			rollback();
			throw e;
		}
	}

	public static Session getCurrentSession() {
		Session session = CONTEXTO.get();
		if (session == null) {
			throw new RuntimeException("No hay ninguna session en el contexto");
		}
		return session;
	}

	public static void beginTransaction() {
		Session session = SessionFactoryProvider.getInstance().createSession();
		Transaction tx = session.beginTransaction();
		CONTEXTO.set(session);
	}

	public static void commit() {
		Session session = CONTEXTO.get();
		session.getTransaction().commit();
		session.close();
		CONTEXTO.set(null);
	}

	public static void rollback() {
		Session session = CONTEXTO.get();
		// solamente puedo cerrar la transaccion si fue abierta antes,
		// puede haberse roto el metodo ANTES de abrir una transaccion
		if (session != null) {
			session.getTransaction().rollback();
			session.close();
			CONTEXTO.set(null);
		}

	}
}