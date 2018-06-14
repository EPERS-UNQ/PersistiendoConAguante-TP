package ar.edu.unq.epers.woe.backend.model.mision;

import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.lugar.Taberna;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISCRIMINATOR", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("MISION")
public class Mision {

    @Id
    private String nombre; //único

    @ElementCollection
    private Set<String> prereqs = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Recompensa recompensa;

    @ManyToOne
    private Personaje pjOwner;

    @ManyToOne
    private Taberna taberna;

    public Mision() {};

    public Mision(String nombre, Recompensa recompensa) {
        this.nombre = nombre;
        this.recompensa = recompensa;
    }
    public Mision(String nombre, Set<String> prereqs, Recompensa recompensa) {
        this.nombre = nombre;
        this.prereqs = prereqs;
        this.recompensa = recompensa;
    }

    public Recompensa getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(Recompensa recompensa) {
        this.recompensa = recompensa;
    }

    public Taberna getTaberna() {
        return taberna;
    }

    public void setTaberna(Taberna taberna) {
        this.taberna = taberna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<String> getPrereqs() {
        return prereqs;
    }

    public void setPrereqs(Set<String> prereqs) {
        this.prereqs = prereqs;
    }

    public Personaje getPjOwner() {
        return pjOwner;
    }

    public void setPjOwner(Personaje pjOwner) {
        this.pjOwner = pjOwner;
    }

    public Boolean misionDisponiblePara(Personaje pj) {
        Boolean res = true;
        for(String nm : this.prereqs) {
            if(!pj.getMisionesCumplidas().contains(nm)) {
                res = false;
                break;
            }
        }
        return res;
    }

    public Boolean puedeAceptarMision(Personaje pj) {
        return misionDisponiblePara(pj) && (pj.getLugar().getClass() == Taberna.class);
    }

    public Boolean misionAceptadaPor(Personaje pj) {
        return pj.getMisionesAceptadas().contains(this.nombre);
    }

    public Boolean misionCumplidaPor(Personaje pj) {
        Boolean res = false;
        for(String m : pj.getMisionesCumplidas()) {
            if(m.equals(this.nombre)) {
                res = true;
                break;
            }
        }
        return res;
    }

    public Boolean puedeCompletarMision(Personaje pj) {return false;}

    public void incrementarVictoriasActualesSiPuede(ResultadoCombate resComb) {}

    public void cumplirMisionSiPuede() {
        if(puedeCompletarMision(this.getPjOwner())) {
            this.getPjOwner().getMisionesEnCurso().remove(this);
            this.getPjOwner().getMisionesAceptadas().remove(this.getNombre());
            this.getPjOwner().getMisionesCumplidas().add(this.getNombre());
            this.getRecompensa().otorgarRecompensaA(this.getPjOwner());
            this.setPjOwner(null);
        }
    }

}
