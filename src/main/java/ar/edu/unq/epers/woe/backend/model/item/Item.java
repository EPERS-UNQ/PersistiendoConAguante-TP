package ar.edu.unq.epers.woe.backend.model.item;

import java.util.Set;

import ar.edu.unq.epers.woe.backend.model.raza.Clase;



public class Item {
	
	private String nombre;
	private String ubicacion;
	private String tipo;
	private Set<Clase> clases;
	//private Set<Requerimiento> requerimientos;
	private int costoDeCompra;
	private int costoDeVenta;
	//private Set<Atributo> atributos; 
	
	public Item(String nombre,String ubicacion,String tipo,
			    Set<Clase> clases, int costoDeCompra, int costoDeVenta) {
		this.setNombre(nombre);
		this.setUbicacion(ubicacion);
		this.setTipo(tipo);
		this.setClases(clases);
		this.setCostoDeCompra(costoDeCompra);
		this.setCostoDeVenta(costoDeVenta);	
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getCostoDeCompra() {
		return costoDeCompra;
	}

	public void setCostoDeCompra(int costoDeCompra) {
		this.costoDeCompra = costoDeCompra;
	}

	public int getCostoDeVenta() {
		return costoDeVenta;
	}

	public void setCostoDeVenta(int costoDeVenta) {
		this.costoDeVenta = costoDeVenta;
	}

	public Set<Clase> getClases() {
		return clases;
	}

	public void setClases(Set<Clase> clases) {
		this.clases = clases;
	}
	

}
