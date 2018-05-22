package ar.edu.unq.epers.woe.backend.hibernateDAO;

import ar.edu.unq.epers.woe.backend.model.monstruo.Monstruo;
import org.hibernate.Session;

public class HibernateMonstruoDAO {

    public void guardar(Monstruo m) {
        Session session = Runner.getCurrentSession();
        session.save(m);
    }

    public Monstruo recuperar(Integer id) {
        Session session = Runner.getCurrentSession();
        Monstruo mr = session.get(Monstruo.class, id);
        if(mr==null) {
            throw new RuntimeException("El Monstruo no existe");
        }
        return mr;
    }

}
