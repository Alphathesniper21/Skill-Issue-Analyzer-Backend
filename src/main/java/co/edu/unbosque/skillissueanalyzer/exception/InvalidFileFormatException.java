/**
 * Paquete de excepciones propias del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.exception;

/**
 * Excepcion que se lanza cuando el archivo subido no tiene el formato
 * esperado (por ejemplo, no es un .zip). Resulta en una respuesta HTTP 415.
 */
public class InvalidFileFormatException extends RuntimeException {

    /**
     * Constructor de la excepcion.
     * @param mensaje Descripcion del error de formato.
     */
    public InvalidFileFormatException(String mensaje) {
        super(mensaje);
    }
}
