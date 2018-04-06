package ar.edu.unq.epers.woe.frontend.app;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import ar.edu.unq.epers.woe.frontend.api.RazaServiceREST;

/**
 * Esto es codigo de frontend, no deberian tocar nada de aca.
 */
@ApplicationPath("api")
public class BichomonWebApp extends ResourceConfig {

	public BichomonWebApp() {
		this.packages(true, RazaServiceREST.class.getPackage().getName())
			.register(JsonObjectMapperProvider.class)
	        .register(JacksonFeature.class);
	}
	
}
