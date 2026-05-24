/**
 * Paquete que contiene las clases DTO (Data Transfer Object) del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.dto;

import java.util.Objects;

/**
 * Clase DTO que representa la respuesta de autenticacion. Contiene los elementos necesarios para este proceso
 * (Token JWT y rol del usuario autenticado).
 */
public class AuthResponseDTO {

	/**
	 * Token JWT generado para el usuario autenticado.
	 */
	private String token;
	
	/**
	 * Nombre del usuario autenticado.
	 */
	private String username;
	
	/**
	 * Nombre de la persona del usuario.
	 */
	private String nombre;
	
	/**
	 * Tipo de rol del usuario autenticado.
	 */
	private String rol;

	/**
	 * Constructor vacio.
	 */
	public AuthResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor completo.
	 * @param token Token JWT.
	 * @param username Nombre del usuario autenticado.
	 * @param nombre Nombre real del usuario.
	 * @param rol Tipo de rol del usuario.
	 */
	public AuthResponseDTO(String token, String username, String nombre, String rol) {
		this.token    = token;
		this.username = username;
		this.nombre   = nombre;
		this.rol      = rol;
	}

	/**
	 * Retorna el token de autenticación del usuario.
	 *
	 * @return token JWT o de sesión asignado
	 */
	public String getToken() {
	    return token;
	}

	/**
	 * Establece el token de autenticación del usuario.
	 *
	 * @param token token a asignar
	 */
	public void setToken(String token) {
	    this.token = token;
	}

	/**
	 * Retorna el nombre de usuario (username) para inicio de sesión.
	 *
	 * @return nombre de usuario
	 */
	public String getUsername() {
	    return username;
	}

	/**
	 * Establece el nombre de usuario para inicio de sesión.
	 *
	 * @param username nombre de usuario a asignar
	 */
	public void setUsername(String username) {
	    this.username = username;
	}

	/**
	 * Retorna el nombre real del usuario.
	 *
	 * @return nombre del usuario
	 */
	public String getNombre() {
	    return nombre;
	}

	/**
	 * Establece el nombre real del usuario.
	 *
	 * @param nombre nombre a asignar
	 */
	public void setNombre(String nombre) {
	    this.nombre = nombre;
	}

	/**
	 * Retorna el rol asignado al usuario en el sistema.
	 *
	 * @return rol del usuario (por ejemplo, "ADMIN", "USER")
	 */
	public String getRol() {
	    return rol;
	}

	/**
	 * Establece el rol del usuario en el sistema.
	 *
	 * @param rol rol a asignar
	 */
	public void setRol(String rol) {
	    this.rol = rol;
	}
	
	/**
	 * Metodo toString() para la representacion de la informacion de la clase por consola.
	 * @return Retorna la informacion de la clase en formato String.
	 */
	@Override
	public String toString() {
		return "AuthResponseDTO [token=" + token + ", username=" + username + ", nombre=" + nombre + ", rol=" + rol
				+ "]";
	}
	
	/**
	 * Retorna el codigo hash del conductor.
	 * @return Resultado entero que retorna el codigo hash de la clase.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(nombre, rol, token, username);
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
		AuthResponseDTO other = (AuthResponseDTO) obj;
		return Objects.equals(nombre, other.nombre)
				&& Objects.equals(rol, other.rol)
				&& Objects.equals(token, other.token)
				&& Objects.equals(username, other.username);
	}

}