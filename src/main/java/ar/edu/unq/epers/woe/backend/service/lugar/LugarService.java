package ar.edu.unq.epers.woe.backend.service.lugar;

import java.util.ArrayList;
import java.util.List;
import ar.edu.unq.epers.woe.backend.hibernateDAO.*;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import ar.edu.unq.epers.woe.backend.model.lugar.Taberna;
import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;
import ar.edu.unq.epers.woe.backend.model.mision.Mision;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.neo4jDAO.Neo4jLugarDAO;

public class LugarService {

	private HibernatePersonajeDAO pjhd = new HibernatePersonajeDAO();
	private HibernateItemDAO ihd = new HibernateItemDAO();
	private HibernateMisionDAO imd = new HibernateMisionDAO();
	private HibernateMonstruoDAO imod = new HibernateMonstruoDAO();
	private HibernateLugarDAO ild = new HibernateLugarDAO();
	private Neo4jLugarDAO n4ld = new Neo4jLugarDAO();

    /*
     * Devuelve la lista de misiones disponibles para un jugador.
     * Validar que el personaje se encuentre en una Taberna
     */
	public List<Mision> listarMisiones(String nombrePj){
		return Runner.runInSession(() -> {
			Personaje pj = pjhd.recuperar(nombrePj);
			if(!pj.getLugar().getClass().equals(Taberna.class)) {
				throw new RuntimeException("El Personaje no está en una Taberna.");
			} else {
				List<Mision> res = new ArrayList<Mision>();
				Taberna tab = (Taberna) pj.getLugar();
				for(Mision m : tab.getMisiones()) {
					if(m.puedeAceptarMision(pj)) {
						res.add(m);
					}
				}
			return res; }});
	}

	/*
	 * El personaje acepta la mision.
	 * Validar que el personaje se encuentre en una Taberna y que la mision este disponible.
	 */
    public void aceptarMision(String nombrePj, String nombreMis) {
		Runner.runInSession(() -> {
		Personaje pj = this.pjhd.recuperar(nombrePj);
		Mision m = this.imd.recuperar(nombreMis);
		if(!pj.getLugar().getClass().equals(Taberna.class)) {
			throw new RuntimeException("El Personaje no está en una Taberna.");
		} else if(this.listarMisiones(nombrePj).contains(m)) {
			pj.aceptarMision(m);
		}
		return null; });
    }

    public void moverPermisivo(String nombrePj, String nombrLugar) {
		Runner.runInSession(() -> {
		Personaje pj = this.pjhd.recuperar(nombrePj);
		Lugar lugar = this.ild.recuperar(nombrLugar);
        pj.cambiarDeLugar(lugar);
        return null; });
    }

    /*
     * Devuelve una lista de items disponibles que tiene la tienda.
     * Validar que el personaje se encuentre en una Tienda.
     */
    public List<Item> listarItems(String nombrePj) {
		return Runner.runInSession(() -> {
			Personaje pj = pjhd.recuperar(nombrePj);
			if(!pj.getLugar().getClass().equals(Tienda.class)) {
				throw new RuntimeException("El Personaje no está en una Tienda.");
			} else {
				List<Item> res = new ArrayList<Item>();
				Tienda tie = (Tienda) pj.getLugar();
				for(Item i : tie.getItems()) {
					res.add(i);
				}
				return res; }});
    }

    /*
     * El personaje obtiene el item y se le debita el costo del mismo.
     * Validar que tenga la cantidad de monedas necesarias para comprar el item.
     */
    public void comprarItem(String nombrePj, int idItem) {
		Runner.runInSession(() -> {
		Personaje pj = pjhd.recuperar(nombrePj);
		Item i = ihd.recuperar(idItem);
		if(!pj.getLugar().getClass().equals(Tienda.class)) {
			throw new RuntimeException("El Personaje no está en una Tienda.");
		} else if(pj.getBilletera() < i.getCostoDeCompra()) {
			throw new RuntimeException("Dinero insuficiente para comprar el item.");
		} else {
			pj.comprar(i);
			return null; }});
    }

    /*
     * El personaje vende el item y se le acredita el costo del mismo.
     */
    public void venderItem(String nombrePj, int idItem) {
		Runner.runInSession(() -> {
			Personaje pj = pjhd.recuperar(nombrePj);
			Item i = ihd.recuperar(idItem);
			if (!pj.getLugar().getClass().equals(Tienda.class)) {
				throw new RuntimeException("El Personaje no está en una Tienda.");
			} else if(pj.tieneElItem(i)) {
				pj.vender(i);
			}
			return null;
		});
	}

	public void crearUbicacion(Lugar l) {
		Runner.runInSession(() -> {
			this.ild.guardar(l);
			return null;
		});
    	this.n4ld.create(l);
	}

	public void conectar(String ubicacion1, String ubicacion2, String tipoCamino) {
    	this.n4ld.crearRelacionConectadoCon(ubicacion1, ubicacion2, tipoCamino);
	}

	public List<Lugar> conectados(String ubicacion, String tipoCamino) {
    	List<Lugar> res = new ArrayList<Lugar>();
    	for(String nombreLugar : this.n4ld.conectadosCon(ubicacion, tipoCamino)) {
			Runner.runInSession(() -> { res.add(this.ild.recuperar(nombreLugar)); return null; });
		}
		return res;
	}

	public void validarRequisitosParaMover(Personaje pj, String ubicacion, String criterio) {
		if(!this.n4ld.existeCaminoEntre(pj.getLugar().getNombre(), ubicacion)) {
			throw new UbicacionMuyLejana(pj.getLugar().getNombre(), ubicacion);
		}
    	Integer costoViaje = null;
    	switch(criterio) {
			case "masCorto": costoViaje = this.n4ld.costoRutaMasCorta(pj.getLugar().getNombre(), ubicacion); break;
			case "masBarato": costoViaje = this.n4ld.costoRutaMasBarata(pj.getLugar().getNombre(), ubicacion); break;
		}
		if(costoViaje > pj.getBilletera()) {
			throw new CaminoMuyCostoso(costoViaje, pj.getBilletera());
		}
	}

	public void moverMasCorto(String personaje, String ubicacion) {
		Runner.runInSession(() -> {
			Personaje pj = this.pjhd.recuperar(personaje);
			validarRequisitosParaMover(pj, ubicacion, "masCorto");
				Lugar lr = this.ild.recuperar(ubicacion);
				pj.gastarBilletera(this.n4ld.costoRutaMasCorta(pj.getLugar().getNombre(), ubicacion));
				pj.cambiarDeLugar(lr);
		return null; });
	}

	/*
	 * Cambia la ubicación actual del personaje por la especificada por parámetro.
	 */
	public void mover(String personaje, String ubicacion) {
		Runner.runInSession(() -> {
			Personaje pj = this.pjhd.recuperar(personaje);
			validarRequisitosParaMover(pj, ubicacion, "masBarato");
			Lugar lr = this.ild.recuperar(ubicacion);
			pj.gastarBilletera(this.n4ld.costoRutaMasBarata(pj.getLugar().getNombre(), ubicacion));
			pj.cambiarDeLugar(lr);
		return null; });
	}
}
