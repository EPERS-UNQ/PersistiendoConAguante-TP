package ar.edu.unq.epers.woe.backend.model.personaje;

import ar.edu.unq.epers.woe.backend.model.raza.Clase;

public class RetribucionAdicionalPersonaje {

	// Se encarga de aumentar atributos segun clase Personaje

	private Personaje p;

	public RetribucionAdicionalPersonaje(Personaje p) {
		this.p = p;
		incrementarSegunClase(this.p.getClase());
	}

	private void incrementarSegunClase(Clase clase) {

		switch (this.p.getClase().ordinal()) {
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

	// incrementos segun clase
	
	private void incrementarPicaro() {
		p.getAtributo(Fuerza.class).setValor(p.getAtributo(Fuerza.class).getValor() * 1.06f);
	}

	private void incrementarPaladin() {
		p.getAtributo(Vida.class).setValor(p.getAtributo(Vida.class).getValor() * 1.02f);
		p.getAtributo(Fuerza.class).setValor(p.getAtributo(Fuerza.class).getValor() * 1.04f);
	}

	private void incrementarCazador() {
		p.getAtributo(Vida.class).setValor(p.getAtributo(Vida.class).getValor() * 1.05f);
	}

	private void incrementarMago() {
		p.getAtributo(Fuerza.class).setValor(p.getAtributo(Fuerza.class).getValor() * 1.02f);
		p.getAtributo(Destreza.class).setValor(p.getAtributo(Destreza.class).getValor() * 1.09f);
		p.getAtributo(Vida.class).setValor(p.getAtributo(Vida.class).getValor() * 1.05f);
	}

	private void incrementarMonje() {
		p.getAtributo(Fuerza.class).setValor(p.getAtributo(Fuerza.class).getValor() * 1.02f);
		p.getAtributo(Destreza.class).setValor(p.getAtributo(Destreza.class).getValor() * 1.09f);
	}

	private void incrementarGuerrero() {
		p.getAtributo(Fuerza.class).setValor(p.getAtributo(Fuerza.class).getValor() * 1.04f);
		p.getAtributo(Destreza.class).setValor(p.getAtributo(Destreza.class).getValor() * 1.01f);
	}

	private void incrementarSacerdote() {
		p.getAtributo(Vida.class).setValor(p.getAtributo(Vida.class).getValor() * 1.02f);
	}

	private void incrementarCaballero() {
		p.getAtributo(Fuerza.class).setValor(p.getAtributo(Fuerza.class).getValor() * 1.1f);
		p.getAtributo(Destreza.class).setValor(p.getAtributo(Destreza.class).getValor() * 1.03f);
		p.getAtributo(Vida.class).setValor(p.getAtributo(Vida.class).getValor() * 1.08f);
	}

	private void incrementarDruida() {
		p.getAtributo(Vida.class).setValor(p.getAtributo(Vida.class).getValor() * 1.06f);
	}

	private void incrementarBrujo() {
		p.getAtributo(Destreza.class).setValor(p.getAtributo(Destreza.class).getValor() * 1.02f);
	}

}
