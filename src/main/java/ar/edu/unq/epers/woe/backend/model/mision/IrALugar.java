package ar.edu.unq.epers.woe.backend.model.mision;

import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
@DiscriminatorValue("IRALUGAR")
public class IrALugar extends Mision {

    @OneToOne
    private Lugar destino;

    public IrALugar(String nombre, Recompensa recompensa, Lugar destino) {
        super(nombre, recompensa);
        this.destino = destino;
    }

    public IrALugar(String nombre, Set<String> prereqs, Recompensa recompensa, Lugar destino) {
        super(nombre, prereqs, recompensa);
        this.destino = destino;
    }

    public Lugar getDestino() {
        return destino;
    }

    public void setDestino(Lugar destino) {
        this.destino = destino;
    }

    @Override
    public Boolean puedeCompletarMision(Personaje pj) {
        return this.misionAceptadaPor(pj) && (pj.getLugar().getClass() == this.destino.getClass());
    }

    @Override
    public Boolean esIrALugar() { return true; }




}
