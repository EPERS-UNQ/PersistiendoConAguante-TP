package ar.edu.unq.epers.woe.backend.model.lugar;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISCRIMINATOR", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("LUGAR")
public class Lugar {

	@Id	@GeneratedValue
	private int id;
	private String nombre;

	public Lugar() {};

	public Lugar(String nombreLugar) {
		this.nombre = nombreLugar;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean esTienda() {
		//throw new RuntimeException("No estas en una tienda");
		return false;
	}

	public boolean esTaberna() {
		// devuelve false para todas las subclases de Lugar excepto Taberna
		return false;
	}

}
