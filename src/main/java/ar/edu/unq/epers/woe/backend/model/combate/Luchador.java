package ar.edu.unq.epers.woe.backend.model.combate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Luchador {

	@Id
	protected String nombre; //único
	
	public abstract Vida getVida();

	public abstract Danho getDanhoTotal();

	public abstract void atacar(Luchador l2);

	public abstract void recibirAtaque(Danho danhoTotal);

	public abstract void setVida(Vida vl1);

	public abstract Danho calcularDanhoRecibido(Danho danho);

	public abstract boolean sosPersonaje();

	public abstract boolean sosMonstruo();

}
