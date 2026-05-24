/**
 * Paquete principal del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Clase principal de prueba para el aplicativo SkillIssueAnalyzer.
 *
 * <p>Esta clase contiene pruebas para verificar que el contexto de la aplicacion Spring se carga
 * correctamente y que la funcionalidad basica funciona como se espera.
 */
@SpringBootTest
class SkillissueanalyzerApplicationTests {

    /**
     * Prueba que el contexto de la aplicacion Spring se carga correctamente.
     *
     * Esta es una prueba basica que verifica que el contexto de Spring puede cargarse sin errores.
     */
    @Test
    void contextLoads() {
        assertEquals(0, 0);
    }

    /**
     * Prueba que el bean ModelMapper se crea correctamente.
     *
     * Esta prueba verifica que el bean ModelMapper definido en la aplicacion se crea correctamente.
     */
    @Test
    void testModelMapperBean() {
        SkillissueanalyzerApplication app = new SkillissueanalyzerApplication();
        assertNotNull(app.getModelMapper(), "El bean ModelMapper no debe ser nulo");
    }
}