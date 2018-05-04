package ar.edu.unq.epers.woe.backend.model.lugar;

public class Taberna extends Lugar {

	public Taberna(String nombreLugar) {
		super(nombreLugar);
	}
	
	@Override
	public boolean esTaberna() {
		return true;
	}

}
