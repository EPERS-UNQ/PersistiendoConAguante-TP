package ar.edu.unq.epers.woe.backend.service.lugar;

public class UbicacionMuyLejana extends RuntimeException {

    private String partida;
    private String llegada;

    public UbicacionMuyLejana(String partida, String llegada) {
        this.partida = partida;
        this.llegada = llegada;
    }

    @Override
    public String getMessage() {
        return "El lugar [" + this.partida + "] y el lugar [" + this.llegada + "] no están conectados.";
    }

}
