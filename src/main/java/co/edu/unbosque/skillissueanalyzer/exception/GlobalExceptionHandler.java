/**
 * Paquete de excepciones propias del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Manejador global de excepciones.
 * Intercepta las excepciones lanzadas por los controladores y servicios
 * y las convierte en respuestas HTTP con el codigo de estado apropiado.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validacion de negocio — 400 Bad Request.
     */
    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<String> handleValidacion(ValidacionException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Maneja recursos no encontrados — 404 Not Found.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNoEncontrado(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Maneja archivos vacios o sin URL — 400 Bad Request.
     */
    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<String> handleArchivoVacio(EmptyFileException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Maneja formatos de archivo no soportados — 415 Unsupported Media Type.
     */
    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<String> handleFormatoInvalido(InvalidFileFormatException ex) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(ex.getMessage());
    }

    /**
     * Maneja archivos ZIP sin codigo Java — 422 Unprocessable Entity.
     */
    @ExceptionHandler(EmptyJavaArchiveException.class)
    public ResponseEntity<String> handleSinCodigoJava(EmptyJavaArchiveException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    /**
     * Maneja archivos ZIP corruptos o ilegibles — 400 Bad Request.
     */
    @ExceptionHandler(CorruptFileException.class)
    public ResponseEntity<String> handleArchivocorrupto(CorruptFileException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Maneja URLs de repositorio con formato invalido — 400 Bad Request.
     */
    @ExceptionHandler(InvalidRepositoryUrlException.class)
    public ResponseEntity<String> handleUrlInvalida(InvalidRepositoryUrlException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Maneja repositorios privados, inexistentes o sin rama valida — 404 Not Found.
     */
    @ExceptionHandler(InaccessibleRepositoryException.class)
    public ResponseEntity<String> handleRepoNoAccesible(InaccessibleRepositoryException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Maneja repositorios sin archivos Java — 422 Unprocessable Entity.
     */
    @ExceptionHandler(EmptyJavaRepositoryException.class)
    public ResponseEntity<String> handleRepoSinJava(EmptyJavaRepositoryException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    /**
     * Maneja errores de red o protocolo con GitHub — 502 Bad Gateway.
     */
    @ExceptionHandler(GithubConnectionErrorException.class)
    public ResponseEntity<String> handleConexionGithub(GithubConnectionErrorException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(ex.getMessage());
    }

    /**
     * Maneja el servicio de IA no disponible — 503 Service Unavailable.
     */
    @ExceptionHandler(AIServiceUnavailableException.class)
    public ResponseEntity<String> handleServicioIA(AIServiceUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }

    /**
     * Maneja credenciales incorrectas — 401 Unauthorized.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Credenciales invalidas. Verifica tu usuario y contrasena.");
    }

    /**
     * Maneja cuentas deshabilitadas o pendientes de aprobacion — 403 Forbidden.
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<String> handleDisabled(DisabledException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Tu cuenta esta pendiente de aprobacion o ha sido desactivada.");
    }

    /**
     * Maneja cuentas bloqueadas — 403 Forbidden.
     */
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<String> handleLocked(LockedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Tu cuenta ha sido bloqueada. Contacta al administrador.");
    }

    /**
     * Maneja archivos demasiado grandes — 413 Payload Too Large.
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxUpload(MaxUploadSizeExceededException ex) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("El archivo supera el tamano maximo permitido (10 MB).");
    }

    /**
     * Maneja cualquier otra excepcion no controlada — 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + ex.getMessage());
    }

}