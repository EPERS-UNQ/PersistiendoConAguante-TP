package ar.edu.unq.epers.woe.backend.hibernateDAO;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import org.hibernate.Session;

public class HibernateItemDAO {

    public void guardar(Item i) {
        Session session = Runner.getCurrentSession();
        session.save(i);
    }

    public Item recuperar(Integer id) {
        Session session = Runner.getCurrentSession();
        Item itemr = session.get(Item.class, id);
        if(itemr==null) {
            throw new RuntimeException("El personaje no existe");
        }
        return itemr;
    }


}
