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
    private int id;

    @ManyToOne
    private Item item;

    @ManyToOne
    private Requerimiento requerimiento;

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Float getValor() {
        return valor;
    }

    private Float valor;

}
