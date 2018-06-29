package ar.edu.unq.epers.woe.backend.model.evento;

public class Arribo extends Evento {

    private String nombreLugarDestino;
    private String claseLugarDestino;
    private String claseLugarOrigen;

    public Arribo() {}

    public Arribo(String nombrePJ, String nombreLugarOrigen, String claseLugarOrigen,
                  String nombreLugarDestino, String claseLugarDestino) {
        super(nombrePJ, nombreLugarOrigen);
        this.nombreLugarDestino = nombreLugarDestino;
        this.claseLugarDestino = claseLugarDestino;
        this.claseLugarOrigen = claseLugarOrigen;
    }

    // Getters y setters
    public String getNombreLugarDestino() {
        return nombreLugarDestino;
    }

    public void setNombreLugarDestino(String nombreLugarDestino) {
        this.nombreLugarDestino = nombreLugarDestino;
    }

    public String getClaseLugarDestino() {
        return claseLugarDestino;
    }

    public void setClaseLugarDestino(String claseLugarDestino) {
        this.claseLugarDestino = claseLugarDestino;
    }

    public String getClaseLugarOrigen() {
        return claseLugarOrigen;
    }

    public void setClaseLugarOrigen(String claseLugarOrigen) {
        this.claseLugarOrigen = claseLugarOrigen;
    }
    //

}
