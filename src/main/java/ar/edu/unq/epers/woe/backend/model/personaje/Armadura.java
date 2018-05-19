package ar.edu.unq.epers.woe.backend.model.personaje;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ARMADURA")
public class Armadura extends Atributo {

    public Armadura() {}

    public Armadura(Float valor, Personaje p) {
        this.setValor(valor);
        this.setPersonaje(p); }

    public Armadura(Float valor) {
        this.setValor(valor);
    }

}
