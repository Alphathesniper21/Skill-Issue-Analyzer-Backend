/**
 * Paquete que contiene las clases DTO (Data Transfer Object) del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.dto;

import java.util.Objects;

/**
 * DTO para activar o desactivar una cuenta de usuario desde el panel admin.
 */
public class CambioEstadoDTO {

	/**
	 * Atributo boolean que indica si el usuario esta activo o no.
	 */
	private boolean activo;

	/**
	 * Constructor vacio.
	 */
	public CambioEstadoDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor completo.
	 * @param activo Atributo boolean si la cuenta del usuario esta activo o no.
	 */
	public CambioEstadoDTO(boolean activo) {
		this.activo = activo;
	}

	/**
	 * Indica si el usuario se encuentra activo en el sistema.
	 *
	 * @return {@code true} si el usuario está activo, {@code false} en caso contrario
	 */
	public boolean isActivo() {
	    return activo;
	}

	/**
	 * Establece el estado de activación del usuario.
	 *
	 * @param activo {@code true} para activar el usuario, {@code false} para desactivarlo
	 */
	public void setActivo(boolean activo) {
	    this.activo = activo;
	}

	/**
	 * Metodo toString() para la representacion de la informacion de la clase por consola.
	 * @return Retorna la informacion de la clase en formato String.
	 */
	@Override
	public String toString() {
		return "Esta activo?: " + activo;
	}

	/**
	 * Retorna el codigo hash del conductor.
	 * @return Resultado entero que retorna el codigo hash de la clase.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(activo);
	}

	/**
	 * Metodo que compara dos objetos por igualdad.
	 * @paramn obj Objeto a comparar.
	 * @return Retorna un boolean que indica si son iguales (true), false si es lo contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		CambioEstadoDTO other = (CambioEstadoDTO) obj;
		return activo == other.activo;
	}

}