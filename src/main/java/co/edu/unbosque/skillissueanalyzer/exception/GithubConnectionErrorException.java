/**
 * Paquete de excepciones propias del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.exception;

/**
 * Excepcion que se lanza cuando ocurre un error de red o de protocolo
 * al conectarse con la API de GitHub. Resulta en una respuesta HTTP 502.
 */
public class GithubConnectionErrorException extends RuntimeException {

    /**
     * @param mensaje Descripcion del error de conexion.
     */
    public GithubConnectionErrorException(String mensaje) {
        super(mensaje);
    }
}
