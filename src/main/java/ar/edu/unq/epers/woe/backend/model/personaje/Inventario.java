package ar.edu.unq.epers.woe.backend.model.personaje;

public class Inventario {

    private Slot cabeza;
    private Slot torso;
    private Slot piernas;
    private Slot pies;
    private Slot izquierda;
    private Slot derecha;

    public Inventario() {
        this.cabeza = new Slot("cabeza");
        this.torso = new Slot("torso");
        this.piernas = new Slot("piernas");
        this.pies = new Slot("pies");
        this.izquierda = new Slot("izquierda");
        this.derecha = new Slot("derecha");
    }

    public void setCabeza(Slot cabeza) {
        this.cabeza = cabeza;
    }

    public void setTorso(Slot torso) {
        this.torso = torso;
    }

    public void setPiernas(Slot piernas) {
        this.piernas = piernas;
    }

    public void setPies(Slot pies) {
        this.pies = pies;
    }

    public void setIzquierda(Slot izquierda) {
        this.izquierda = izquierda;
    }

    public void setDerecha(Slot derecha) {
        this.derecha = derecha;
    }

    public Slot getCabeza() {
        return cabeza;
    }

    public Slot getTorso() {
        return torso;
    }

    public Slot getPiernas() {
        return piernas;
    }

    public Slot getPies() {
        return pies;
    }

    public Slot getIzquierda() {
        return izquierda;
    }

    public Slot getDerecha() {
        return derecha;
    }

}
