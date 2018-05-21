package ar.edu.unq.epers.woe.backend.service.lugar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ar.edu.unq.epers.woe.backend.hibernateDAO.*;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import ar.edu.unq.epers.woe.backend.model.lugar.Taberna;
import ar.edu.unq.epers.woe.backend.model.mision.Mision;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class LugarService {

	private HibernatePersonajeDAO pjhd = new HibernatePersonajeDAO();
	private HibernateItemDAO ihd = new HibernateItemDAO();
	private HibernateMisionDAO imd = new HibernateMisionDAO();
	private HibernateMonstruoDAO imod = new HibernateMonstruoDAO();
	
    /*
     * Devuelve la lista de misiones disponibles para un jugador. 
     * Validar que el personaje se encuentre en una Taberna
     */
	public List<Mision> listarMisiones(String nombrePj){
		return Runner.runInSession(() -> {
			Personaje pj = pjhd.recuperar(nombrePj);
			if(!pj.getLugar().esTaberna()) {
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
    public void aceptarMision(Personaje personaje, Mision mision){
//		if((personaje.getLugar().esTaberna())
//		   && (listarMisiones(personaje).contains(mision))) {
//			   personaje.aceptarMision(mision);
//		}
    }
    
    /*
     * Cambia la ubicación actual del personaje por la especificada por parámetro.
     */
    
    public void mover(Personaje pj, Lugar lugar) {
        pj.setLugar(lugar);
    }
    
    /*
     * Devuelve una lista de items disponibles que tiene la tienda.
     * Validar que el personaje se encuentre en una Tienda.
     */

    public List<Item> listarItems(Personaje personaje) {
		return null;
    	
    }
    
    /*
     * El personaje obtiene el item y se le debita el costo del mismo.
     * Validar que tenga la cantidad de monedas necesarias para comprar el item.
     */

    public void comprarItem(Personaje personaje, Item item) {
    	
    }
    
    /* 
     * El personaje vende el item y se le acredita el costo del mismo.
     */
    public void venderItem(Personaje personaje, Item item) {
    	
    }


    
    
}
