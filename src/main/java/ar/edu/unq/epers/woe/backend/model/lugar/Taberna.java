package ar.edu.unq.epers.woe.backend.model.lugar;

import ar.edu.unq.epers.woe.backend.model.mision.Mision;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("TABERNA")
public class Taberna extends Lugar {

	@OneToMany(mappedBy="taberna", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Mision> misiones;

	public Taberna() {}

	public Taberna(String nombreLugar) {
		super(nombreLugar);
		this.misiones = new HashSet<Mision>();
	}

	public Taberna(String nombreLugar, Set<Mision> misiones) {
		super(nombreLugar);
		this.misiones = misiones;
		for(Mision m : this.misiones) {
			m.setTaberna(this);
		}
	}

	public Set<Mision> getMisiones() {
		return misiones;
	}

	public void setMisiones(Set<Mision> misiones) {
		this.misiones = misiones;
		for(Mision m : this.misiones) {
			m.setTaberna(this);
		}
	}

	public void agregar(Mision m) {
		m.setTaberna(this);
		this.misiones.add(m);
	}

}
