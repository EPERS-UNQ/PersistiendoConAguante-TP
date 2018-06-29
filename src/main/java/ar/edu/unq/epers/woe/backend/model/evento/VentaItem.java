package ar.edu.unq.epers.woe.backend.model.evento;

public class VentaItem extends Evento {

	private String item;

	public VentaItem(String nombrePersonaje, String nombreLugar, String idItem) {
		super(nombrePersonaje, nombreLugar);
		item = idItem;
	}

}
