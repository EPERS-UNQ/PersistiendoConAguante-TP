package ar.edu.unq.epers.woe.backend.service.raza;

import java.util.List;

import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;

/**
 * @author Steve Frontend
 */
public interface RazaService {
	
	/**
	 * Este método será utilizado por una interfaz de administración para crear nuevas
	 * especies de bichos. Recibe por parametro un objeto {@link Raza} previamente
	 * construido y se encarga de persistirlo en la ase de datos.  Tener en cuenta que
	 * el nombre de cada raza debe ser único para toda la aplicación.
	 * 
	 * @param raza - un objeto Raza previamente construido por la gente de frontend
	 */
	void crearRaza(Raza raza);
	
	/**
	 * Este método devolverá la {@link Raza} cuyo nombre sea igual al provisto por
	 * parámetro.
	 * 
	 * Se espera que este método devuelva, a lo sumo, un solo resultado.
	 * 
	 * @param razaId - el id de la raza que se busca
	 * @return la raza encontrada
	 * @throws la excepción {@link RazaNoExistente} (no chequeada)
	 */
	Raza getRaza(Integer razaId);

	/**
	 * @return una lista de todas los objetos {@link Raza} existentes ordenados
	 * alfabéticamente por su nombre en forma ascendente
	 */
	List<Raza> getAllRazas();

	/**
	 * Crea un nuevo Personaje perteneciente a la raza especificada. El nuevo objeto Personaje no es
	 * persistido (de momento), solo devuelto.
	 * 
	 * Para llevar una mejor estadística de los bichos que han sido creados cada objeto
	 * {@link Raza} cuenta con un contador cantidadBichos. El mismo deberá ser incrementado
	 * en 1.
	 * 
	 * @param razaId - el id de la raza del personaje a crear
	 * @param nombrePersonaje - el nombre del personaje a ser creado
	 * @param clasePersonaje - la clase del personaje a ser creado
	 * @return un objeto {@link Personaje} instanciado
	 */
	Personaje crearPersonaje(Integer razaId, String nombrePersonaje, Clase clasePersonaje);

}
