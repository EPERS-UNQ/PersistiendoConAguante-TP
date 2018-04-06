package ar.edu.unq.epers.woe.backend.model.personaje;

import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;

/**
 * Un {@link Personaje} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Raza} en particular.
 * 
 * @author Charly Backend
 */
public class Personaje {
	
	private String nombre;
	private Raza raza;
	private Clase clase;
	private int energia;
	
	public Personaje(Raza raza, String nombre, Clase clase) {
		this.raza = raza;
		this.nombre = nombre;
		this.clase = clase;
	}


	/**
	 * @return el nombre de un personaje (todos los bichos tienen
	 * nombre). Este NO es el nombre de su raza.
	 */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * @return la raza a la que este personaje pertenece.
	 */
	public Raza getRaza() {
		return this.raza;
	}
	
	/**
	 * @return la cantidad de puntos de energia de este personaje en
	 * particular. Dicha cantidad crecerá (o decrecerá) conforme
	 * a este personaje participe en combates contra otros bichomones.
	 */
	public int getEnergia() {
		return this.energia;
	}
	public void setEnergia(int energia) {
		this.energia = energia;
	}

	public Clase getClase() {
		return clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}

}