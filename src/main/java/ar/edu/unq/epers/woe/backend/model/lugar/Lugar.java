package ar.edu.unq.epers.woe.backend.model.lugar;

public class Lugar {

	private String nombre;

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
