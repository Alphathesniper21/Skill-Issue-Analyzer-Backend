/**
 * Paquete de excepciones propias del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.exception;

/**
 * Clase de excepcion propia que consiste en la validacion del correo electronico ingresado
 * por el usuario.
 */
public class InvalidEmailFormat extends Exception {

	/**
	 * Constructor vacio de la excepcion.
	 */
	public InvalidEmailFormat() {
		
		super("El correo ingresado no sigue un formato valido.");
	}
}
