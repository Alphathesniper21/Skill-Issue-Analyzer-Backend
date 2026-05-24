package co.edu.unbosque.skillissueanalyzer.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Clase de prueba para el manejador global de excepciones.
 *
 * <p>Esta clase contiene pruebas unitarias para verificar el correcto funcionamiento
 * del GlobalExceptionHandler y sus respuestas HTTP.
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    /**
     * Inicializa el manejador de excepciones antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    /**
     * Prueba que ValidacionException retorna 400 Bad Request.
     */
    @Test
    void testHandleValidacion() {
        ValidacionException ex = new ValidacionException("Error de validacion");
        ResponseEntity<String> response = handler.handleValidacion(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error de validacion", response.getBody());
    }

    /**
     * Prueba que ResourceNotFoundException retorna 404 Not Found.
     */
    @Test
    void testHandleNoEncontrado() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Recurso no encontrado");
        ResponseEntity<String> response = handler.handleNoEncontrado(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Recurso no encontrado", response.getBody());
    }

    /**
     * Prueba que EmptyFileException retorna 400 Bad Request.
     */
    @Test
    void testHandleArchivoVacio() {
        EmptyFileException ex = new EmptyFileException("Archivo vacio");
        ResponseEntity<String> response = handler.handleArchivoVacio(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Archivo vacio", response.getBody());
    }

    /**
     * Prueba que InvalidFileFormatException retorna 415 Unsupported Media Type.
     */
    @Test
    void testHandleFormatoInvalido() {
        InvalidFileFormatException ex = new InvalidFileFormatException("Formato invalido");
        ResponseEntity<String> response = handler.handleFormatoInvalido(ex);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        assertEquals("Formato invalido", response.getBody());
    }

    /**
     * Prueba que EmptyJavaArchiveException retorna 422 Unprocessable Entity.
     */
    @Test
    void testHandleSinCodigoJava() {
        EmptyJavaArchiveException ex = new EmptyJavaArchiveException("Sin codigo Java");
        ResponseEntity<String> response = handler.handleSinCodigoJava(ex);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Sin codigo Java", response.getBody());
    }

    /**
     * Prueba que CorruptFileException retorna 400 Bad Request.
     */
    @Test
    void testHandleArchivoCorrupto() {
        CorruptFileException ex = new CorruptFileException("Archivo corrupto");
        ResponseEntity<String> response = handler.handleArchivocorrupto(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Archivo corrupto", response.getBody());
    }

    /**
     * Prueba que InvalidRepositoryUrlException retorna 400 Bad Request.
     */
    @Test
    void testHandleUrlInvalida() {
        InvalidRepositoryUrlException ex = new InvalidRepositoryUrlException("URL invalida");
        ResponseEntity<String> response = handler.handleUrlInvalida(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("URL invalida", response.getBody());
    }

    /**
     * Prueba que InaccessibleRepositoryException retorna 404 Not Found.
     */
    @Test
    void testHandleRepoNoAccesible() {
        InaccessibleRepositoryException ex = new InaccessibleRepositoryException("Repositorio no accesible");
        ResponseEntity<String> response = handler.handleRepoNoAccesible(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Repositorio no accesible", response.getBody());
    }

    /**
     * Prueba que EmptyJavaRepositoryException retorna 422 Unprocessable Entity.
     */
    @Test
    void testHandleRepoSinJava() {
        EmptyJavaRepositoryException ex = new EmptyJavaRepositoryException("Repositorio sin Java");
        ResponseEntity<String> response = handler.handleRepoSinJava(ex);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Repositorio sin Java", response.getBody());
    }

    /**
     * Prueba que GithubConnectionErrorException retorna 502 Bad Gateway.
     */
    @Test
    void testHandleConexionGithub() {
        GithubConnectionErrorException ex = new GithubConnectionErrorException("Error de conexion con GitHub");
        ResponseEntity<String> response = handler.handleConexionGithub(ex);

        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
        assertEquals("Error de conexion con GitHub", response.getBody());
    }

    /**
     * Prueba que AIServiceUnavailableException retorna 503 Service Unavailable.
     */
    @Test
    void testHandleServicioIA() {
        AIServiceUnavailableException ex = new AIServiceUnavailableException("Servicio IA no disponible");
        ResponseEntity<String> response = handler.handleServicioIA(ex);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals("Servicio IA no disponible", response.getBody());
    }

    /**
     * Prueba que BadCredentialsException retorna 401 Unauthorized.
     */
    @Test
    void testHandleBadCredentials() {
        BadCredentialsException ex = new BadCredentialsException("Credenciales invalidas");
        ResponseEntity<String> response = handler.handleBadCredentials(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Credenciales invalidas. Verifica tu usuario y contrasena.", response.getBody());
    }

    /**
     * Prueba que DisabledException retorna 403 Forbidden.
     */
    @Test
    void testHandleDisabled() {
        DisabledException ex = new DisabledException("Cuenta deshabilitada");
        ResponseEntity<String> response = handler.handleDisabled(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Tu cuenta esta pendiente de aprobacion o ha sido desactivada.", response.getBody());
    }

    /**
     * Prueba que LockedException retorna 403 Forbidden.
     */
    @Test
    void testHandleLocked() {
        LockedException ex = new LockedException("Cuenta bloqueada");
        ResponseEntity<String> response = handler.handleLocked(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Tu cuenta ha sido bloqueada. Contacta al administrador.", response.getBody());
    }

    /**
     * Prueba que MaxUploadSizeExceededException retorna 413 Payload Too Large.
     */
    @Test
    void testHandleMaxUpload() {
        MaxUploadSizeExceededException ex = new MaxUploadSizeExceededException(10L);
        ResponseEntity<String> response = handler.handleMaxUpload(ex);

        assertEquals(HttpStatus.PAYLOAD_TOO_LARGE, response.getStatusCode());
        assertEquals("El archivo supera el tamano maximo permitido (10 MB).", response.getBody());
    }

    /**
     * Prueba que una excepcion generica retorna 500 Internal Server Error.
     */
    @Test
    void testHandleGeneral() {
        Exception ex = new Exception("Error inesperado");
        ResponseEntity<String> response = handler.handleGeneral(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error interno del servidor: Error inesperado", response.getBody());
    }
}