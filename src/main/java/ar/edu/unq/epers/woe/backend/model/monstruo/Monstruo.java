package ar.edu.unq.epers.woe.backend.model.monstruo;

import ar.edu.unq.epers.woe.backend.model.combate.Luchador;
import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;

import javax.persistence.*;

@Entity
public class Monstruo extends Luchador{

	@Id
	@GeneratedValue
	private Integer idMonstruo;

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Vida vida;

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Danho daño;

	private String tipo;

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Raza raza;

	public Monstruo() {}
	
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

	public Vida getVida() {
		return this.vida;
	}
	
	public Danho getDanhoTotal() {
		return this.daño;
		}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public Raza getRaza() {
		return this.raza;
	}

	@Override
	public void atacar(Luchador l) {
		l.recibirAtaque(this.getDanhoTotal());

	}
	@Override
	public void recibirAtaque(Danho danhoTotal) {
		this.calcularDanhoRecibido( danhoTotal) ;
		this.setVida(new Vida(this.getVida().getValor() - danhoTotal.getValor()));
	}
	
	@Override
	public Danho calcularDanhoRecibido(Danho danho) {
	   return danho;
	}
	
	@Override
	public boolean sosPersonaje() {
		return false;
	}
	
	@Override
	public boolean sosMonstruo() {
		return true;
	}



}

	
	

