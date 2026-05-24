/**
 * Paquete de excepciones propias del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.exception;

/**
 * Excepcion que se lanza cuando el servicio de inteligencia artificial
 * (Claude) no esta disponible o devuelve un error. Resulta en una respuesta HTTP 503.
 */
public class AIServiceUnavailableException extends RuntimeException {

    /**
     * Constructor de la excepcion.
     * @param mensaje Descripcion del error del servicio de IA.
     */
    public AIServiceUnavailableException(String mensaje) {
        super(mensaje);
    }
}
