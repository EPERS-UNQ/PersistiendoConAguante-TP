package ar.edu.unq.epers.woe.backend.model.item;

import java.util.Set;
import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import ar.edu.unq.epers.woe.backend.model.mision.Recompensa;
import ar.edu.unq.epers.woe.backend.model.personaje.Armadura;
import ar.edu.unq.epers.woe.backend.model.personaje.Atributo;
import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Mochila;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;

import javax.persistence.*;


@Entity
public class Item {

	@Id @GeneratedValue
	private int idItem;
	@ManyToOne
	private Mochila mochila;
	@ManyToOne
	private Lugar lugar;
	@ManyToOne
	private Recompensa recompensa;
	private String nombre;
	private String ubicacion;
	private String tipo;
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<Clase> clases;
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Requerimiento requerimiento; 
	private int costoDeCompra;
	private int costoDeVenta;
	@OneToMany(mappedBy="item", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Atributo> atributos;

	public Item() {}
	
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

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	public Mochila getMochila() {
		return mochila;
	}

	public void setMochila(Mochila mochila) {
		this.mochila = mochila;
	}

	public Lugar getLugar() {
		return lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	public Recompensa getRecompensa() {
		return recompensa;
	}

	public void setRecompensa(Recompensa recompensa) {
		this.recompensa = recompensa;
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
		for(Atributo a: this.atributos) {
			a.setItem(this);
		}
	}

	public Danho getDanho() {
		float val = 0f;
		for(Atributo a : this.getAtributos()) {
			if(a.getClass() == Danho.class) {
				val = val + a.getValor();
				break;
			}
		}
		return new Danho(val);
	}

    public Armadura getArmadura() {
		float val = 0f;
		for(Atributo a : this.getAtributos()) {
			if(a.getClass() == Armadura.class) {
				val = val + a.getValor();
				break;
			}
		}
		return new Armadura(val);
    }

	public boolean equivaleA(Item i) {
		return nombre.equals(i.getNombre()) &&
				ubicacion.equals(i.getUbicacion()) &&
				tipo.equals(i.getTipo());
	}
}
