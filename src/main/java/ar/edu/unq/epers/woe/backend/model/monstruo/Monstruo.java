package ar.edu.unq.epers.woe.backend.model.monstruo;

import ar.edu.unq.epers.woe.backend.model.combate.Luchador;
import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;

import javax.persistence.*;

@Entity
public class Monstruo extends Luchador{

//	@Id ahora hay 'nombre' como Id para monstruo
//	@GeneratedValue
//	private int id;

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Vida vida;

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Danho daño;

	private String tipo;

	public Monstruo() {}
	
	public Monstruo(String nombreMonstruo, Vida cantv, Danho cantdaño, String unTipo) {
		this.setNombre(nombreMonstruo);
		this.setVida(cantv);
		this.setDaño(cantdaño);
		this.setTipo(unTipo);
	}

	private void setNombre(String nombreMonstruo) {
			this.nombre = nombreMonstruo;
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
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getTipo() {
		return this.tipo;
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

	
	

