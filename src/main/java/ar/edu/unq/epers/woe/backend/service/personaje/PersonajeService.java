package ar.edu.unq.epers.woe.backend.service.personaje;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class PersonajeService {

    public void equipar(Personaje pj, Item item) {
        if(item.getRequerimiento().cumpleRequerimiento(pj) && pj.getMochila().getItems().contains(item)) {
            pj.getInventario().setItemEnUnaUbicacion(item, pj);
            pj.getMochila().getItems().remove(item);
        }
    }
}
