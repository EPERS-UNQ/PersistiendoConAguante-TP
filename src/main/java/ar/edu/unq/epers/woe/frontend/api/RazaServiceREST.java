package ar.edu.unq.epers.woe.frontend.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.service.ServiceFactory;
import ar.edu.unq.epers.woe.backend.service.raza.RazaService;

/**
 * Esta implementacion decora el servicio {@link RazaService} devuelto
 * por {@link ServiceFactory} y expone sus metodos a traves de una API
 * REST.
 * 
 * Esto es codigo de frontend, no deberian tocar nada de aca.
 * 
 * @author Steve Frontend
 */
@Path("raza")
public class RazaServiceREST implements RazaService {
	
	private final RazaService decorado;

	public RazaServiceREST() {
		this.decorado = new ServiceFactory().getRazaService();
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void crearRaza(Raza raza) {
		this.decorado.crearRaza(raza);
	}

	@Override
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Raza getRaza(@PathParam("id") Integer razaId) {
		return this.decorado.getRaza(razaId);
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Raza> getAllRazas() {
		return this.decorado.getAllRazas();
	}

	@Override
	@POST
	@Path("{razaId}/{nombrePersonaje}/{clasePersonaje}")
	@Produces(MediaType.APPLICATION_JSON)
	public Personaje crearPersonaje(@PathParam("razaId") Integer razaId, @PathParam("nombrePersonaje") String nombrePersonaje, @PathParam("clasePersonaje") Clase clasePersonaje ) {
		return this.decorado.crearPersonaje(razaId, nombrePersonaje, clasePersonaje);
	}

}
