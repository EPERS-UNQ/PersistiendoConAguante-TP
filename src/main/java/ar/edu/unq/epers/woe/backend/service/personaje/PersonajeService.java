package ar.edu.unq.epers.woe.backend.service.personaje;

import ar.edu.unq.epers.woe.backend.hibernateDAO.HibernatePersonajeDAO;
import ar.edu.unq.epers.woe.backend.hibernateDAO.Runner;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.hibernateDAO.HibernateItemDAO;

public class PersonajeService {

    private HibernatePersonajeDAO pjhd = new HibernatePersonajeDAO();
    private HibernateItemDAO ihd = new HibernateItemDAO();

    public void equipar(String nombrePj, Integer item) {
            Runner.runInSession(() -> {
                Item i = ihd.recuperar(item);
                Personaje pj = pjhd.recuperar(nombrePj);
                if(i.getRequerimiento().cumpleRequerimiento(pj) && pj.getMochila().getItems().contains(i)) {
                    i.setMochila(null);
                    pj.getInventario().setItemEnUnaUbicacion(i, pj);
                    pj.getMochila().getItems().remove(i);
                }
            return null; });
    }
}
