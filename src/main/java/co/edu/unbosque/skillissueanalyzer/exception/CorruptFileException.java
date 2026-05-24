/**
 * Paquete de excepciones propias del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.exception;

/**
 * Excepcion que se lanza cuando el archivo ZIP no se puede leer,
 * esta corrupto o tiene una estructura invalida. Resulta en una respuesta HTTP 400.
 */
public class CorruptFileException extends RuntimeException {

    /**
     * Constructor de la excepcion.
     * @param mensaje Descripcion del error de lectura.
     */
    public CorruptFileException(String mensaje) {
        super(mensaje);
    }
}
