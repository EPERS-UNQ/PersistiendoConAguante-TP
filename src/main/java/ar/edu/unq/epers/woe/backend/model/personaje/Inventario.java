package ar.edu.unq.epers.woe.backend.model.personaje;

import ar.edu.unq.epers.woe.backend.model.item.Item;

import java.util.HashSet;
import java.util.Set;

public class Inventario {

    private Set<Slot> slots;

    public Inventario() {
        this.slots = new HashSet<>();
        this.slots.add(new Slot("cabeza"));
        this.slots.add(new Slot("torso"));
        this.slots.add(new Slot("piernas"));
        this.slots.add(new Slot("pies"));
        this.slots.add(new Slot("izquierda"));
        this.slots.add(new Slot("derecha"));
    }

    public void setItemEnUnaUbicacion(Item item, Personaje pj) {
        if(this.getEnUbicacion(item.getUbicacion()).getItem() != null) {
            pj.getMochila().agregarItem(this.getEnUbicacion(item.getUbicacion()).getItem());
        }
        this.getEnUbicacion(item.getUbicacion()).setItem(item);
    }

    public Slot getEnUbicacion(String ubicacion) {
        Slot res = null;
        for(Slot s : this.slots) {
            if(s.getUbicacion().equals(ubicacion)) {
                res = s;
            }
        }
        return res;
    }
}
