package ar.edu.unq.epers.woe.backend.model.combate;

import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;

public class Ataque {
	Luchador atacante;
	Luchador atacado;
	Danho danhoAtacante;
	Danho danhoRecibido;
	Vida restoDeVida;
	public Ataque(Luchador atacante, Luchador atacado) {
		this.setAtacante(atacante);
		this.setAtacado(atacado);
		this.setDanhoAtacante(atacante.getDanhoTotal());
		this.setDanhoRecibido(atacado.calcularDanhoRecibido(atacante.getDanhoTotal()));
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
}