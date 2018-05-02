package ar.edu.unq.epers.woe.backend.model.requerimiento;

import ar.edu.unq.epers.woe.backend.model.personaje.Atributo;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

import java.util.HashSet;
import java.util.Set;

public class Requerimiento {
    private Integer nivel_req = 0;
    private Set<Atributo> atributos_req = new HashSet<>();


    public Requerimiento(Integer nivel_req, Set<Atributo> atributos_req) {
        this.nivel_req = nivel_req;
        this.atributos_req = atributos_req;
    }

    public Integer getNivel_req() {
        return nivel_req;
    }

    public void setNivel_req(Integer nivel_req) {
        this.nivel_req = nivel_req;
    }

    public Set<Atributo> getAtributos_req() {
        return atributos_req;
    }

    public void setAtributos_req(Set<Atributo> atributos_req) {
        this.atributos_req = atributos_req;
    }

    public Boolean cumpleRequerimiento(Personaje pj) {
        Boolean res = true;
        Boolean cumple_lvl = pj.getNivel() >= this.getNivel_req();
        Boolean cumple_atribs = cumpleAtributos(pj, this.atributos_req);
        return cumple_lvl && cumple_atribs;
    }

    private Boolean cumpleAtributos(Personaje pj, Set<Atributo> atributos_req) {
        Boolean res = true;
        for(Atributo a : atributos_req) {
            if(a.getValor() > pj.getAtributo(a.getClass()).getValor()) {
                res = false;
                break;
            }
        }
        return res;
    }
}
