package ar.edu.unq.epers.woe.backend.model.personaje;

import javax.persistence.*;
import ar.edu.unq.epers.woe.backend.model.item.Item;
@Entity
public class Slot {
	
	@Id @GeneratedValue int id;
    private String ubicacion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;

    public Slot(String ubicacion) {
        this.setUbicacion(ubicacion);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
