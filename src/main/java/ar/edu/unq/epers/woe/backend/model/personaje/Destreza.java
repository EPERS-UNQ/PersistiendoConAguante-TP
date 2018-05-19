package ar.edu.unq.epers.woe.backend.model.personaje;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DESTREZA")
public class Destreza extends Atributo {

    public Destreza() {}

    public Destreza(Float valor, Personaje p) {
        this.setValor(valor);
        this.setPersonaje(p); }

    public Destreza(Float valor) {
        this.setValor(valor);
    }

}