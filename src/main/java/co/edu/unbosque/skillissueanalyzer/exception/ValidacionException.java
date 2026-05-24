/**
 * Paquete de excepciones propias del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.exception;

/**
 * Excepcion que se lanza cuando los datos de entrada no pasan la validacion de negocio.
 * Resulta en una respuesta HTTP 400.
 */
public class ValidacionException extends RuntimeException {

	/**
	 * Constructor de la excepcion.
	 * @param mensaje Descripcion del error de validacion.
	 */
	public ValidacionException(String mensaje) {
		super(mensaje);
	}

}