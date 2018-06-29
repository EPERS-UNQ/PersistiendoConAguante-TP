package ar.edu.unq.epers.woe.backend.model.evento;

public class CompraItem extends Evento {

	private String item;

	public CompraItem(String nombrePersonaje, String nombreLugar, String idItem) {
		super(nombrePersonaje, nombreLugar);
		item = idItem;
	}
	
}
