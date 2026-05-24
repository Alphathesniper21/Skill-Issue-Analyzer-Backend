/**
 * Paquete de excepciones propias del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.exception;

/**
 * Excepcion que se lanza cuando el repositorio de GitHub no existe,
 * es privado o no tiene una rama accesible. Resulta en una respuesta HTTP 404.
 */
public class InaccessibleRepositoryException extends RuntimeException {

    /**
     * Constructor de la excepcion.
     * @param mensaje Descripcion del error de acceso.
     */
    public InaccessibleRepositoryException(String mensaje) {
        super(mensaje);
    }
}
