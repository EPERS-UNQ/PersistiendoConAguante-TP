package ar.edu.unq.epers.woe.backend.service.hibernateDAO;

import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import org.hibernate.Session;

public class HibernateLugarDAO {

    public HibernateLugarDAO() {}

    public void guardar(Lugar l) {
        Runner.runInSession(() -> {
            Session session = Runner.getCurrentSession();
            session.save(l);
            return null;
        });
    }

    public Lugar recuperar(int idLugar) {
        return Runner.runInSession(() -> {
            Session session = Runner.getCurrentSession();
            return session.get(Lugar.class, idLugar);
        });
    }
}
