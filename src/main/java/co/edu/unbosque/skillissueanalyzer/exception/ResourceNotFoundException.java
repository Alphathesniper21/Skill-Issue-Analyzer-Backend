/**
 * Paquete de excepciones propias del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.exception;

/**
 * Excepcion que se lanza cuando un recurso solicitado no existe en la base de datos.
 * Resulta en una respuesta HTTP 404.
 */
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * Constructor de la excepcion.
	 * @param mensaje Mensaje descriptivo del recurso no encontrado.
	 */
	public ResourceNotFoundException(String mensaje) {
		super(mensaje);
	}

}