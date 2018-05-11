package ar.edu.unq.epers.woe.backend.service.raza;

import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.razadao.RazaDao;
import ar.edu.unq.epers.woe.backend.service.hibernateDAO.HibernateRazaDAO;
import ar.edu.unq.epers.woe.backend.service.hibernateDAO.Runner;

import java.util.ArrayList;
import java.util.List;

public class ServiciosRaza implements RazaService {

    private HibernateRazaDAO razadao;

    public ServiciosRaza() { this.razadao = new HibernateRazaDAO(); }

    //implementación del método crearPersonaje
    public Personaje crearPersonaje(Integer razaId, String nombrePersonaje, Clase clase) {
        Raza razaR = this.getRaza(razaId) ;
		Personaje pj = razaR.crearPersonaje(nombrePersonaje, clase);
		Runner.runInSession(() -> {
        	this.razadao.incrementarPjs(razaId, razaR.getCantidadPersonajes());
        	return null;
		});
        return pj;
    }

    //implementación del método getRaza
    public Raza getRaza(Integer id) {
    	return Runner.runInSession(() -> {
    		return this.razadao.recuperar(id);
    	});
//        
    }

    //implementación del método crearRaza
    public void crearRaza(Raza raza) {
    	Runner.runInSession(() -> {
    		this.razadao.guardar(raza);
    		return null;
    	});
    }

    //implementación del método getAllRazas
    public List<Raza> getAllRazas() {
//        List<Raza> res = new ArrayList<Raza>();
        return (List<Raza>) this.razadao.agregarRazasOrdenadas();
    }


}
