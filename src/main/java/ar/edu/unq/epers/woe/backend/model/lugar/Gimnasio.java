package ar.edu.unq.epers.woe.backend.model.lugar;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GIMNASIO")
public class Gimnasio extends Lugar  {

	public Gimnasio() {}

	public Gimnasio(String nombreLugar) {
		super(nombreLugar);
	}

}
