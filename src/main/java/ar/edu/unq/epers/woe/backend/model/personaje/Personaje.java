package ar.edu.unq.epers.woe.backend.model.personaje;

import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import java.util.HashSet;
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
	private Inventario inventario;

	public Mochila getMochila() {
		return mochila;
	}

	public void setMochila(Mochila mochila) {
		this.mochila = mochila;
	}

	private Mochila mochila;
	private Set<Atributo> atributos;
	//private lugar Lugar;


	public Personaje(Raza raza, String nombre, Clase clase) {
		this.raza = raza;
		this.nombre = nombre;
		this.clase = clase;
		this.nivel = 1;
		this.exp = 0;
		this.mochila = new Mochila();
		this.billetera = 0f;
		this.atributos = new HashSet<>();
		this.atributos.add(new Armadura(1f));
		this.atributos.add(new Danho(1f));
		this.atributos.add(new Destreza(1f));
		this.atributos.add(new Fuerza(1f));
		this.atributos.add(new Vida(1f));
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

	public Atributo getAtributo(Class claseAtributo) {
		Atributo res = null;
		for(Atributo a : this.getAtributos()) {
			if(a.getClass() == claseAtributo) {
				res = a;
			}
		}
		return res;
	}
	//

	public void ganarExperiencia(Integer exp) {
		this.setExp(this.getExp() + exp);
		this.subirDeNivelSiPuede();
	}

	public Boolean expSuficiente() {
		Integer mod = null;
		if(this.getNivel() >=1 && this.getNivel() <= 10) {
			mod = 100;
		} else if(this.getNivel() >=11 && this.getNivel() <= 20) {
			mod = 200;
		} else if(this.getNivel() >=21 && this.getNivel() <= 50) {
			mod = 400;
		} else if(this.getNivel() >= 51) {
			mod = 800;
		}
		return mod * this.getNivel() <= this.getExp();
	}

	public void subirDeNivelSiPuede() {
		if(this.expSuficiente()) {
			this.nivel++;
			switch(this.getClase().ordinal()) {
				case 0:
					this.getAtributo(Destreza.class).setValor(this.getAtributo(Destreza.class).getValor() * 1.02f);
					break;
				case 1:
					this.getAtributo(Vida.class).setValor(this.getAtributo(Vida.class).getValor() * 1.06f);
					break;
				case 2:
					this.getAtributo(Fuerza.class).setValor(this.getAtributo(Fuerza.class).getValor() * 1.1f);
					this.getAtributo(Destreza.class).setValor(this.getAtributo(Destreza.class).getValor() * 1.03f);
					this.getAtributo(Vida.class).setValor(this.getAtributo(Vida.class).getValor() * 1.08f);
					break;
				case 3:
					this.getAtributo(Vida.class).setValor(this.getAtributo(Vida.class).getValor() * 1.02f);
					break;
				case 4:
					this.getAtributo(Fuerza.class).setValor(this.getAtributo(Fuerza.class).getValor() * 1.04f);
					this.getAtributo(Destreza.class).setValor(this.getAtributo(Destreza.class).getValor() * 1.01f);
					break;
				case 5:
					this.getAtributo(Fuerza.class).setValor(this.getAtributo(Fuerza.class).getValor() * 1.02f);
					this.getAtributo(Destreza.class).setValor(this.getAtributo(Destreza.class).getValor() * 1.09f);
					break;
				case 6:
					this.getAtributo(Fuerza.class).setValor(this.getAtributo(Fuerza.class).getValor() * 1.02f);
					this.getAtributo(Destreza.class).setValor(this.getAtributo(Destreza.class).getValor() * 1.09f);
					this.getAtributo(Vida.class).setValor(this.getAtributo(Vida.class).getValor() * 1.05f);
					break;
				case 7:
					this.getAtributo(Vida.class).setValor(this.getAtributo(Vida.class).getValor() * 1.05f);
					break;
				case 8:
					this.getAtributo(Vida.class).setValor(this.getAtributo(Vida.class).getValor() * 1.02f);
					this.getAtributo(Fuerza.class).setValor(this.getAtributo(Fuerza.class).getValor() * 1.04f);
					break;
				case 9:
					this.getAtributo(Fuerza.class).setValor(this.getAtributo(Fuerza.class).getValor() * 1.06f);
					break;
			}
		}

	}

}