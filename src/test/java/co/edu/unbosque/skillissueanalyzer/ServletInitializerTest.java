/**
 * Paquete principal del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Clase de prueba para el inicializador de Servlet.
 *
 * <p>Esta clase contiene pruebas para verificar que el ServletInitializer configura correctamente
 * la aplicacion Spring Boot.
 */
@SpringBootTest
class ServletInitializerTest {

    /**
     * Prueba que el metodo configure retorna un SpringApplicationBuilder configurado.
     *
     * La prueba verifica que el metodo configure del ServletInitializer configura correctamente
     * el SpringApplicationBuilder con la clase principal de la aplicacion.
     */
    @Test
    void testConfigure() {
        ServletInitializer initializer = new ServletInitializer();
        SpringApplicationBuilder builder = new SpringApplicationBuilder();

        SpringApplicationBuilder result = initializer.configure(builder);

        assertNotNull(result, "El SpringApplicationBuilder configurado no debe ser nulo");
    }
}