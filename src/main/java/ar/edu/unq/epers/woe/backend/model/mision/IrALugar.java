package ar.edu.unq.epers.woe.backend.model.mision;

import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import java.util.Set;

public class IrALugar extends Mision {

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




}
