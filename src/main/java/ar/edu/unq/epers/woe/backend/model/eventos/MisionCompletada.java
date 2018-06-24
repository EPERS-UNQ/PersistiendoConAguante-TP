package ar.edu.unq.epers.woe.backend.model.eventos;

import ar.edu.unq.epers.woe.backend.model.evento.Evento;

public class MisionCompletada extends Evento {

	String nombreMision;
	
	public MisionCompletada(String nombrePersonaje, String nombreLugar, String nombreMisionCompl) {
		super(nombrePersonaje, nombreLugar);
		this.nombreMision = nombreMisionCompl;
	}
	public MisionCompletada() { }
	
	public String getNombreMision() {
		return nombreMision;
	}
	
}
