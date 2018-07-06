package ar.edu.unq.epers.woe.backend.service.personaje;

import ar.edu.unq.epers.woe.backend.hibernateDAO.HibernateCombateDAO;
import ar.edu.unq.epers.woe.backend.hibernateDAO.HibernatePersonajeDAO;
import ar.edu.unq.epers.woe.backend.hibernateDAO.Runner;
import ar.edu.unq.epers.woe.backend.model.combate.Combate;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.evento.Ganador;
import ar.edu.unq.epers.woe.backend.model.evento.MisionCompletada;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Gimnasio;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.hibernateDAO.HibernateItemDAO;
import ar.edu.unq.epers.woe.backend.mongoDAO.EventoMongoDAO;
import ar.edu.unq.epers.woe.backend.service.cache.CacheGenerator;

import java.util.*;

public class PersonajeService {

    private HibernatePersonajeDAO pjhd = new HibernatePersonajeDAO();
    private HibernateItemDAO ihd = new HibernateItemDAO();
    private HibernateCombateDAO icd = new HibernateCombateDAO();
    private EventoMongoDAO emd = new EventoMongoDAO();
    private CacheGenerator cg = new CacheGenerator();

    public void equipar(String nombrePj, Integer item) {
            Runner.runInSession(() -> {
                Item i = this.ihd.recuperar(item);
                Personaje pj = this.pjhd.recuperar(nombrePj);
                if(i.getRequerimiento().cumpleRequerimiento(pj) && pj.getMochila().getItems().contains(i)
                        && i.getClases().contains(pj.getClase())) {
                    i.setMochila(null);
                    pj.getInventario().setItemEnUnaUbicacion(i, pj);
                    pj.getMochila().getItems().remove(i);
                    this.cg.setCacheMasFuerte(pj.getNombre());
                }
            return null; });
    }

    public ResultadoCombate combatir(String nombrePj1, String nombrePj2) {
        return Runner.runInSession(() -> {
            Personaje pj1 = this.pjhd.recuperar(nombrePj1);
            Personaje pj2 = this.pjhd.recuperar(nombrePj2);
            if(!pj1.getLugar().getClass().equals(Gimnasio.class) || !pj2.getLugar().getClass().equals(Gimnasio.class)) {
                throw new RuntimeException("Alguno de los personajes no está en un gimnasio.");
            } else {
                List<String> l1 = misionesCumplidasPor(pj1);
                List<String> l2 = misionesCumplidasPor(pj2);
                ResultadoCombate resultadoCombate = new Combate().combatir(pj1, pj2);
                this.icd.guardar(resultadoCombate);
                generarEventosSiCorresponde(resultadoCombate, l1, l2);
                this.cg.setCacheMasFuerte(pj1.getNombre());
                this.cg.setCacheMasFuerte(pj2.getNombre());
                return resultadoCombate;
            }});
    }

    public List<String> misionesCumplidasPor(Personaje pj) {
        List<String> res = new ArrayList<>();
        res.add(pj.getNombre());
        res.addAll(pj.getMisionesCumplidas());
        return res;
    }

    private void generarEventosSiCorresponde(ResultadoCombate resultadoCombate, List<String> l1, List<String> l2) {
        Personaje g = (Personaje) resultadoCombate.getGanador();
        Personaje p = (Personaje) resultadoCombate.getPerdedor();
        this.emd.save(new Ganador(g.getNombre(), g.getLugar().getNombre(), p.getNombre(), p.getClase().name(),
                      g.getClase().name(), p.getRaza().getNombre(), g.getRaza().getNombre()));
        List<String> mis = new ArrayList<>(g.getMisionesCumplidas());
        MisionCompletada eventoMisionC = new MisionCompletada(g.getNombre(), g.getLugar().getNombre(), mis.iterator().next());
		if(g.getNombre().equals(l1.get(0)) && g.getMisionesCumplidas().size() > (l1.size()-1)) {
            mis.removeAll(l1);
            this.emd.save(eventoMisionC);
        } else if(g.getNombre().equals(l2.get(0)) && g.getMisionesCumplidas().size() > (l2.size()-1)) {
            mis.removeAll(l2);
            this.emd.save(eventoMisionC);
        }
    }

}
