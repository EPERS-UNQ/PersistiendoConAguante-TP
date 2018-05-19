package ar.edu.unq.epers.woe.backend.model.personaje;

import javax.persistence.*;
import ar.edu.unq.epers.woe.backend.model.item.Item;
@Entity
public class Slot {
	
	@Id @GeneratedValue int idSlot;
    private String ubicacion;
    
    @OneToOne(fetch = FetchType.EAGER)
    private Item item;

    @ManyToOne
    private Inventario inventario;

    public int getIdSlot() {
        return idSlot;
    }

    public void setIdSlot(int idSlot) {
        this.idSlot = idSlot;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Slot() {}

    public Slot(String ubicacion) {
        this.setUbicacion(ubicacion);
    }

    public Slot(String ubicacion, Inventario inventario) {
        this.setUbicacion(ubicacion);
        this.setInventario(inventario);
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
