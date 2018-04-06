package ar.edu.unq.epers.woe.backend.service.raza;

/**
 * Situación excepcional en que una raza buscada no es
 * encontrada.
 */
public class RazaNoExistente extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RazaNoExistente(Integer razaId) {
		super("No se encuentra la raza con id [" + razaId+ "]");
	}
	
}
