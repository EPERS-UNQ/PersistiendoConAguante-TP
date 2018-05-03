package ar.edu.unq.epers.woe.backend.model.lugar;

import java.util.Set;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class Tienda extends Lugar {

	private Set<Item> items;

	public Tienda(String nombreLugar) {
		super(nombreLugar);
	}

	public void setItems(Set<Item> listaItems) {
		this.items = listaItems;
		
	}

	public Set<Item> getItems() {
		return items;
	}

	public void comprar(Personaje pj, Item i) {
		if(pj.getBilletera()>=i.getCostoDeCompra()) {
			pj.gastarBilletera(i.getCostoDeCompra());
			pj.agregarItem(i);
		}
	}

}
