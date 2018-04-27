package ar.edu.unq.epers.woe.backend.model.personaje;

public class Slot {

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    private String ubicacion;
    //private Item item;

    public Slot(String ubicacion) {
        this.setUbicacion(ubicacion);
    }


}
