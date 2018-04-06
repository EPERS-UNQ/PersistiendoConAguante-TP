package ar.edu.unq.epers.woe.backend.model.raza;

import jersey.repackaged.com.google.common.collect.Sets;

public class RazaBuilder {

    private Raza raza;

    public RazaBuilder(String nombre){
        this.raza = new Raza(nombre);
    }

    public RazaBuilder conClases(Clase... clases){
        raza.setClases(Sets.newHashSet(clases));
        return this;
    }

    public RazaBuilder conAltura(int altura){
        raza.setAltura(altura);
        return this;
    }

    public RazaBuilder conPeso(int peso){
        raza.setPeso(peso);
        return this;
    }

    public RazaBuilder conEnergiaInicial(int energiaInicial){
        raza.setEnergiaIncial(energiaInicial);
        return this;
    }

    public RazaBuilder conUrlFoto(String urlFoto){
        raza.setUrlFoto(urlFoto);
        return this;
    }

    public Raza build(){
        return raza;
    }

    public Raza getRaza() {
        return raza;
    }

    public void setRaza(Raza raza) {
        this.raza = raza;
    }
}
