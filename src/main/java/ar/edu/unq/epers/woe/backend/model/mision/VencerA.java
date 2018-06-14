package ar.edu.unq.epers.woe.backend.model.mision;

import ar.edu.unq.epers.woe.backend.model.combate.Luchador;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.monstruo.Monstruo;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
@DiscriminatorValue("VENCERA")
public class VencerA extends Mision {

    @OneToOne
    private Personaje rivalPj;

    @OneToOne
    private Monstruo rivalMonstruo;

    private Integer victoriasActuales = 0, victoriasReq = 0;

    public VencerA() {}

    public VencerA(String nombre, Recompensa recompensa, Luchador rival, Integer victoriasReq) {
        super(nombre, recompensa);
        this.victoriasReq = victoriasReq;
        if(rival.sosPersonaje()) {
            this.rivalPj = (Personaje) rival;
        } else if(rival.sosMonstruo()) {
            this.rivalMonstruo = (Monstruo) rival;
        }
    }

    public VencerA(String nombre, Set<String> prereqs, Recompensa recompensa, Luchador rival, Integer victoriasReq) {
        super(nombre, prereqs, recompensa);
        this.victoriasReq = victoriasReq;
        if(rival.sosPersonaje()) {
            this.rivalPj = (Personaje) rival;
        } else if(rival.sosMonstruo()) {
            this.rivalMonstruo = (Monstruo) rival;
        }
    }

    public Personaje getRivalPj() {
        return rivalPj;
    }

    public void setRivalPj(Personaje rivalPj) {
        this.rivalPj = rivalPj;
    }

    public Monstruo getRivalMonstruo() {
        return rivalMonstruo;
    }

    public void setRivalMonstruo(Monstruo rivalMonstruo) {
        this.rivalMonstruo = rivalMonstruo;
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

    @Override
    public void incrementarVictoriasActualesSiPuede(ResultadoCombate resComb) {
        if((resComb.getGanador().sosPersonaje()) && (this.getRivalPj() != null)
            && resComb.getPerdedor().sosPersonaje()) {
            Personaje ganador = (Personaje) resComb.getGanador();
            Personaje perdedor = (Personaje) resComb.getPerdedor();
            Personaje rival = (Personaje) this.getRivalPj();
            incVictSiGanoOwnerVsPj(ganador, perdedor, rival);
        } else if((resComb.getGanador().sosPersonaje()) && (this.getRivalMonstruo() != null)
                   && resComb.getPerdedor().sosMonstruo()) {
            Personaje ganador = (Personaje) resComb.getGanador();
            Monstruo perdedor = (Monstruo) resComb.getPerdedor();
            Monstruo rival = (Monstruo) this.getRivalMonstruo();
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
