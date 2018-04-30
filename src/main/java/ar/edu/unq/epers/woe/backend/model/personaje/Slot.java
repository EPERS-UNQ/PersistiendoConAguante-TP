package ar.edu.unq.epers.woe.backend.model.personaje;

import ar.edu.unq.epers.woe.backend.model.item.Item;

public class Slot {

    private String ubicacion;
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
