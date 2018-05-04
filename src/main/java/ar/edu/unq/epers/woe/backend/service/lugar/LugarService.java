package ar.edu.unq.epers.woe.backend.service.lugar;

import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class LugarService {

    public void mover(Personaje pj, Lugar lugar) {
        pj.setLugar(lugar);
    }
    
}
