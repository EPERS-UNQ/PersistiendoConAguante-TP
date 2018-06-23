package ar.edu.unq.epers.woe.backend.model.evento;

import ar.edu.unq.epers.woe.backend.model.lugar.Lugar;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import java.time.LocalDateTime;

public class Evento {

    private Personaje pj;
    private Lugar lugar;
    private LocalDateTime fecha = LocalDateTime.now();

    // Getters y setters
    public Personaje getPj() {
        return pj;
    }

    public void setPj(Personaje pj) {
        this.pj = pj;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

}
