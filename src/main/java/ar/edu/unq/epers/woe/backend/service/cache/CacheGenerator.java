package ar.edu.unq.epers.woe.backend.service.cache;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.epers.woe.backend.hibernateDAO.HibernatePersonajeDAO;
import ar.edu.unq.epers.woe.backend.hibernateDAO.Runner;
import ar.edu.unq.epers.woe.backend.model.evento.Evento;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.mongoDAO.EventoMongoDAO;
import ar.edu.unq.epers.woe.backend.redisDAO.RedisDAO;

public class CacheGenerator {

    private RedisDAO rd = new RedisDAO();
    private HibernatePersonajeDAO pjhd = new HibernatePersonajeDAO();
	private EventoMongoDAO edao = new EventoMongoDAO();
    private String claveMasFuerte = "masFuerte";
    private String claveEventosDePj = "eventosDe ";

    // Retorna null si la clave aún no ha sido seteada en Redis.
    public Personaje getMasFuerte() {
        Personaje pj = null;
        if(this.rd.get(this.claveMasFuerte) != null) {
            pj = Runner.runInSession(() -> {
                return pjhd.recuperar(this.rd.get(this.claveMasFuerte)); });
        }
        return pj;
    }

    public void setCacheMasFuerte(String nombrePJ) {
        Personaje pj = Runner.runInSession(() -> { return pjhd.recuperar(nombrePJ); });
        if(this.getMasFuerte() == null) {
            this.rd.put(this.claveMasFuerte, pj.getNombre());
        } else if(pj.getDanhoTotal().getValor() > this.getMasFuerte().getDanhoTotal().getValor()) {
            this.rd.put(this.claveMasFuerte, pj.getNombre());
        }
    }
    
    public void setCacheEventosDePersonaje(String nombreP, List<String> eventosId) {
    	String clave = claveEventosDePj+nombreP;
    	String valor = String.join(",", eventosId);
    	rd.put(clave, valor);
    }

    // Retorna null si la clave aún no ha sido seteada en Redis.
	public List<Evento> getEventosDePersonaje(String nombreP) {
		List<Evento> eventos = null;
		String clave = claveEventosDePj+nombreP;
        if( rd.existsKey(clave) ) {
        	eventos = new ArrayList<Evento>();
        	for( String id : rd.get(clave).split(",")) {
        		eventos.add( edao.get(id) );
        	}
        }
		return eventos;
	}

}
