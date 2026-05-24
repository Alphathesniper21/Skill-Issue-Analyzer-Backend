/**
 * Paquete de las clases controller que permiten la gestion de los endpoints del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unbosque.skillissueanalyzer.dto.AnalisisDTO;
import co.edu.unbosque.skillissueanalyzer.service.AnalisisService;

/**
 * Clase de prueba para el controlador de analisis.
 *
 * <p>Esta clase contiene pruebas unitarias para verificar el correcto funcionamiento
 * de los endpoints del AnalisisController.
 */
class AnalisisControllerTest {

    @InjectMocks
    private AnalisisController analisisController;

    @Mock
    private AnalisisService analisisService;

    /**
     * Inicializa los mocks antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba que el metodo analizarZip retorna un AnalisisDTO correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y el resultado del analisis esperado.
     */
    @Test
    void testAnalizarZip() {
        MultipartFile archivo = mock(MultipartFile.class);
        AnalisisDTO dto = new AnalisisDTO();
        when(analisisService.analizarZip(archivo)).thenReturn(dto);

        ResponseEntity<AnalisisDTO> response = analisisController.analizarZip(archivo);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    /**
     * Prueba que el metodo getMisAnalisis retorna la lista de analisis del usuario correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y la lista de analisis esperada.
     */
    @Test
    void testGetMisAnalisis() {
        List<AnalisisDTO> lista = List.of(new AnalisisDTO());
        when(analisisService.getMisAnalisis()).thenReturn(lista);

        ResponseEntity<List<AnalisisDTO>> response = analisisController.getMisAnalisis();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    /**
     * Prueba que el metodo getById retorna un analisis por su ID correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y el analisis esperado.
     */
    @Test
    void testGetById() {
        AnalisisDTO dto = new AnalisisDTO();
        when(analisisService.getById(1L)).thenReturn(dto);

        ResponseEntity<AnalisisDTO> response = analisisController.getById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    /**
     * Prueba que el metodo eliminar elimina un analisis correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y el mensaje de exito esperado.
     */
    @Test
    void testEliminar() {
        doNothing().when(analisisService).eliminar(1L);

        ResponseEntity<String> response = analisisController.eliminar(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Análisis eliminado correctamente.", response.getBody());
    }

    /**
     * Prueba que el metodo analizarRepo retorna un AnalisisDTO correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y el resultado del analisis esperado.
     */
    @Test
    void testAnalizarRepo() {
        String repoUrl = "https://github.com/owner/repo";
        AnalisisDTO dto = new AnalisisDTO();
        when(analisisService.analizarRepo(repoUrl)).thenReturn(dto);

        ResponseEntity<AnalisisDTO> response = analisisController.analizarRepo(repoUrl);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}