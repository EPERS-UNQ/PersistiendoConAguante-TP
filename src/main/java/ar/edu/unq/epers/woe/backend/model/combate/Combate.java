package ar.edu.unq.epers.woe.backend.model.combate;

import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;

public class Combate {


	public ResultadoCombate combatir(Luchador l1, Luchador l2) {
		if(sonDosPersonajes(l1,l2) || sonUnPersonajeYunMonstruo(l1,l2)) {
			Vida vl1= l1.getVida();
			Vida vl2= l2.getVida();
			ResultadoCombate resultadoC = new ResultadoCombate();

			while(l1.getVida().getValor()> 0 && l2.getVida().getValor()>0) {
				l1.atacar(l2);
				Ataque at1 = new Ataque (l1,l2);
				resultadoC.getDetalle().add(at1);
				if (l2.getVida().getValor()> 0) {
					l2.atacar(l1);
					Ataque at2 = new Ataque (l2,l1);
					resultadoC.getDetalle().add(at2);
				}
			}

			if(l1.getVida().getValor()> 0) {
				resultadoC.setGanador(l1);
				resultadoC.setPerdedor(l2);
			} else if(l2.getVida().getValor()> 0) {
				resultadoC.setGanador(l2);
				resultadoC.setPerdedor(l1);
			}
			if(resultadoC.getGanador().sosPersonaje()) {
				Personaje ganador = (Personaje) resultadoC.getGanador();
				ganador.incrementarVictoriasActualesSiPuede(resultadoC);
				ganador.cumplirMisionesSiPuede();
			}

			l1.setVida(vl1);
			l2.setVida(vl2);
			return resultadoC;
		}
		else {throw new RuntimeException("El combate no es posible con dos monstruos"); } // tirar exepcion
	}

	private boolean sonUnPersonajeYunMonstruo(Luchador l1, Luchador l2) {
		return (l1.sosPersonaje() &&  l2.sosMonstruo()) || (l2.sosPersonaje() &&  l1.sosMonstruo());
	}

	private boolean sonDosPersonajes(Luchador l1, Luchador l2) {
		return l1.sosPersonaje() && l2.sosPersonaje();
	}




	


}
