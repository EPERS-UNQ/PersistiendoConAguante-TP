package ar.edu.unq.epers.woe.backend.model.combate;

import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;

public interface Luchador {

	public Vida getVida();

	public Danho getDanho();

	public void atacar(Luchador l2);

	public void recibirAtaque(Danho danhoTotal);
}
