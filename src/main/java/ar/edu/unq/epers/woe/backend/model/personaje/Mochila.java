package ar.edu.unq.epers.woe.backend.model.personaje;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Mochila {

	@Id @GeneratedValue
	int id;
	@OneToMany (mappedBy="mochila", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items;
	@ManyToOne Personaje p;

    public Mochila() { this.items = new ArrayList<Item>(); }

    public Mochila(Personaje p) {
        this.p = p;
        this.items = new ArrayList<Item>();
    };
    
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void agregarItem(Item item) {
        if(this.items.size() >= 10) {
            throw new RuntimeException("Mochila llena.");
        } else {
            item.setMochila(this);
            this.items.add(item);
        }
    }

    public Integer itemsEnMochila() {
        return this.items.size();
    }

	public void sacarItem(Item i) {
        i.setMochila(null);
		items.remove(i);
	}

	public Boolean tieneElItem(Item item) {
		Boolean res = false;
        for(Item i : this.items ) {
            if( i.getNombre() == item.getNombre() && i.getUbicacion() == item.getUbicacion() &&
                    i.getAtributos() == item.getAtributos() && i.getClases() == item.getClases() &&
                    i.getCostoDeCompra() == item.getCostoDeCompra() && i.getCostoDeVenta() == item.getCostoDeVenta()) {
                res = true;
                break;
            }
        }
        return res;
	}
}
