/**
 * Paquete de excepciones propias del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.exception;

/**
 * Excepcion que se lanza cuando el usuario no adjunta ningun archivo
 * o sube un archivo vacio. Resulta en una respuesta HTTP 400.
 */
public class EmptyFileException extends RuntimeException {

    /**
     * @param mensaje Descripcion del error.
     */
    public EmptyFileException(String mensaje) {
        super(mensaje);
    }
}
