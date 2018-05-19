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
    private List<Item> items = new ArrayList<Item>();
	@ManyToOne Personaje p;

    public Mochila() {};
    
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
		items.remove(i);
	}

	public Boolean tieneElItem(Item item) {
		Boolean res = false;
        for(Item i : this.items ) {
            if( i == item ) {
                res = true;
                break;
            }
        }
        return res;
	}
}
