package ar.edu.unq.epers.woe.backend.hibernateDAO;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class HibernateItemDAO {

    public void guardar(Item i) {
        Session session = Runner.getCurrentSession();
        session.save(i);
    }

    public Item recuperarPorNombre(String nombre) {
    	
    	Session s = Runner.getCurrentSession();
		String hql = "from Item i "
				+ "where i.nombre= :n";
		
		Query<Item> query = s.createQuery(hql,  Item.class);
		query.setParameter("n", nombre);
		query.setMaxResults(1);

		return query.getSingleResult();    	
    }
    
    public Item recuperar(Integer id) {
        Session session = Runner.getCurrentSession();
        Item itemr = session.get(Item.class, id);
        if(itemr==null) {
            throw new RuntimeException("El Item no existe");
        }
        return itemr;
    }
}
