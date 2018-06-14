package ar.edu.unq.epers.woe.backend.model.lugar;

import java.util.HashSet;
import java.util.Set;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("GIMNASIO")
public class Gimnasio extends Lugar  {

	public Gimnasio() {}

	public Gimnasio(String nombreLugar) {
		super(nombreLugar);
	}

}
