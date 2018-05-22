package ar.edu.unq.epers.woe.backend.model.lugar;

import java.util.Set;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

import javax.persistence.*;


@Entity
@DiscriminatorValue("TIENDA")
public class Tienda extends Lugar {

	@OneToMany(mappedBy="lugar", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Item> items;

	public Tienda() {};

	public Tienda(String nombreLugar) {
		super(nombreLugar);
	}

	public void setItems(Set<Item> listaItems) {
		this.items = listaItems;
		for(Item i : this.items) {
			i.setLugar(this);
		}
	}

	public Set<Item> getItems() {
		return items;
	}

	public void comprar(Personaje pj, Item i) {
		if(pj.getBilletera()>=i.getCostoDeCompra()) {
			pj.gastarBilletera(i.getCostoDeCompra());
			pj.getMochila().agregarItem(i);
		}
	}

	public void vender(Personaje pj, Item i) {
		pj.agregarABilletera(i.getCostoDeVenta());
		pj.sacarItem(i);
	}

	@Override
	public boolean esTienda() { return true; }

}
