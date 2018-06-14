package ar.edu.unq.epers.woe.backend.model.lugar;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISCRIMINATOR", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("LUGAR")
public class Lugar {

	@Id
	private String nombre;

	public Lugar() {};

	public Lugar(String nombreLugar) {
		this.nombre = nombreLugar;
	}

	public String getNombre() {
		return nombre;
	}

}
