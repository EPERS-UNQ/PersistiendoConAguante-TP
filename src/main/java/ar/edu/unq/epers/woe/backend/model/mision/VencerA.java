package ar.edu.unq.epers.woe.backend.model.mision;

import ar.edu.unq.epers.woe.backend.model.combate.Luchador;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.monstruo.Monstruo;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

import java.util.Set;

//@Entity
//@DiscriminatorValue("VENCERA")
public class VencerA extends Mision {

    private Luchador rival;

    private Integer victoriasActuales = 0, victoriasReq = 0;

    public VencerA() {}

    public VencerA(String nombre, Recompensa recompensa, Luchador rival, Integer victoriasReq) {
        super(nombre, recompensa);
        this.rival = rival;
        this.victoriasReq = victoriasReq;
    }

    public VencerA(String nombre, Set<String> prereqs, Recompensa recompensa, Luchador rival, Integer victoriasReq) {
        super(nombre, prereqs, recompensa);
        this.rival = rival;
        this.victoriasReq = victoriasReq;
    }

    public Luchador getRival() {
        return rival;
    }

    public void setRival(Luchador rival) {
        this.rival = rival;
    }

    public Integer getVictoriasReq() {
        return victoriasReq;
    }

    public void setVictoriasReq(Integer victoriasReq) {
        this.victoriasReq = victoriasReq;
    }

    public Integer getVictoriasActuales() {
        return victoriasActuales;
    }

    public void setVictoriasActuales(Integer victoriasActuales) {
        this.victoriasActuales = victoriasActuales;
    }

    public void incrementarVictoriasActualesSiPuede(ResultadoCombate resComb) {
        if((resComb.getGanador().sosPersonaje()) && (this.getRival().sosPersonaje())
            && resComb.getPerdedor().sosPersonaje()) {
            Personaje ganador = (Personaje) resComb.getGanador();
            Personaje perdedor = (Personaje) resComb.getPerdedor();
            Personaje rival = (Personaje) this.getRival();
            incVictSiGanoOwnerVsPj(ganador, perdedor, rival);
        } else if((resComb.getGanador().sosPersonaje()) && (this.getRival().sosMonstruo())
                   && resComb.getPerdedor().sosMonstruo()) {
            Personaje ganador = (Personaje) resComb.getGanador();
            Monstruo perdedor = (Monstruo) resComb.getPerdedor();
            Monstruo rival = (Monstruo) this.getRival();
            incVictSiGanoOwnerVsMonstruo(ganador, perdedor, rival);
        }
    }

    private void incVictSiGanoOwnerVsMonstruo(Personaje ganador, Monstruo perdedor, Monstruo rival) {
        if((ganador.getNombre() == this.getPjOwner().getNombre()) && perdedor.getTipo() == rival.getTipo()) {
            this.victoriasActuales++;
        }
    }

    private void incVictSiGanoOwnerVsPj(Personaje ganador, Personaje perdedor, Personaje rival) {
        if((ganador.getNombre() == this.getPjOwner().getNombre()) && perdedor.getNombre() == rival.getNombre()) {
            this.victoriasActuales++;
        }
    }

    @Override
    public Boolean puedeCompletarMision(Personaje pj) {
        return this.misionAceptadaPor(pj) && (this.victoriasActuales >= this.victoriasReq);
    }



}
