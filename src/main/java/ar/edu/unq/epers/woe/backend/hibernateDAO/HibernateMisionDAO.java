package ar.edu.unq.epers.woe.backend.hibernateDAO;

import ar.edu.unq.epers.woe.backend.model.mision.Mision;
import org.hibernate.Session;

public class HibernateMisionDAO {

    public HibernateMisionDAO() {}

    public void guardar(Mision m) {
            Session session = Runner.getCurrentSession();
            session.save(m);
    }

    public Mision recuperar(String nombre) {
        Session session = Runner.getCurrentSession();
        Mision misionr = session.get(Mision.class, nombre);
        if(misionr==null) {
            throw new RuntimeException("La Mision no existe.");
        }
        return misionr;
    }

}
