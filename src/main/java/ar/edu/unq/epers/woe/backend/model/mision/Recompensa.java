package ar.edu.unq.epers.woe.backend.model.mision;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recompensa {

    @Id @GeneratedValue
    private Integer id;
    @OneToMany(mappedBy="recompensa", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<Item>();
    private Integer exp = 0;
    private Float monedas = 0f;

    public Recompensa() {}

    public Recompensa(List<Item> items, Integer exp, Float monedas) {
        this.items = items;
        this.exp = exp;
        this.monedas = monedas;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Float getMonedas() {
        return monedas;
    }

    public void setMonedas(Float monedas) {
        this.monedas = monedas;
    }

    public void otorgarRecompensaA(Personaje pj) {
        pj.ganarExperiencia(this.exp);
        pj.setBilletera(pj.getBilletera() + this.monedas);
        for(Item i : this.items) {
            pj.getMochila().agregarItem(i);
        }
    }
}
