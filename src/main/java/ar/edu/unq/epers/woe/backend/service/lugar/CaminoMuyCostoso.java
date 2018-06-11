package ar.edu.unq.epers.woe.backend.service.lugar;

public class CaminoMuyCostoso extends RuntimeException {

    private Integer costoCamino;
    private Float dineroPJ;

    public CaminoMuyCostoso( Integer costoCamino, Float dineroPJ) {
        this.costoCamino = costoCamino;
        this.dineroPJ = dineroPJ;
    }

    @Override
    public String getMessage() {
        return "El viaje cuesta [" + this.costoCamino + "] y el personaje tiene [" + this.dineroPJ + "] monedas.";
    }

}