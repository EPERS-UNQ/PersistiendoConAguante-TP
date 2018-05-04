package ar.edu.unq.epers.woe.backend.model.combate;

import java.util.ArrayList;

public class ResultadoCombate {

	private Luchador ganador;
	private Luchador perdedor;
	private ArrayList<Ataque> detalle;
	
	public ResultadoCombate() {
		ganador = null;
		perdedor = null;
		detalle = new ArrayList<Ataque>();
	}
	
	public void setGanador(Luchador g){
		this.ganador= g;
	}
	
	public Luchador getGanador() {
		return this.ganador;
	}
	
	public void setPerdedor(Luchador p) {
		this.perdedor= p;
	}
	
	public Luchador getPerdedor() {
		return this.perdedor;
	}
	
	public void setDetalle(ArrayList<Ataque> ataques) {
		this.detalle= ataques;
	}
	
	public ArrayList<Ataque> getDetalle(){
		return this.detalle;
	}
}