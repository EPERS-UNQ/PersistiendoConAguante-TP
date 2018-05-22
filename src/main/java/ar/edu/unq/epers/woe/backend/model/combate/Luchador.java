package ar.edu.unq.epers.woe.backend.model.combate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Luchador {

	@Id
	protected String nombre; //único
	
	public Vida getVida() {
		return null;
	}

	public Danho getDanhoTotal() {
		return null;
	}

	public void atacar(Luchador l2) {}

	public void recibirAtaque(Danho danhoTotal){}

	public void setVida(Vida vl1) {}

	public Danho calcularDanhoRecibido(Danho danho) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean sosPersonaje() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean sosMonstruo() {
		// TODO Auto-generated method stub
		return false;
	}
}
