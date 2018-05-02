package ar.edu.unq.epers.woe.backend.model.requerimiento;

import ar.edu.unq.epers.woe.backend.model.personaje.Atributo;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.personaje.Vida;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;


import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RequerimientoTest {

    private Personaje pj;
    private Requerimiento req;

    @Before
    public void crearModelo() {
        pj = new Personaje(new Raza("tstRaza"), "tstPJ0", Clase.MAGO);
    }

    @Test
    public void pjDeNivel2CumpleRequerimientoDeNivel2() {
        this.pj.ganarExperiencia(120);
        req = new Requerimiento(2, new HashSet<>());
        assertTrue(req.cumpleRequerimiento(this.pj));
    }

    @Test
    public void pjDeVida5CumpleRequerimientoDeVida4() {
        this.pj.getAtributo(Vida.class).setValor(5f);
        Set<Atributo> atribs = new HashSet<>();
        atribs.add(new Vida(4f));
        req = new Requerimiento(0, atribs);
        assertTrue(req.cumpleRequerimiento(this.pj));
    }

    @After
    public void tearDown() {

    }
}