package ar.edu.unq.epers.woe.backend.model.item;

import java.util.Set;

import ar.edu.unq.epers.woe.backend.model.personaje.Atributo;
import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;



public class Item {
	
	private String nombre;
	private String ubicacion;
	private String tipo;
	private Set<Clase> clases;
	private Requerimiento requerimiento; 
	private int costoDeCompra;
	private int costoDeVenta;
	private Set<Atributo> atributos;
	
	public Item(String nombre,String ubicacion,String tipo,
			    Set<Clase> clases,Requerimiento requerimiento,
			    int costoDeCompra,int costoDeVenta,
			    Set<Atributo> atributos) {
		this.setNombre(nombre);
		this.setUbicacion(ubicacion);
		this.setTipo(tipo);
		this.setClases(clases);
		this.setRequerimiento(requerimiento);
		this.setCostoDeCompra(costoDeCompra);
		this.setCostoDeVenta(costoDeVenta);	
		this.setAtributos(atributos);
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

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Set<Atributo> getAtributos() {
		return atributos;
	}

	public void setAtributos(Set<Atributo> atributos) {
		this.atributos = atributos;
	}

	public Danho getDanho() {
		return new Danho(0f);
	}
	

}
