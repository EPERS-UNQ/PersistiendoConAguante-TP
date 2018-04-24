package ar.edu.unq.epers.woe.backend.model.personaje;

import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import java.util.Set;


/**
 * Un {@link Personaje} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Raza} en particular.
 *
 * @author Charly Backend
 */
public class Personaje {

	private String nombre; //único
	private Raza raza;
	private Clase clase;
	private Integer nivel;
	private Integer exp;
	private Float billetera;
	//private Inventario inventario;
	//private mochila Set<Item>;
	private Set<Atributo> atributos;
	//private lugar Lugar;


	public Personaje(Raza raza, String nombre, Clase clase) {
		this.raza = raza;
		this.nombre = nombre;
		this.clase = clase;
	}


	// Getters y Setters
	public String getNombre() {
		return this.nombre;
	}

	public Raza getRaza() {
		return this.raza;
	}

	public Clase getClase() {
		return clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}

	public Integer getNivel() {
		return nivel;
	}

	public Integer getExp() {
		return exp;
	}

	public Float getBilletera() {
		return billetera;
	}

	public Set<Atributo> getAtributos() {
		return atributos;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setRaza(Raza raza) {
		this.raza = raza;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public void setBilletera(Float billetera) {
		this.billetera = billetera;
	}

	public void setAtributos(Set<Atributo> atributos) {
		this.atributos = atributos;
	}
	//

}