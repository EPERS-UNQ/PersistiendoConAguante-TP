package ar.edu.unq.epers.woe.backend.model.evento;

public class CompraItem extends Evento {

	private String item;
	private int precio;

	public CompraItem() {}

	public CompraItem(String nombrePersonaje, String nombreLugar, String idItem, int precio) {
		super(nombrePersonaje, nombreLugar);
		this.item = idItem;
		this.precio = precio;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

}
