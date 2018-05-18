package ar.edu.unq.epers.woe.backend.model.personaje;

import ar.edu.unq.epers.woe.backend.model.combate.Luchador;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;
import ar.edu.unq.epers.woe.backend.model.mision.Mision;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


/**
 * Un {@link Personaje} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Raza} en particular.
 *
 * @author Charly Backend
 */
@Entity
public class Personaje {

	@Id
	private String nombre; //único
	@ManyToOne
	private Raza raza;
	@Enumerated(EnumType.STRING)
	private Clase clase;
	private Integer nivel;
	private Integer exp;
	private Float billetera;
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Inventario inventario;
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Mochila mochila;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Atributo> atributos;
	
	@ElementCollection private Set<String> misionesAceptadas;
	@ElementCollection private Set<String> misionesCumplidas;
	@ManyToOne
	private Lugar lugar;


	public Personaje() {}
	
	public Personaje(Raza raza, String nombre, Clase clase) {
		this.raza = raza;
		this.nombre = nombre;
		this.clase = clase;
		this.nivel = 1;
		this.exp = 0;
		this.mochila = new Mochila();
		this.inventario = new Inventario();
		this.billetera = 0f;
		this.atributos = new HashSet<>();
		this.misionesAceptadas = new HashSet<>();
		this.misionesCumplidas = new HashSet<>();
		this.atributos.add(new Armadura(1f));
		this.atributos.add(new Danho(1f));
		this.atributos.add(new Destreza(1f));
		this.atributos.add(new Fuerza(1f));
		this.atributos.add(new Vida(1f));
	}


	// Getters y Setters
	public Set<String> getMisionesAceptadas() {
		return misionesAceptadas;
	}

	public void setMisionesAceptadas(Set<String> misionesAceptadas) {
		this.misionesAceptadas = misionesAceptadas;
	}

	public Set<String> getMisionesCumplidas() {
		return misionesCumplidas;
	}

	public void setMisionesCumplidas(Set<String> misionesCumplidas) {
		this.misionesCumplidas = misionesCumplidas;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public Lugar getLugar() {
		return lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	public Mochila getMochila() {
		return mochila;
	}

	public void setMochila(Mochila mochila) {
		this.mochila = mochila;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Raza getRaza() {
		return this.raza;
	}

	public Clase getClase() {
		return clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}

	public Integer getNivel() {
		return nivel;
	}

	public Integer getExp() {
		return exp;
	}

	public Float getBilletera() {
		return billetera;
	}

	public Set<Atributo> getAtributos() {
		return atributos;
	}
	public Vida getVida() {
		return (Vida) this.getAtributo(Vida.class);
	}
	
	public Danho getDanho() {
		return (Danho) this.getAtributo(Danho.class);
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setRaza(Raza raza) {
		this.raza = raza;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public void setBilletera(Float billetera) {
		this.billetera = billetera;
	}

	public void setAtributos(Set<Atributo> atributos) {
		this.atributos = atributos;
	}

	public Atributo getAtributo(Class claseAtributo) {
		Atributo res = null;
		for(Atributo a : this.getAtributos()) {
			if(a.getClass() == claseAtributo) {
				res = a;
			}
		}
		return res;
	}
	

	public void ganarExperiencia(Integer exp) {
		this.setExp(this.getExp() + exp);
		this.subirDeNivelSiPuede();
	}

	public Boolean expSuficiente() {
		Integer mod = null;
		if(this.getNivel() >=1 && this.getNivel() <= 10) {
			mod = 100;
		} else if(this.getNivel() >=11 && this.getNivel() <= 20) {
			mod = 200;
		} else if(this.getNivel() >=21 && this.getNivel() <= 50) {
			mod = 400;
		} else if(this.getNivel() >= 51) {
			mod = 800;
		}
		return mod * this.getNivel() <= this.getExp();
	}

	public void subirDeNivelSiPuede() {
		if(this.expSuficiente()) {
			this.nivel++;
			switch(this.getClase().ordinal()) {
				case 0:
					incrementarBrujo();
					break;
				case 1:
					incrementarDruida();
					break;
				case 2:
					incrementarCaballero();
					break;
				case 3:
					incrementarSacerdote();
					break;
				case 4:
					incrementarGuerrero();
					break;
				case 5:
					incrementarMonje();
					break;
				case 6:
					incrementarMago();
					break;
				case 7:
					incrementarCazador();
					break;
				case 8:
					incrementarPaladin();
					break;
				case 9:
					incrementarPicaro();
					break;
			}
		}
	}

	public void incrementarAtributos(Set<Atributo> atributos) {
		for(Atributo a : atributos) {
			this.getAtributo(a.getClass()).setValor(this.getAtributo(a.getClass()).getValor() + a.getValor());
		}
	}

	public void decrementarAtributos(Set<Atributo> atributos) {
		for(Atributo a : atributos) {
			this.getAtributo(a.getClass()).setValor(this.getAtributo(a.getClass()).getValor() - a.getValor());
		}
	}

	private void incrementarPicaro() {
		this.getAtributo(Fuerza.class).setValor(this.getAtributo(Fuerza.class).getValor() * 1.06f);
	}

	private void incrementarPaladin() {
		this.getAtributo(Vida.class).setValor(this.getAtributo(Vida.class).getValor() * 1.02f);
		this.getAtributo(Fuerza.class).setValor(this.getAtributo(Fuerza.class).getValor() * 1.04f);
	}

	private void incrementarCazador() {
		this.getAtributo(Vida.class).setValor(this.getAtributo(Vida.class).getValor() * 1.05f);
	}

	private void incrementarMago() {
		this.getAtributo(Fuerza.class).setValor(this.getAtributo(Fuerza.class).getValor() * 1.02f);
		this.getAtributo(Destreza.class).setValor(this.getAtributo(Destreza.class).getValor() * 1.09f);
		this.getAtributo(Vida.class).setValor(this.getAtributo(Vida.class).getValor() * 1.05f);
	}

	private void incrementarMonje() {
		this.getAtributo(Fuerza.class).setValor(this.getAtributo(Fuerza.class).getValor() * 1.02f);
		this.getAtributo(Destreza.class).setValor(this.getAtributo(Destreza.class).getValor() * 1.09f);
	}

	private void incrementarGuerrero() {
		this.getAtributo(Fuerza.class).setValor(this.getAtributo(Fuerza.class).getValor() * 1.04f);
		this.getAtributo(Destreza.class).setValor(this.getAtributo(Destreza.class).getValor() * 1.01f);
	}

	private void incrementarSacerdote() {
		this.getAtributo(Vida.class).setValor(this.getAtributo(Vida.class).getValor() * 1.02f);
	}

	private void incrementarCaballero() {
		this.getAtributo(Fuerza.class).setValor(this.getAtributo(Fuerza.class).getValor() * 1.1f);
		this.getAtributo(Destreza.class).setValor(this.getAtributo(Destreza.class).getValor() * 1.03f);
		this.getAtributo(Vida.class).setValor(this.getAtributo(Vida.class).getValor() * 1.08f);
	}

	private void incrementarDruida() {
		this.getAtributo(Vida.class).setValor(this.getAtributo(Vida.class).getValor() * 1.06f);
	}

	private void incrementarBrujo() {
		this.getAtributo(Destreza.class).setValor(this.getAtributo(Destreza.class).getValor() * 1.02f);
	}


	public void comprar(Item i) {
		Tienda t = (Tienda) lugar;
		t.comprar(this, i);
	}
	
	public void gastarBilletera(int costo) {
		setBilletera(billetera-costo);
	}

	
	public void agregarItem(Item i) {
		getInventario().setItemEnUnaUbicacion(i, this);
	}


	public void vender(Item i) {
		Tienda t = (Tienda) lugar;
		t.vender(this, i);
	}


	public void agregarABilletera(int suma) {
		setBilletera(billetera+suma);
	}


	public void sacarItem(Item i) {
		//Por ahora: sacar item de la mochila, no del inventario
		mochila.sacarItem(i);
	}

//	@Override
//	public void atacar(Luchador l2) {
//		l2.recibirAtaque(this.getDanhoTotal());
//
//	}

	public Danho getDanhoArma() {
		return new Danho(this.getDanhoManoDerecha().getValor() + this.getDanhoManoIzquierda().getValor());
	}

    public Danho getDanhoManoIzquierda() {
    	return this.getInventario().getEnUbicacion("Izquierda").getItem().getDanho();
    }
	public Danho getDanhoManoDerecha() {
		return this.getInventario().getEnUbicacion("Derecha").getItem().getDanho();
	}
	public Danho getDanhoTotal() {
		return new Danho(this.getDanhoArma().getValor()  * 
		(this.getAtributo(Fuerza.class).getValor() + 
		  (this.getAtributo(Destreza.class).getValor() / 100)
		    / 100));
	}

//
//	@Override
//	public void recibirAtaque(Danho danhoTotal) {
//		this.calcularDañoRecividoConDefensa(danhoTotal);
//
//	}


	//@Override
	//public void recibirAtaque(Danho danhoAtacante) {
	//	float danhorecibido =danhoAtacante.getValor() - this.calcularDañoRecividoConDefensa(danhoAtacante).getValor();
	//	float cantidadVidaActual = this.getVida().getValor();
	//	Vida vidatotal = new Vida (cantidadVidaActual - danhorecibido);
	//	this.setVida(vidatotal);
		
		
		
	//}

	private Danho calcularDañoRecividoConDefensa(Danho danhoAtacante) {
		return new Danho(danhoAtacante.getValor() - this.defensa().getValor());
		
	}


	public Danho defensa() {
		//hacer
		return new Danho(0f);
		
	}
//	private Danho calcularDañoRecividoConDefensa(Danho danhoTotal) {
//		return danhoTotal - this.defensa();
//
//	}


//	private Danho defensa() {
//
//		//return
//	}


	public Boolean tieneElItem(Item item) {
		return this.mochila.tieneElItem(item);		
	}

//	@Override
//	public void setVida(Vida vl1) {
//		this.getAtributo(Vida.class).setValor(vl1.getValor());;
//	}


  
	public void aceptarMision(Mision mision) {
		getMisionesAceptadas().add(mision.getNombre());
  }

	public Item getItemEnUbicacion(String ubicacion) {
		return inventario.getEnUbicacion(ubicacion).getItem();
	}
  
}