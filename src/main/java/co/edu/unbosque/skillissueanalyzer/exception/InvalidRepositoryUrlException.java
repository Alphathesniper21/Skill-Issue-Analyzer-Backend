/**
 * Paquete de excepciones propias del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.exception;

/**
 * Excepcion que se lanza cuando la URL del repositorio de GitHub
 * no tiene el formato esperado. Resulta en una respuesta HTTP 400.
 */
public class InvalidRepositoryUrlException extends RuntimeException {

    /**
     * Constructor de la excepcion.
     * @param mensaje Descripcion del error de formato de URL.
     */
    public InvalidRepositoryUrlException(String mensaje) {
        super(mensaje);
    }
}
