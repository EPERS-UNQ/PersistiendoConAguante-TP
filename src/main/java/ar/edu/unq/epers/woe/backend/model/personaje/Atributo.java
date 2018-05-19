package ar.edu.unq.epers.woe.backend.model.personaje;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;
import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISCRIMINATOR", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("ATRIBUTO")
public class Atributo {

    public Atributo() {}

    @Id	@GeneratedValue
    private int idAtrib;

    @ManyToOne
    private Personaje personaje;

    @ManyToOne
    private Item item;

    @ManyToOne
    private Requerimiento requerimiento;

    public int getIdAtrib() {
        return idAtrib;
    }

    public void setIdAtrib(int idAtrib) {
        this.idAtrib = idAtrib;
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Float getValor() {
        return valor;
    }

    private Float valor;

}
