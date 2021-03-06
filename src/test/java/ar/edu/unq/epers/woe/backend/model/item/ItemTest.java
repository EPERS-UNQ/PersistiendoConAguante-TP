package ar.edu.unq.epers.woe.backend.model.item;

import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import ar.edu.unq.epers.woe.backend.model.personaje.Atributo;
import ar.edu.unq.epers.woe.backend.model.personaje.Danho;
import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.model.requerimiento.Requerimiento;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemTest {
	private Item item;
	private Personaje pj;
	private Set<Clase> clasesPermitidas;
    private Requerimiento req;
    private Set<Atributo> atbs;


    @Before
    public void crearModelo() {
        pj = new Personaje(new Raza("humano"), "Dumbledore", Clase.MAGO);
        clasesPermitidas= new HashSet<>();
        clasesPermitidas.add(Clase.MAGO);
        atbs = new HashSet<>();
        atbs.add(new Danho(15f));
        req = new Requerimiento(1, atbs);
        item = new Item("Varita de sauco", "derecha", "Varita",clasesPermitidas,
        		         req, 200, 100,atbs);
    }
    
    @Test
    public void personajeQCumpleReqSePreguntaSiLoCumpleRespondeTrue() {
        this.pj.getAtributo(Danho.class).setValor(20f);
        assertTrue(this.item.getRequerimiento().cumpleRequerimiento(this.pj));
    }

    @Test
    public void alPreguntarAlItemUbicacionSeObtieneLaEquivalenteDelInventarioDelPj() {
        assertEquals(this.item.getUbicacion(), this.pj.getInventario().getEnUbicacion(this.item.getUbicacion()).getUbicacion());
    }

}
