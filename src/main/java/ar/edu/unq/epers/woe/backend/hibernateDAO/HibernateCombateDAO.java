package ar.edu.unq.epers.woe.backend.hibernateDAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ar.edu.unq.epers.woe.backend.hibernateDAO.Runner;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class HibernateCombateDAO {

	public int guardar(ResultadoCombate resComb) {
		Session s = Runner.getCurrentSession();
		return (int) s.save(resComb);
	}

	public ResultadoCombate recuperar(int id) {
		Session s = Runner.getCurrentSession();
		ResultadoCombate r = s.get(ResultadoCombate.class, id);
		if(r==null) {
			throw new RuntimeException("No existe id ResultadoCombate");
		}
		return r;
	}
	
	public Personaje personajeMayorDanho() {
		// metodo en construccion...
		Session s = Runner.getCurrentSession();
		String hql = "select p "+
				"from Personaje p join Danho d "
				+ "order by d.valor desc";
		Query<Personaje> query = s.createQuery(hql, Personaje.class);

		// aca pretendia retornar el personaje que tenga valor de danho 'maxValor',
		// obtenido de otra query, usando 'where d.valor = maxValor' pero tambien me
		// tiraba error...
		// Query<Float> queryMaxDanho = s.createQuery("select max(d.valor) from Danho
		// d", Float.class);
		// Float maxValor= queryMaxDanho.getSingleResult();
		//
		// Query query = s.createQuery(hql, String.class);
		// query.setParameter("maximo", maxValor);
		
		System.out.println("nombre del forro: "+ query.getResultList().get(0) );

		return null ;
	}
	
	public List<Personaje> conMasBatallasGanadas(){
		
		Session session = Runner.getCurrentSession();
		
		String hqlCount = "select r.ganador, count(*) as c "
				+ "from ResultadoCombate r "
				+ "group by r.ganador "
				+ "order by c desc";
		Query<Object[]> query = session.createQuery(hqlCount);
		query.setMaxResults(10);
		// query me retorna un ArrayList<Object[2]>

		return( recolectarPersonajeEnPos(query.getResultList(), 0) );
	}

	private List<Personaje> recolectarPersonajeEnPos(List<Object[]> resultList, int posicion) {
		List<Personaje> list = new ArrayList<Personaje>();
		for( Object[] o : resultList ) {
			list.add( (Personaje) o[posicion] );
		}
		return list;
	}
}
