package ar.edu.unq.epers.woe.backend.model.evento;

import ar.edu.unq.epers.woe.backend.model.evento.Evento;

public class MisionAceptada extends Evento {

	String nombreMision;
	
	public MisionAceptada(String nombrePersonaje, String nombreLugar, String nombreMision) {
		super(nombrePersonaje, nombreLugar);
		this.nombreMision = nombreMision;
	}
	public MisionAceptada() { }
	
	public String getNombreMision() {
		return nombreMision;
	}
}
