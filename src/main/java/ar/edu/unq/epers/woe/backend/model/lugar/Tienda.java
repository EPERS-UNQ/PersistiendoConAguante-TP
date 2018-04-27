package ar.edu.unq.epers.woe.backend.model.lugar;

import java.util.Set;

import ar.edu.unq.epers.woe.backend.model.item.Item;

public class Tienda extends Lugar {

	private Set<Item> items;

	public Tienda(String nombreLugar) {
		super(nombreLugar);
	}

	public void setItems(Set<Item> listaItems) {
		items = listaItems;
		
	}

	public Set<Item> getItems() {
		return items;
	}

}
