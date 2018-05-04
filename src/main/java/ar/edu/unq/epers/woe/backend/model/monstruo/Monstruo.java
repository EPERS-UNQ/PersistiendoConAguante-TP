package ar.edu.unq.epers.woe.backend.model.monstruo;

import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;

public class Monstruo {
	private Vida vida;
	private Danho daño;
	private String tipo;
	private Raza raza;
	
	public Monstruo ( Vida cantv, Danho cantdaño, String unTipo, Raza unaRaza){
		this.setVida(cantv);
		this.setDaño(cantdaño);
		this.setTipo(unTipo);
		this.setRaza(unaRaza);
		
	}
	public void setRaza(Raza unaRaza) {
		this.raza= unaRaza;
	}
	public void setTipo(String unTipo) {
		this.tipo = unTipo;
	}
	public void setDaño(Danho cantdaño) {
		this.daño = cantdaño;
	}
	public void setVida(Vida cantv2) {
		this.vida = cantv2;
		
		
	}
}