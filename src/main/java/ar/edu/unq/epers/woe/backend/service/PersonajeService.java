package ar.edu.unq.epers.woe.backend.service;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class PersonajeService {

	public void equipar(Personaje personaje, Item item) {
		personaje.agregarItem(item);
	}

}
