package ar.edu.unq.epers.woe.backend.service.raza;

import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;

/**
 * Situación excepcional en que se crea un personaje de con una raza y clase determinada
 * y la raza no tiene esa clase disponible.
 */
public class ClaseInvalida extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClaseInvalida(Raza raza, Clase clase) {
		super("La raza [" + raza.getNombre()+ "] no tiene la clase ["+clase.name()+"]");
	}
	
}
