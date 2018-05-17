package ar.edu.unq.epers.woe.backend.model.combate;

import ar.edu.unq.epers.woe.backend.model.personaje.Vida;

public class Combate {


	public ResultadoCombate combatir(Luchador l1, Luchador l2) {
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
		
		l1.setVida(vl1);
		l2.setVida(vl2);
		return resultadoC;
	}

//	public ResultadoCombate combatir(Luchador l1, Luchador l2) {
//		Vida vl1= l1.getVida();
//		Vida vl2= l2.getVida();
//		ResultadoCombate resultadoC = new ResultadoCombate();
//
//		while(l1.getVida().getValor()> 0 && l2.getVida().getValor()>0) {
//			l1.atacar(l2);
//			Ataque at1 = new Ataque (l1,l2);
//			resultadoC.getDetalle().add(at1);
//			if (l2.getVida().getValor()> 0) {
//				l2.atacar(l1);
//				Ataque at2 = new Ataque (l2,l1);
//				resultadoC.getDetalle().add(at2);
//			}
//			l1.setVida(vl1);
//			l2.setVida(vl2);
//			return resultadoC;
//		}
//
//	}


	


}
