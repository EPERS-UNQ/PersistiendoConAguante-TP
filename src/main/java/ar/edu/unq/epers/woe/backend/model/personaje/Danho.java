package ar.edu.unq.epers.woe.backend.model.personaje;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DANHO")
public class Danho extends Atributo {

    public Danho() {}

    public Danho(Float valor, Personaje p) {
        this.setValor(valor);
        this.setPersonaje(p); }

    public Danho(Float valor) {
        this.setValor(valor);
    }

}
