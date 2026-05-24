/**
 * Paquete que contiene las clases DTO (Data Transfer Object) del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.dto;

import java.util.Objects;
import co.edu.unbosque.skillissueanalyzer.model.RolUsuario;

/**
 * Clase DTO (Data Transfer Object) que representa la informacion de un usuario.
 * Se usa tanto para creacion como para respuestas del panel admin.
 */
public class UsuarioDTO {

	/**
	 * Identificador unico de la clase (ID).
	 */
	private long id;

	/**
	 * Nombre del usuario.
	 */
	private String username;

	/**
	 * Nombre real de la persona.
	 */
	private String nombre;

	/**
	 * Correo electronico del usuario.
	 */
	private String correo;

	/**
	 * Contrasenia del usuario.
	 */
	private String password;

	/**
	 * Tipo de rol del usuario.
	 */
	private RolUsuario rol;

	/**
	 * Atributo boolean que indica si el usuario esta activo o no.
	 */
	private boolean activo;

	/**
	 * Estado de solicitud en caso que la cuenta creada se solicite ser admin.
	 */
	private String estadoSolicitud;

	/**
	 * Fecha de creacion de la cuenta.
	 */
	private String fechaCreacion;

	/**
	 * Constructor vacio.
	 */
	public UsuarioDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor completo de la clase.
	 * 
	 * @param username        Nombre del usuario.
	 * @param nombre          Nombre de la persona real del usuario.
	 * @param correo          Correo del usuario.
	 * @param password      Contrasenia del usuario.
	 * @param rol             Rol del usuario.
	 * @param activo          Indicador boolean si esta activo o no.
	 * @param estadoSolicitud Estado de la solicitud de aprobacion de creacion de
	 *                        cuenta admin.
	 * @param fechaCreacion   Fecha de creacion de la cuenta.
	 */
	public UsuarioDTO(String username, String nombre, String correo, String password, RolUsuario rol, boolean activo,
			String estadoSolicitud, String fechaCreacion) {
		super();
		this.username = username;
		this.nombre = nombre;
		this.correo = correo;
		this.password = password;
		this.rol = rol;
		this.activo = activo;
		this.estadoSolicitud = estadoSolicitud;
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Retorna el identificador unico del usuario.
	 *
	 * @return id del usuario
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador unico del usuario.
	 *
	 * @param id identificador a asignar
	 */
	public void setId(long id) {
		this.id = id;
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
	 * Retorna el correo electronico del usuario.
	 *
	 * @return correo electronico
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo electronico del usuario.
	 *
	 * @param correo correo electronico a asignar
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Retorna la password del usuario.
	 *
	 * @return password del usuario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Establece la contrasena del usuario.
	 *
	 * @param contrasena contrasena a asignar
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Retorna el rol asignado al usuario en el sistema.
	 *
	 * @return rol del usuario de tipo {@link RolUsuario}
	 */
	public RolUsuario getRol() {
		return rol;
	}

	/**
	 * Establece el rol del usuario en el sistema.
	 *
	 * @param rol rol de tipo {@link RolUsuario} a asignar
	 */
	public void setRol(RolUsuario rol) {
		this.rol = rol;
	}

	/**
	 * Indica si el usuario se encuentra activo en el sistema.
	 *
	 * @return {@code true} si el usuario esta activo, {@code false} en caso
	 *         contrario
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * Establece el estado de activacion del usuario.
	 *
	 * @param activo {@code true} para activar el usuario, {@code false} para
	 *               desactivarlo
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * Retorna el estado de la solicitud de registro o acceso del usuario.
	 *
	 * @return estado de la solicitud (por ejemplo, "PENDIENTE", "APROBADO",
	 *         "RECHAZADO")
	 */
	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	/**
	 * Establece el estado de la solicitud del usuario.
	 *
	 * @param estadoSolicitud estado a asignar
	 */
	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	/**
	 * Retorna la fecha de creacion del registro.
	 *
	 * @return fecha de creacion como cadena de texto
	 */
	public String getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Establece la fecha de creacion del registro.
	 *
	 * @param fechaCreacion fecha de creacion a asignar
	 */
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Metodo toString() para la representacion de la informacion de la clase por
	 * consola.
	 * 
	 * @return Retorna la informacion de la clase en formato String.
	 */
	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", username=" + username + ", nombre=" + nombre + ", correo=" + correo
				+ ", contrasena=" + password + ", rol=" + rol + ", activo=" + activo + ", estadoSolicitud="
				+ estadoSolicitud + ", fechaCreacion=" + fechaCreacion + "]";
	}

	/**
	 * Retorna el codigo hash del conductor.
	 * 
	 * @return Resultado entero que retorna el codigo hash de la clase.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(activo, password, correo, estadoSolicitud, id, nombre, rol, username);
	}

	/**
	 * Metodo que compara dos objetos por igualdad.
	 * 
	 * @paramn obj Objeto a comparar.
	 * @return Retorna un boolean que indica si son iguales (true), false si es lo
	 *         contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		return activo == other.activo && Objects.equals(password, other.password)
				&& Objects.equals(correo, other.correo) && Objects.equals(estadoSolicitud, other.estadoSolicitud)
				&& id == other.id && Objects.equals(nombre, other.nombre) && rol == other.rol
				&& Objects.equals(username, other.username);
	}

}