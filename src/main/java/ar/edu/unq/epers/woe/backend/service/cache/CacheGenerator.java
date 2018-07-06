package ar.edu.unq.epers.woe.backend.service.cache;


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
    private String claveEventosDeLugar = "eventosDe ";

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
    
    public void setCacheEventosDeLugar(String nombreL, List<Evento> eventos) {
       	String clave = claveEventosDeLugar+nombreL;
    	rd.putList(clave, eventos);
    }

    // Retorna null si la clave aún no ha sido seteada en Redis.
	public List<Evento> getEventosDeLugar(String nombreL) {
		List<Evento> eventos = null;		
        if( existenEventosDe(nombreL) ) {
        	String clave = claveEventosDeLugar+nombreL;
        	return rd.getList(clave);
        }
		return eventos;
	}

	public boolean existenEventosDe(String nombreP) {
		return rd.existsKey(claveEventosDeLugar+nombreP);
	}
	
	public void invalidarClaveLugar( String nombreLugar ) {
		String clave = claveEventosDeLugar+nombreLugar;
		rd.remove(clave);
	}

}
