package ar.edu.unq.epers.woe.backend.service.data;

import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.hibernateDAO.SessionFactoryProvider;
import ar.edu.unq.epers.woe.backend.neo4jDAO.Neo4jLugarDAO;
import ar.edu.unq.epers.woe.backend.service.raza.ServiciosRaza;
import java.util.HashSet;
import java.util.Set;

public class ServiciosDB implements DataService {

    //implementación del método crearSetDatosIniciales
    public void crearSetDatosIniciales() {
        ServiciosRaza razaServ = new ServiciosRaza();
        Raza raza1 = new Raza();
        Set<Clase> clases1 = new HashSet<Clase>();
        clases1.add(Clase.SACERDOTE);
        clases1.add(Clase.MAGO);

        raza1.setNombre("xRaza1");
        raza1.setAltura(55);
        raza1.setClases(clases1);
        raza1.setEnergiaIncial(10);
        raza1.setPeso(50);
        raza1.setUrlFoto("url_dest1");
        raza1.setCantidadPersonajes(0);

        Raza raza2 = new Raza();
        Set<Clase> clases2 = new HashSet<Clase>();
        clases2.add(Clase.BRUJO);

        raza2.setNombre("yRaza2");
        raza2.setAltura(182);
        raza2.setClases(clases1);
        raza2.setEnergiaIncial(158);
        raza2.setPeso(90);
        raza2.setUrlFoto("url_dest2");
        raza2.setCantidadPersonajes(2);

        razaServ.crearRaza(raza1);
        razaServ.crearRaza(raza2);

    }

    //implementación del método deleteAll
    public void eliminarDatos() {
//        RazaDao razadao = new RazaDao();
//        razadao.executeQuery("TRUNCATE raza;");
    	SessionFactoryProvider.destroy(); //???
    }

    public void eliminarDatosNeo4j() {
        Neo4jLugarDAO njld = new Neo4jLugarDAO();
        njld.eliminarDatos();
    }

}
