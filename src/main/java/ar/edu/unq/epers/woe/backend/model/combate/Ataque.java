package ar.edu.unq.epers.woe.backend.model.combate;

import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;

public class Ataque {
	Luchador atacante;
	Luchador atacado;
	Danho dañoAtacante;
	Danho dañoRecibido;
	Vida restoDeVida;
	public Ataque(Luchador atacante, Luchador atacado) {
		this.setAtacante(atacante);
		this.setAtacado(atacado);
		this.setDanhoAtacante(atacante.getDanho());
		
		
	}
	private void setDanhoAtacante(Danho danho) {
		this.dañoAtacante = danho;
	}
	private void setAtacado(Luchador atacado2) {
		this.atacado= atacado2;
	}
	public void setAtacante(Luchador atacante2) {
		this.atacante = atacante2;
		
	}
}