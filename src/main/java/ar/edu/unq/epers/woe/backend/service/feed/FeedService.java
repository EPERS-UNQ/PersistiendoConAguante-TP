package ar.edu.unq.epers.woe.backend.service.feed;

import java.util.List;

import ar.edu.unq.epers.woe.backend.model.evento.Evento;
import ar.edu.unq.epers.woe.backend.mongoDAO.EventoMongoDAO;
import ar.edu.unq.epers.woe.backend.neo4jDAO.Neo4jLugarDAO;

public class FeedService {
	
	
	private EventoMongoDAO eventoDAO;
	private Neo4jLugarDAO lugarDAO;


	public FeedService() {
		eventoDAO = new EventoMongoDAO();
	}
	
	/*
	 * Devuelve la lista de eventos que involucren al personaje provisto,
	 * conteniendo primero los eventos mas recientes
	 */
	public List<Evento> feedPersonaje(String nombrePersonaje){
		
		return eventoDAO.getByPersonaje(nombrePersonaje);
		
	}

	
	/*
	 * Devuelve los eventos que ocurrieron en el lugar mas todos los eventos de los
	 * lugares que esten conectados con el mismo, conteniendo primero los eventos
	 * mas recientes
	 */
	public List<Evento> feedLugar(String nombreLugar){
		lugarDAO = new Neo4jLugarDAO();
		List<String> lugares = lugarDAO.conectadosCon(nombreLugar);
		lugares.add(nombreLugar);
		return eventoDAO.getByLugares(lugares);
	}

}
