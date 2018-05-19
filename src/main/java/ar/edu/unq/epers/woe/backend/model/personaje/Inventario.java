package ar.edu.unq.epers.woe.backend.model.personaje;

import ar.edu.unq.epers.woe.backend.model.item.Item;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Inventario {

	@Id @GeneratedValue
	private int idInv;

    @OneToMany(mappedBy="inventario", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Slot> slots;

    public Inventario() {
        this.slots = new HashSet<>();
        this.slots.add(new Slot("cabeza", this));
        this.slots.add(new Slot("torso", this));
        this.slots.add(new Slot("piernas", this));
        this.slots.add(new Slot("pies", this));
        this.slots.add(new Slot("izquierda", this));
        this.slots.add(new Slot("derecha", this));
    }

    public int getIdInv() {
        return idInv;
    }

    public void setIdInv(int idInv) {
        this.idInv = idInv;
    }

    public Set<Slot> getSlots() {
        return slots;
    }

    public void setSlots(Set<Slot> slots) {
        this.slots = slots;
    }

    public void setItemEnUnaUbicacion(Item item, Personaje pj) {
        if(this.getEnUbicacion(item.getUbicacion()).getItem() != null) {
            pj.getMochila().agregarItem(this.getEnUbicacion(item.getUbicacion()).getItem());
            pj.decrementarAtributos(this.getEnUbicacion(item.getUbicacion()).getItem().getAtributos());
        }
        this.getEnUbicacion(item.getUbicacion()).setItem(item);
        pj.incrementarAtributos(item.getAtributos());
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
