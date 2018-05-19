package ar.edu.unq.epers.woe.backend.model.personaje;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FUERZA")
public class Fuerza  extends Atributo {

    public Fuerza() {}

    public Fuerza(Float valor, Personaje p) {
        this.setValor(valor);
        this.setPersonaje(p); }

    public Fuerza(Float valor) {
        this.setValor(valor);
    }

}
