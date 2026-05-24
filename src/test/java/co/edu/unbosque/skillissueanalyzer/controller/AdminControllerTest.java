/**
 * Paquete de las clases controller que permiten la gestion de los endpoints del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import co.edu.unbosque.skillissueanalyzer.dto.AnalisisDTO;
import co.edu.unbosque.skillissueanalyzer.dto.CambioEstadoDTO;
import co.edu.unbosque.skillissueanalyzer.dto.EstadisticasAdminDTO;
import co.edu.unbosque.skillissueanalyzer.dto.UsuarioDTO;
import co.edu.unbosque.skillissueanalyzer.repository.AnalisisRepository;
import co.edu.unbosque.skillissueanalyzer.repository.UsuarioRepository;
import co.edu.unbosque.skillissueanalyzer.service.AnalisisService;
import co.edu.unbosque.skillissueanalyzer.service.UsuarioService;

/**
 * Clase de prueba para el controlador de administracion.
 *
 * <p>Esta clase contiene pruebas unitarias para verificar el correcto funcionamiento
 * de los endpoints del AdminController.
 */
class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private AnalisisService analisisService;

    @Mock
    private UsuarioRepository usuarioRepo;

    @Mock
    private AnalisisRepository analisisRepo;

    /**
     * Inicializa los mocks antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba que el metodo getUsuarios retorna una lista de usuarios correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y la lista de usuarios esperada.
     */
    @Test
    void testGetUsuarios() {
        List<UsuarioDTO> lista = List.of(new UsuarioDTO());
        when(usuarioService.getTodos()).thenReturn(lista);

        ResponseEntity<List<UsuarioDTO>> response = adminController.getUsuarios();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    /**
     * Prueba que el metodo eliminarUsuario elimina un usuario correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y el mensaje de exito esperado.
     */
    @Test
    void testEliminarUsuario() {
        doNothing().when(usuarioService).eliminar(1L);

        ResponseEntity<String> response = adminController.eliminarUsuario(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Usuario eliminado.", response.getBody());
    }

    /**
     * Prueba que el metodo cambiarEstado actualiza el estado de un usuario correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y el mensaje de exito esperado.
     */
    @Test
    void testCambiarEstado() {
        CambioEstadoDTO dto = new CambioEstadoDTO();
        doNothing().when(usuarioService).cambiarEstado(1L, dto.isActivo());

        ResponseEntity<String> response = adminController.cambiarEstado(1L, dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Estado actualizado.", response.getBody());
    }

    /**
     * Prueba que el metodo getSolicitudesPendientes retorna la lista de solicitudes correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y la lista de solicitudes esperada.
     */
    @Test
    void testGetSolicitudesPendientes() {
        List<UsuarioDTO> lista = List.of(new UsuarioDTO());
        when(usuarioService.getSolicitudesPendientes()).thenReturn(lista);

        ResponseEntity<List<UsuarioDTO>> response = adminController.getSolicitudesPendientes();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    /**
     * Prueba que el metodo aprobar aprueba una solicitud correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y el mensaje de aprobacion esperado.
     */
    @Test
    void testAprobar() {
        doNothing().when(usuarioService).aprobarSolicitud(1L);

        ResponseEntity<String> response = adminController.aprobar(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Solicitud aprobada. El usuario ya puede acceder.", response.getBody());
    }

    /**
     * Prueba que el metodo rechazar rechaza una solicitud correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y el mensaje de rechazo esperado.
     */
    @Test
    void testRechazar() {
        doNothing().when(usuarioService).rechazarSolicitud(1L);

        ResponseEntity<String> response = adminController.rechazar(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Solicitud rechazada y eliminada.", response.getBody());
    }

    /**
     * Prueba que el metodo getTodosAnalisis retorna la lista de analisis correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y la lista de analisis esperada.
     */
    @Test
    void testGetTodosAnalisis() {
        List<AnalisisDTO> lista = List.of(new AnalisisDTO());
        when(analisisService.getTodos()).thenReturn(lista);

        ResponseEntity<List<AnalisisDTO>> response = adminController.getTodosAnalisis();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    /**
     * Prueba que el metodo getEstadisticas retorna las estadisticas correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y las estadisticas esperadas.
     */
    @Test
    void testGetEstadisticas() {
        when(usuarioRepo.count()).thenReturn(5L);
        when(analisisRepo.count()).thenReturn(10L);
        when(usuarioRepo.findByEstadoSolicitud("PENDIENTE")).thenReturn(List.of());
        when(analisisRepo.promedioProblemas()).thenReturn(3.5);

        ResponseEntity<EstadisticasAdminDTO> response = adminController.getEstadisticas();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(5L, response.getBody().getTotalUsuarios());
        assertEquals(10L, response.getBody().getTotalAnalisis());
    }
}