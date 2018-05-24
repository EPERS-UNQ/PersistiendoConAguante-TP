package ar.edu.unq.epers.woe.backend.model.raza;

import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;

import ar.edu.unq.epers.woe.backend.service.raza.ClaseInvalida;
import ar.edu.unq.epers.woe.backend.service.raza.RazaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Formula;

/**
 * Representa una {@link Raza} de un personaje.
 * 
 * @author Charly Backend
 */
@Entity
public class Raza {

	@Id @GeneratedValue
	private Integer id;
	private String nombre;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<Clase> clases;
	private int peso;
	private int altura;
	private int energiaInicial;
	private String urlFoto;
	private int cantidadPersonajes;

	@OneToMany(mappedBy="raza", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Personaje> personajes;

	public Raza() {}

	public Raza(String nombre) {
		this.nombre = nombre;
	}

	public Personaje crearPersonaje(String nombrePersonaje, Clase clase){
		this.validarClase(clase);
		cantidadPersonajes++;
		return new Personaje(this, nombrePersonaje, clase);
	}

	protected void validarClase(Clase clase){
		if(!this.getClases().contains(clase)){
			throw new ClaseInvalida(this, clase);
		}
	}
	
	/**
	 * @return el nombre de la raza (por ejemplo: Perromon)
	 */
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return la altura de todos los bichos de esta raza
	 */
	public int getAltura() {
		return this.altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	/**
	 * @return el peso de todos los bichos de esta raza
	 */
	public int getPeso() {
		return this.peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	/**
	 * @return una url que apunta al un recurso imagen el cual será
	 * utilizado para mostrar un thumbnail del woe por el frontend.
	 */
	public String getUrlFoto() {
		return this.urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	/**
	 * @return la cantidad de energia de poder iniciales para los bichos
	 * de esta raza.
	 */
	public int getEnergiaInicial() {
		return this.energiaInicial;
	}
	public void setEnergiaIncial(int energiaInicial) {
		this.energiaInicial = energiaInicial;
	}

	
	/**
	 * @return la cantidad de bichos que se han creado para esta
	 * raza.
	 */
	public int getCantidadPersonajes() {
		return this.cantidadPersonajes;
	}
	public void setCantidadPersonajes(int i) {
		this.cantidadPersonajes = i;
	}

	public Set<Clase> getClases() {
		return clases;
	}

	public void setClases(Set<Clase> clases) {
		this.clases = clases;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Personaje> getPersonajes() {
		return personajes;
	}

	public void setPersonajes(Set<Personaje> personajes) {
		this.personajes = personajes;
	}
}
