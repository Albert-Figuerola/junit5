package models;

import exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CuentaTest {
    Cuenta cuenta;

    @BeforeAll
    static void beforeAll() {
//        System.out.println("Inicializando la clase test");
    }

    @AfterAll
    static void afterAll() {
//        System.out.println("Finalizando la clase test");
    }

    @BeforeEach
    void initTestMethod() {
        this.cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
//        System.out.println("Ejecutando init del metodo test");
    }

    @AfterEach
    void tearDown() {
//        System.out.println("Finalizando el método de prueba");
    }

    @Test
    @DisplayName("Probando nombre de la cuenta")
    void testNombreCuenta() {
        String esperado = "Andres";
        String real = cuenta.getPersona();

        assertNotNull(real, () -> "La cuenta no puede ser nula");
        assertEquals(esperado, real, () -> "El nombre de la cuenta no coincide");
        assertTrue(real.equals("Andres"), () -> "Nombre cuenta esperado igual al real");
    }

    @Test
    @DisplayName("Probando saldo de la cuenta")
    void testSaldoCuenta() {
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("Probando referencias de cuenta")
    void testReferenciaDeCuenta() {
        cuenta = new Cuenta("Jon Doe", new BigDecimal("890.997"));
        Cuenta cuenta2 = new Cuenta("Jon Doe", new BigDecimal("890.997"));

//        assertNotEquals(cuenta2, cuenta);
        assertEquals(cuenta2, cuenta);
    }

    @Test
    @DisplayName("Probando retiro de la cuenta")
    void testDebitoCuenta() {
        cuenta.debito(new BigDecimal(100));

        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Probando ingreso de la cuenta")
    void testCreditoCuenta() {
        cuenta.credito(new BigDecimal(100));

        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }


    @Test
    @DisplayName("Probando insuficiente dinero en la cuenta")
    void testDineroInsuficienteExceptionCuenta() {
        Exception exception = assertThrows(DineroInsuficienteException.class, () ->
                cuenta.debito(new BigDecimal(1500))
        );
        String actual = exception.getMessage();
        String esperado = "Dinero insuficiente";
        assertEquals(esperado, actual);
    }

    @Test
    @DisplayName("Probando transferencias entre cuentas")
    void testTransferenciaCuenta() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco del estado");

        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    // @Disabled("En pausa")
    @DisplayName("Probando relaciones entre cuentas")
    void testRelacionBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        assertAll(
                () -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(), () -> "Saldo de cuenta 2 no es el esperado"),
                () -> assertEquals("3000", cuenta1.getSaldo().toPlainString(), () -> "Saldo de cuenta 1 no es el esperado"),
                () -> assertEquals(2, banco.getCuentas().size(), () -> "El banco no tiene las cuentas esperadas"),
                () -> assertEquals("Banco del Estado", cuenta1.getBanco().getNombre()),
                () -> assertEquals("Andres", banco.getCuentas().stream()
                        .filter(c -> c.getPersona().equals("Andres"))
                        .findFirst()
                        .get()
                        .getPersona()
                ),
                () -> assertTrue(banco.getCuentas().stream()
                        .anyMatch(c -> c.getPersona().equals("Jhon Doe"))
                )
        );
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows() {
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void testSoloMAC() {
    }

    @Test
    @DisabledOnOs(OS.MAC)
    void testNoMAC() {
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testSolJdk8() {
    }

    @Test
    @EnabledOnJre(JRE.JAVA_19)
    void testSoloJdk19() {
    }

    @Test
    @DisabledOnJre(JRE.JAVA_19)
    void testNoSoloJdk19() {
    }

    @Test
    void printSystemProperties() {
//        System.getProperties().list(System.out);
//        Properties properties = System.getProperties();
//        properties.forEach((k, v) -> System.out.println(k + " : " + v));
    }

    @Test
    @DisplayName("Test Java version")
    @EnabledIfSystemProperty(named = "java.version", matches = ".*19.*")
    void testJavaVersion() {
    }

    @Test
    @DisplayName("Arch 64")
    @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
    void testOnly64() {
    }

    @Test
    @DisplayName("Arch 32")
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
    void testIf32() {
    }

    @Test
    @DisplayName("Test if user name")
    @EnabledIfSystemProperty(named = "user.name", matches = "gaddex")
    void testIfUserName() {
    }

    @Test
    @DisplayName("Test if DEV")
    @EnabledIfSystemProperty(named = "ENV", matches = "dev")
    void testDev() {
    }

    @Test
    void imprimirVariablesAmbiente() {
        System.getenv().forEach(
                (k, v) -> System.out.println(k + " = " + v)
        );
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "LOGNAME", matches = "gaddex")
    void testLogname() { }

    @Test
    @EnabledIfEnvironmentVariable(named = "USER", matches = "gaddex")
    void testUser() { }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "dev")
    void testEnv() { }


    @Test
    @DisabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "prod")
    void testEnvProdDisabled() { }

    @Test
    @DisplayName("Probando saldo de la cuenta en Dev")
    void testSaldoCuentaDev() {
        boolean isDev = "dev".equals(System.getProperty("ENV"));
        assumeTrue(isDev);
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("Probando saldo de la cuenta en Dev 2")
    void testSaldoCuentaDev2() {
        boolean isDev = "dev".equals(System.getProperty("ENV"));
        assumingThat(isDev, () -> {
            assertNotNull(cuenta.getSaldo());
            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        });
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

}