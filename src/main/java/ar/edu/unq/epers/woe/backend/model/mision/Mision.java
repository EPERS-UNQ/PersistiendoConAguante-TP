package ar.edu.unq.epers.woe.backend.model.mision;

import ar.edu.unq.epers.woe.backend.model.lugar.Taberna;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import java.util.HashSet;
import java.util.Set;

public class Mision {

    private String nombre; //único
    private Set<String> prereqs = new HashSet<>();
    private Recompensa recompensa;

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

}
