package ar.edu.unq.epers.woe.backend.service.cache;

import ar.edu.unq.epers.woe.backend.hibernateDAO.HibernatePersonajeDAO;
import ar.edu.unq.epers.woe.backend.hibernateDAO.Runner;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.redisDAO.RedisDAO;

public class CacheGenerator {

    private RedisDAO rd = new RedisDAO();
    private HibernatePersonajeDAO pjhd = new HibernatePersonajeDAO();
    private String claveMasFuerte = "masFuerte";

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

}
