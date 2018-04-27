package ar.edu.unq.epers.woe.backend.service.raza;

import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.razadao.RazaDao;

import java.util.ArrayList;
import java.util.List;

public class ServiciosRaza implements RazaService {

    private RazaDao razadao;

    public ServiciosRaza() { this.razadao = new RazaDao(); }

    //implementación del método crearPersonaje
    public Personaje crearPersonaje(Integer razaId, String nombrePersonaje, Clase clase) {
        Personaje pj = this.getRaza(razaId).crearPersonaje(nombrePersonaje, clase);
        this.razadao.incrementarPjs(razaId);
        return pj;
    }

    //implementación del método getRaza
    public Raza getRaza(Integer id) {
        Raza raza = new Raza();
        this.razadao.recuperarRaza(id, raza);
        return raza;
    }

    //implementación del método crearRaza
    public void crearRaza(Raza raza) {
        this.razadao.guardar(raza);
    }

    //implementación del método getAllRazas
    public List<Raza> getAllRazas() {
        List<Raza> res = new ArrayList<Raza>();
        this.razadao.agregarRazasOrdenadas(res);
        return res;
    }


}
