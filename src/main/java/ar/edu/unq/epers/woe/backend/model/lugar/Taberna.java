package ar.edu.unq.epers.woe.backend.model.lugar;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TABERNA")
public class Taberna extends Lugar {

	public Taberna(String nombreLugar) {
		super(nombreLugar);
	}
	
	@Override
	public boolean esTaberna() {
		return true;
	}

}
