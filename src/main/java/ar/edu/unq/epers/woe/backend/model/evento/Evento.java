package ar.edu.unq.epers.woe.backend.model.evento;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import java.util.Date;

public class Evento {

    @MongoId
    @MongoObjectId
    private String idEvento;
    private String nombrePJ;
    private String nombreLugar;
    private Date fecha;

    public Evento() {}

    public Evento(String nombrePJ, String nombreLugar) {
        this.nombrePJ = nombrePJ;
        this.nombreLugar = nombreLugar;
        this.fecha = new Date();
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

}
