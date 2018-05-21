package ar.edu.unq.epers.woe.backend.model.mision;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("OBTENERITEM")
public class ObtenerItem extends Mision {
	
	@OneToOne
	private Item item;

	public ObtenerItem() {}
	
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

	@Override
	public Boolean esObtenerItem() { return true; }

}

