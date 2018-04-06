package ar.edu.unq.epers.woe.backend.service;

import ar.edu.unq.epers.woe.backend.service.data.DataService;
import ar.edu.unq.epers.woe.backend.service.raza.RazaService;
import ar.edu.unq.epers.woe.frontend.mock.RazaServiceMock;

/**
 * Esta clase es un singleton, el cual sera utilizado por equipo de frontend
 * para hacerse con implementaciones a los servicios.
 * 
 * @author Steve Frontend
 * 
 * TODO: Gente de backend, una vez que tengan las implementaciones de sus
 * servicios propiamente realizadas apunten a ellas en los metodos provistos
 * debajo. Gracias!
 */
public class ServiceFactory {
	
	/**
	 * @return un objeto que implementa {@link RazaService}
	 */
	public RazaService getRazaService() {
		return new RazaServiceMock();
		//throw new RuntimeException("Todavia no se ha implementado este metodo");
	}
	
	/**
	 * @return un objeto que implementa {@link DataService}
	 */
	public DataService getDataService() {
		throw new RuntimeException("Todavia no se ha implementado este metodo");
	}

}
