/**
 * Paquete de excepciones propias del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.exception;

/**
 * Excepcion que se lanza cuando el archivo ZIP subido no contiene
 * ningun archivo .java. Resulta en una respuesta HTTP 422.
 */
public class EmptyJavaArchiveException extends RuntimeException {

    /**
     * Constructor de la excepcion.
     * @param mensaje Descripcion del error.
     */
    public EmptyJavaArchiveException(String mensaje) {
        super(mensaje);
    }
}
