package ar.edu.unq.epers.woe.backend.model.lugar;

import java.util.HashSet;
import java.util.Set;

import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class Gimnasio extends Lugar  {

	private Set<Personaje> personajes;

	public Gimnasio(String nombreLugar) {
		super(nombreLugar);
		personajes = new HashSet<Personaje>();
	}

	public Set<Personaje> getPersonajes() {
		return personajes;
	}

	public void ingresaPersonaje(Personaje p) {
		// TODO deberia cambiar el lugar de ubicacion del personaje
		personajes.add(p);
		
	}

}
