package ar.edu.unq.epers.woe.backend.service;

import ar.edu.unq.epers.woe.backend.model.lugar.Tienda;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.service.lugar.LugarService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LugarServiceTest {

    private Personaje pj;
    private LugarService ls;


    @Before
    public void crearModelo() {
        this.pj = new Personaje(new Raza("tstRaza"), "tstPJ0", Clase.MAGO);
        this.ls = new LugarService();
    }

    @Test
    public void alMoverAlPJAUnLugarYPreguntarleSuLugarRetornaElNuevoLugar() {
        this.ls.mover(this.pj, new Tienda("tstTienda"));
        assertEquals(this.pj.getLugar().getClass(), Tienda.class);
    }
}
