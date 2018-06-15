package ar.edu.unq.epers.woe.backend.model.personaje;

public class RetribucionAdicionalPersonaje {

	// Se encarga de aumentar atributos segun clase Personaje

	protected static void incrementarSegunClase(Personaje p) {

		switch (p.getClase().ordinal()) {
		case 0:
			incrementarBrujo(p);
			break;
		case 1:
			incrementarDruida(p);
			break;
		case 2:
			incrementarCaballero(p);
			break;
		case 3:
			incrementarSacerdote(p);
			break;
		case 4:
			incrementarGuerrero(p);
			break;
		case 5:
			incrementarMonje(p);
			break;
		case 6:
			incrementarMago(p);
			break;
		case 7:
			incrementarCazador(p);
			break;
		case 8:
			incrementarPaladin(p);
			break;
		case 9:
			incrementarPicaro(p);
			break;
		}

	}

	// incrementos segun clase
	
	private static void incrementarPicaro(Personaje p) {
		p.getAtributo(Fuerza.class).setValor(p.getAtributo(Fuerza.class).getValor() * 1.06f);
	}

	private static void incrementarPaladin(Personaje p) {
		p.getAtributo(Vida.class).setValor(p.getAtributo(Vida.class).getValor() * 1.02f);
		p.getAtributo(Fuerza.class).setValor(p.getAtributo(Fuerza.class).getValor() * 1.04f);
	}

	private static void incrementarCazador(Personaje p) {
		p.getAtributo(Vida.class).setValor(p.getAtributo(Vida.class).getValor() * 1.05f);
	}

	private static void incrementarMago(Personaje p) {
		p.getAtributo(Fuerza.class).setValor(p.getAtributo(Fuerza.class).getValor() * 1.02f);
		p.getAtributo(Destreza.class).setValor(p.getAtributo(Destreza.class).getValor() * 1.09f);
		p.getAtributo(Vida.class).setValor(p.getAtributo(Vida.class).getValor() * 1.05f);
	}

	private static void incrementarMonje(Personaje p) {
		p.getAtributo(Fuerza.class).setValor(p.getAtributo(Fuerza.class).getValor() * 1.02f);
		p.getAtributo(Destreza.class).setValor(p.getAtributo(Destreza.class).getValor() * 1.09f);
	}

	private static void incrementarGuerrero(Personaje p) {
		p.getAtributo(Fuerza.class).setValor(p.getAtributo(Fuerza.class).getValor() * 1.04f);
		p.getAtributo(Destreza.class).setValor(p.getAtributo(Destreza.class).getValor() * 1.01f);
	}

	private static void incrementarSacerdote(Personaje p) {
		p.getAtributo(Vida.class).setValor(p.getAtributo(Vida.class).getValor() * 1.02f);
	}

	private static void incrementarCaballero(Personaje p) {
		p.getAtributo(Fuerza.class).setValor(p.getAtributo(Fuerza.class).getValor() * 1.1f);
		p.getAtributo(Destreza.class).setValor(p.getAtributo(Destreza.class).getValor() * 1.03f);
		p.getAtributo(Vida.class).setValor(p.getAtributo(Vida.class).getValor() * 1.08f);
	}

	private static void incrementarDruida(Personaje p) {
		p.getAtributo(Vida.class).setValor(p.getAtributo(Vida.class).getValor() * 1.06f);
	}

	private static void incrementarBrujo(Personaje p) {
		p.getAtributo(Destreza.class).setValor(p.getAtributo(Destreza.class).getValor() * 1.02f);
	}

}
