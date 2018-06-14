package ar.edu.unq.epers.woe.backend.service.personaje;

import ar.edu.unq.epers.woe.backend.hibernateDAO.HibernateCombateDAO;
import ar.edu.unq.epers.woe.backend.hibernateDAO.HibernatePersonajeDAO;
import ar.edu.unq.epers.woe.backend.hibernateDAO.Runner;
import ar.edu.unq.epers.woe.backend.model.combate.Combate;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Gimnasio;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.hibernateDAO.HibernateItemDAO;

public class PersonajeService {

    private HibernatePersonajeDAO pjhd = new HibernatePersonajeDAO();
    private HibernateItemDAO ihd = new HibernateItemDAO();
    private HibernateCombateDAO icd = new HibernateCombateDAO();

    public void equipar(String nombrePj, Integer item) {
            Runner.runInSession(() -> {
                Item i = ihd.recuperar(item);
                Personaje pj = pjhd.recuperar(nombrePj);
                if(i.getRequerimiento().cumpleRequerimiento(pj) && pj.getMochila().getItems().contains(i)
                        && i.getClases().contains(pj.getClase())) {
                    i.setMochila(null);
                    pj.getInventario().setItemEnUnaUbicacion(i, pj);
                    pj.getMochila().getItems().remove(i);
                }
            return null; });
    }

    public ResultadoCombate combatir(String nombrePj1, String nombrePj2) {
        return Runner.runInSession(() -> {
            Personaje pj1 = pjhd.recuperar(nombrePj1);
            Personaje pj2 = pjhd.recuperar(nombrePj2);
            if(!pj1.getLugar().getClass().equals(Gimnasio.class) || !pj2.getLugar().getClass().equals(Gimnasio.class)) {
                throw new RuntimeException("Alguno de los personajes no está en un gimnasio.");
            } else {
                ResultadoCombate resultadoCombate = new Combate().combatir(pj1, pj2);
                this.icd.guardar(resultadoCombate);
                return resultadoCombate;
            }});
    }

}
