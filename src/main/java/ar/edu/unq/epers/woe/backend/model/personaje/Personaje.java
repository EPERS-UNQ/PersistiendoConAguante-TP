package ar.edu.unq.epers.woe.backend.model.personaje;

import ar.edu.unq.epers.woe.backend.model.combate.Luchador;
import ar.edu.unq.epers.woe.backend.model.combate.ResultadoCombate;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;
import ar.edu.unq.epers.woe.backend.model.mision.Mision;
import ar.edu.unq.epers.woe.backend.model.mision.VencerA;
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
public class Personaje extends Luchador {

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

	@OneToMany(mappedBy="pjOwner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Mision> misionesEnCurso;

	@OneToMany(mappedBy="personaje", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Atributo> atributos;
	
	@ElementCollection(fetch = FetchType.EAGER) private Set<String> misionesAceptadas;
	@ElementCollection(fetch = FetchType.EAGER) private Set<String> misionesCumplidas;
	@ManyToOne
	private Lugar lugar;


	public Personaje() {}
	
	public Personaje(Raza raza, String nombre, Clase clase) {
		this.raza = raza;
		this.nombre = nombre;
		this.clase = clase;
		this.nivel = 1;
		this.exp = 0;
		this.mochila = new Mochila(this);
		this.inventario = new Inventario();
		this.billetera = 0f;
		this.atributos = new HashSet<>();
		this.misionesEnCurso = new HashSet<>();
		this.misionesAceptadas = new HashSet<>();
		this.misionesCumplidas = new HashSet<>();
		this.atributos.add(new Armadura(1f, this));
		this.atributos.add(new Danho(1f, this));
		this.atributos.add(new Destreza(1f, this));
		this.atributos.add(new Fuerza(1f, this));
		this.atributos.add(new Vida(1f, this));
	}


	// Getters y Setters
	public Set<Mision> getMisionesEnCurso() {
		return misionesEnCurso;
	}

	public void setMisionesEnCurso(Set<Mision> misionesEnCurso) {
		this.misionesEnCurso = misionesEnCurso;
	}

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

	public void setLugar(Lugar lugar) { this.lugar = lugar; }

	public void cambiarDeLugar(Lugar lugar) {
		this.lugar = lugar;
		this.cumplirMisionesSiPuede();
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
			RetribucionAdicionalPersonaje.incrementarSegunClase(this);
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


	public void comprar(Item i) {
		Tienda t = (Tienda) lugar;
		t.comprar(this, i);
	}
	
	public void gastarBilletera(int costo) {
		setBilletera(billetera-costo);
	}
	
	public void agregarItemAInv(Item i) {
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
		if(this.mochila.tieneElItem(i)) {
			this.mochila.sacarItem(i);
		} else if(this.inventario.tieneElItem(i)) {
			this.inventario.sacarItem(i, this);
		}
	}

	@Override
	public void atacar(Luchador l2) {
		l2.recibirAtaque(this.getDanhoTotal());
	}

	public Danho getDanhoArma() {
		return new Danho(this.getDanhoManoDerecha().getValor() +
				(this.getDanhoManoIzquierda().getValor() * 0.35f), this);
	}

    public Danho getDanhoManoIzquierda() {
		Danho res = new Danho(0f, this);
		if( getItemEnUbicacion("izquierda") != null) {
			res.setValor(getItemEnUbicacion("izquierda").getDanho().getValor());
		}
		return res;
    }
	public Danho getDanhoManoDerecha() {
		Danho res = new Danho(0f, this);
		if(getItemEnUbicacion("derecha") != null) {
			res.setValor(getItemEnUbicacion("derecha").getDanho().getValor());
		}
		return res;
	}

	public Danho getDanhoTotal() {
		Danho res = new Danho(this.getAtributo(Fuerza.class).getValor() +
				               (this.getAtributo(Destreza.class).getValor() / 100) / 100);
		if(this.getDanhoArma().getValor() > 0) {
			res.setValor(res.getValor() * this.getDanhoArma().getValor());
		}
		return res;
	}

	@Override
	public void recibirAtaque(Danho danhoAtacante) {
		Danho danhorecibido = calcularDanhoRecibido(danhoAtacante);
		float cantidadVidaActual = this.getVida().getValor();
		Vida vidatotal = new Vida (cantidadVidaActual - danhorecibido.getValor(), this);
		this.setVida(vidatotal);
	}

	@Override
	public Danho calcularDanhoRecibido(Danho danho) {
		float danhorecibido = this.calcularDanhoRecividoConDefensa(danho).getValor();
		return new Danho(danhorecibido, this);
	}

	public Danho calcularDanhoRecividoConDefensa(Danho danhoAtacante) {
		return new Danho(danhoAtacante.getValor() - (this.defensa().getValor() + danhoAtacante.getValor() * 0.1f),
				         this);
	}

	public Danho defensa() {
		float val = 0f;
		val = getInventario().defensaDeItems();
		return new Danho(val, this);
	}

	public Boolean tieneElItem(Item item) {
		return this.mochila.tieneElItem(item) || this.inventario.tieneElItem(item);
	}

	@Override
	public void setVida(Vida vl1) {
		this.getAtributo(Vida.class).setValor(vl1.getValor());;
	}

	public void aceptarMision(Mision mision) {
		mision.setPjOwner(this);
		this.getMisionesEnCurso().add(mision);
		this.getMisionesAceptadas().add(mision.getNombre());
  }

	@Override
	public boolean sosPersonaje() {
		return true;
	}

	@Override
	public boolean sosMonstruo() {
		return false;
	}

	public Item getItemEnUbicacion(String ubicacion) {
		return inventario.getEnUbicacion(ubicacion).getItem();
	}

	public void setValorDanho(Danho danho) {
		getAtributo(Danho.class).setValor(danho.getValor()); ;
	}

	public void incrementarVictoriasActualesSiPuede(ResultadoCombate resultadoC) {
		for(Mision m : this.getMisionesEnCurso()) {
			if(m.getClass() == VencerA.class) {
				m.incrementarVictoriasActualesSiPuede(resultadoC);
			}
		}
	}

	public void cumplirMisionesSiPuede() {
		for(Mision m : this.getMisionesEnCurso()) {
			m.cumplirMisionSiPuede();
		}
	}

}