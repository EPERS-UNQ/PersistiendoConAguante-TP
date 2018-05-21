package ar.edu.unq.epers.woe.backend.hibernateDAO;

import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import org.hibernate.Session;

public class HibernateLugarDAO {

    public HibernateLugarDAO() {}

    public void guardar(Lugar l) {
            Session session = Runner.getCurrentSession();
            session.save(l);
    }

    public Lugar recuperar(String nombre) {
        Session session = Runner.getCurrentSession();
        Lugar lugarr = session.get(Lugar.class, nombre);
        if(lugarr==null) {
            throw new RuntimeException("El Lugar no existe");
        }
        return lugarr;
    }
}
