/**
 * Paquete de excepciones propias del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.exception;

/**
 * Excepcion que se lanza cuando el repositorio de GitHub no contiene
 * ningun archivo .java. Resulta en una respuesta HTTP 422.
 */
public class EmptyJavaRepositoryException extends RuntimeException {

    /**
     * Constructor de la excepcion.
     * @param mensaje Descripcion del error.
     */
    public EmptyJavaRepositoryException(String mensaje) {
        super(mensaje);
    }
}
