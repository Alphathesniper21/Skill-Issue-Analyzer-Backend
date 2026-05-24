/**
 * Paquete que contiene las clases DTO (Data Transfer Object) del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.dto;

import java.util.Objects;

/**
 * Clase DTO (Data Transfer Object) para el registro de un nuevo usuario.
 */
public class RegistroDTO {

	/**
	 * Nombre de usuario para el registro.
	 */
	 private String username;
	 
	 /**
	  * Contrasenia de usuario para el registro.
	  */
     private String contrasena;
     
     /**
      * Nombre completo la persona.
      */
     private String nombreCompleto;
     
     /**
      * Correo electronico del usuario.
      */
     private String email;
     
     /**
      * Tipo de rol que tiene el usuario.
      */
     private String rol; 
     
     /**
      * Constructor vacio.
      */
     public RegistroDTO() {
		// TODO Auto-generated constructor stub
	}

     /**
      * Constructor completo de la clase.
      * @param username Nombre de usuario.
      * @param contrasena Contrasenia del usuario.
      * @param nombreCompleto Nombre completo de la persona.
      * @param email Correo electronico del usuario.
      * @param rol Rol del usuario.
      */
	public RegistroDTO(String username, String contrasena, String nombreCompleto, String email, String rol) {
		super();
		this.username = username;
		this.contrasena = contrasena;
		this.nombreCompleto = nombreCompleto;
		this.email = email;
		this.rol = rol;
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
	 * Retorna el nombre completo del usuario.
	 *
	 * @return nombre completo del usuario
	 */
	public String getNombreCompleto() {
	    return nombreCompleto;
	}

	/**
	 * Establece el nombre completo del usuario.
	 *
	 * @param nombreCompleto nombre completo a asignar
	 */
	public void setNombreCompleto(String nombreCompleto) {
	    this.nombreCompleto = nombreCompleto;
	}

	/**
	 * Retorna el correo electronico del usuario.
	 *
	 * @return correo electronico
	 */
	public String getEmail() {
	    return email;
	}

	/**
	 * Establece el correo electronico del usuario.
	 *
	 * @param email correo electronico a asignar
	 */
	public void setEmail(String email) {
	    this.email = email;
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
		return "RegistroDTO [username=" + username + ", contrasena=" + contrasena + ", nombreCompleto=" + nombreCompleto
				+ ", email=" + email + ", rol=" + rol + "]";
	}

	/**
	 * Retorna el codigo hash del conductor.
	 * @return Resultado entero que retorna el codigo hash de la clase.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(email, nombreCompleto, contrasena, rol, username);
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistroDTO other = (RegistroDTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(nombreCompleto, other.nombreCompleto)
				&& Objects.equals(contrasena, other.contrasena) && Objects.equals(rol, other.rol)
				&& Objects.equals(username, other.username);
	}

	
}