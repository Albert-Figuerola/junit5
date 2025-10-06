package models;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("123.47"));

        String esperado = "Andres";
        String real = cuenta.getPersona();

        assertEquals(esperado, real);

        assertTrue(real.equals("Andres"));
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("123.47"));

        assertEquals(123.47, cuenta.getSaldo().doubleValue());

        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);

        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

}