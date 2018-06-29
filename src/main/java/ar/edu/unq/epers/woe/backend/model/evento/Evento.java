package ar.edu.unq.epers.woe.backend.model.evento;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import java.util.Date;

@JsonTypeInfo(use= Id.CLASS,property="_class")
public class Evento {

    @MongoId
    @MongoObjectId
    private String idEvento;
    private String nombrePJ;
    private String nombreLugar;
    private Date fecha;
    private String claseDeEvento;

    public Evento() {}

    public Evento(String nombrePJ, String nombreLugar) {
        this.nombrePJ = nombrePJ;
        this.nombreLugar = nombreLugar;
        this.fecha = new Date();
        this.setClaseDeEvento(this.getClass().getName());
    }

    // Getters y setters
    public String getNombrePJ() {
        return nombrePJ;
    }

    public void setNombrePJ(String nombrePJ) {
        this.nombrePJ = nombrePJ;
    }

    public String getNombreLugar() {
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }
    //

	public String getClaseDeEvento() {
		return claseDeEvento;
	}

	public void setClaseDeEvento(String claseDeEvento) {
		this.claseDeEvento = claseDeEvento;
	}

}
