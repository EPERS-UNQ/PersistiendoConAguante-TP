package ar.edu.unq.epers.woe.backend.model.personaje;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("VIDA")
public class Vida extends Atributo {

    public Vida() {}

    public Vida(Float valor, Personaje p) {
        this.setValor(valor);
        this.setPersonaje(p); }

    public Vida(Float valor) {
        this.setValor(valor);
    }

}