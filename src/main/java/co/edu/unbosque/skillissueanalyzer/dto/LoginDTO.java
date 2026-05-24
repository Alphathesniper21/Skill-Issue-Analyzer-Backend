/**
 * Paquete que contiene las clases DTO (Data Transfer Object) del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.dto;

import java.util.Objects;

/**
 * Clase DTO (Data Transfer Object) que representa los datos necesarios para autenticar un usuario en el sistema.
 * Contiene el nombre de usuario y la contrasenia requeridos en el proceso de login.
 */
public class LoginDTO {

	/**
	 * Nombre de usuario.
	 */
	private String username;
	
	/**
	 * Contrasenia del usuario de carrera.
	 */
	private String contrasena;
	
	/**
	 * Constructor vacio.
	 */
	public LoginDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor completo.
	 * @param username Nombre de usuario.
	 * @param contrasena Contrasenia del usuario.
	 */
	public LoginDTO(String username, String contrasena) {
		this.username = username;
		this.contrasena = contrasena;
	}

    /**
     * Retorna el nombre de usuario para inicio de sesion.
     *
     * @return nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario para inicio de sesion.
     *
     * @param username nombre de usuario a asignar
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retorna la contrasena del usuario.
     *
     * @return contrasena del usuario
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contrasena del usuario.
     *
     * @param contrasena contrasena a asignar
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Metodo toString() para la representacion de la informacion de la clase por consola.
	 * @return Retorna la informacion de la clase en formato String.
     */
	@Override
	public String toString() {
		return "LoginDTO [username=" + username + ", contrasena=" + contrasena + "]";
	}

	/**
	 * Retorna el codigo hash del conductor.
	 * @return Resultado entero que retorna el codigo hash de la clase.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(contrasena, username);
	}

	/**
	 * Metodo que compara dos objetos por igualdad.
	 * @paramn obj Objeto a comparar.
	 * @return Retorna un boolean que indica si son iguales (true), false si es lo contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		LoginDTO other = (LoginDTO) obj;
		return Objects.equals(contrasena, other.contrasena) && Objects.equals(username, other.username);
	}

}