package ar.edu.unq.epers.woe.backend.model.combate;

import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;
import javax.persistence.*;

@Entity
public class Ataque {

	@Id @GeneratedValue
	private Integer idAtaque;

	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private ResultadoCombate resultadoCombate;

	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	Luchador atacante;

	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	Luchador atacado;

	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	Danho danhoAtacante;

	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	Danho danhoRecibido;

	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	Vida restoDeVida;

	public Ataque(Luchador atacante, Luchador atacado) {
		this.setAtacante(atacante);
		this.setAtacado(atacado);
		this.setDanhoAtacante(atacante.getDanhoTotal());
		this.setDanhoRecibido(atacado.calcularDanhoRecibido(atacante.getDanhoTotal()));
		this.setRestoDeVida(atacado.getVida());
	}

	private void setDanhoRecibido(Danho danhoRecibido) {
		this.danhoRecibido = danhoRecibido;
	}

	public Luchador getAtacante(){
		return this.atacante;
	}
	
	public Luchador getAtacado() {
		return this.atacado;
	}
	
	public Danho getDanhoAtante() {
		return this.danhoAtacante;
	}
	
	public Danho getDanhoRecibido() {
		return this.danhoRecibido;
	}
	
	public void setDanhoAtacante(Danho danho) {
		this.danhoAtacante = danho;
	}

	public void setAtacado(Luchador atacado2) {
		this.atacado= atacado2;
	}

	public void setAtacante(Luchador atacante2) {
		this.atacante = atacante2;
	}

	public Integer getIdAtaque() {
		return idAtaque;
	}

	public void setIdAtaque(Integer idAtaque) {
		this.idAtaque = idAtaque;
	}

	public ResultadoCombate getResultadoCombate() {
		return resultadoCombate;
	}

	public void setResultadoCombate(ResultadoCombate resultadoCombate) {
		this.resultadoCombate = resultadoCombate;
	}

	public Danho getDanhoAtacante() {
		return danhoAtacante;
	}

	public Vida getRestoDeVida() {
		return restoDeVida;
	}

	public void setRestoDeVida(Vida restoDeVida) {
		this.restoDeVida = restoDeVida;
	}

}