package ar.edu.unq.epers.woe.backend.model.combate;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class ResultadoCombate {

	@Id @GeneratedValue
	int id;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Luchador ganador;

	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Luchador perdedor;

	@OneToMany(mappedBy="resultadoCombate", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Ataque> detalle;
	
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
	
	public List<Ataque> getDetalle(){
		return this.detalle;
	}

	public void agregarAtaque(Ataque ataque) { this.detalle.add( ataque); ataque.setResultadoCombate(this); }
}
