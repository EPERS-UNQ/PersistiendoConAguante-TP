package ar.edu.unq.epers.woe.backend.model.raza;

import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.service.raza.ClaseInvalida;

import java.util.Set;

/**
 * Representa una {@link Raza} de un personaje.
 * 
 * @author Charly Backend
 */
public class Raza {

	private Integer id;
	private String nombre;
	private Set<Clase> clases;
	private int peso;
	private int altura;
	private int energiaInicial;
	private String urlFoto;
	private int cantidadPersonajes;

	public Raza(){
	}
	
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

	public void setEnergiaInicial(int energiaInicial) {
		this.energiaInicial = energiaInicial;
	}
}