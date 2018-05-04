package ar.edu.unq.epers.woe.backend.model.mision;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class ObtenerItem extends Mision {
	
	private Item item;
	
	public ObtenerItem(String nombre, Recompensa recompensa, Item item) {
        super(nombre, recompensa);
        this.setItem(item);
    }

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public Boolean tieneElItem(Personaje pj, Item item) {
		return pj.tieneElItem(item);
	}
	
	@Override
    public Boolean puedeCompletarMision(Personaje pj) {
        return this.misionAceptadaPor(pj) && (tieneElItem(pj,this.item));
    }

}

