package ar.edu.unq.epers.woe.backend.service.lugar;

import java.util.List;

import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import ar.edu.unq.epers.woe.backend.model.mision.Mision;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

public class LugarService {
	
    /*
     * Devuelve la lista de misiones disponibles para un jugador. 
     * Validar que el personaje se encuentre en una Taberna
     */
	public List<Mision> listarMisiones(Personaje personaje){
		return null;
		
	}
    
	/*
	 * El personaje acepta la mision. 
	 * Validar que el personaje se encuentre en una Taberna y que la mision este disponible.
	 */
    public List<Mision>aceptarMision(Personaje personaje, Mision mision){
		return null;
    	
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
