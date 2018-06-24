package ar.edu.unq.epers.woe.backend.model.evento;

public class Ganador extends Evento {

    private String nombreContrincante;
    private String claseContrincante;
    private String claseGanador;
    private String razaContrincante;
    private String razaGanador;

    public Ganador() {}

    public Ganador(String nombrePJ, String nombreLugar, String nombreContrincante, String claseContrincante,
                   String claseGanador, String razaContrincante, String razaGanador) {
        super(nombrePJ, nombreLugar);
        this.nombreContrincante = nombreContrincante;
        this.claseContrincante = claseContrincante;
        this.claseGanador = claseGanador;
        this.razaContrincante = razaContrincante;
        this.razaGanador = razaGanador;
    }

    // Getters y setters
    public String getNombreContrincante() {
        return nombreContrincante;
    }

    public void setNombreContrincante(String nombreContrincante) {
        this.nombreContrincante = nombreContrincante;
    }

    public String getClaseContrincante() {
        return claseContrincante;
    }

    public void setClaseContrincante(String claseContrincante) {
        this.claseContrincante = claseContrincante;
    }

    public String getClaseGanador() {
        return claseGanador;
    }

    public void setClaseGanador(String claseGanador) {
        this.claseGanador = claseGanador;
    }

    public String getRazaContrincante() {
        return razaContrincante;
    }

    public void setRazaContrincante(String razaContrincante) {
        this.razaContrincante = razaContrincante;
    }

    public String getRazaGanador() {
        return razaGanador;
    }

    public void setRazaGanador(String razaGanador) {
        this.razaGanador = razaGanador;
    }
    //

}
