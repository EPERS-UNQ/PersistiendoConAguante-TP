package ar.edu.unq.epers.woe.backend.redisDAO;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RedisDAOTest {

    private RedisDAO rd = new RedisDAO();

    @Before
    public void setUp() {
        this.rd.clear();
    }

    @Test
    public void sePuedeGuardarClaveYRecuperarSuValor() {
        String clave = "tstClave";
        String valor = "tstValor";
        this.rd.put(clave, valor);
        assertEquals(this.rd.size(), 1);
        assertEquals(this.rd.get(clave), valor);
    }

}
