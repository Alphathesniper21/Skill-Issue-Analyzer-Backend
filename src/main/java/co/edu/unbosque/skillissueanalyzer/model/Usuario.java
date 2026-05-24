/**
 * Paquete que contiene las clases entidad del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Clase que represneta la informacion de un usuario.
 */
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

	/**
	 * Identificador (ID) unico del usuario.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

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
	 * Contrasena del usuario.
	 */
	private String password;

	/**
	 * Tipo de rol del usuario.
	 */
	@Enumerated(EnumType.STRING)
	private RolUsuario rol;

	/**
	 * Indicador boolean si esta activo o no.
	 */
	private boolean activo = true;

	/**
	 * Atributo que aplica para solicitudes para convertirse a admin.
	 */
	private String estadoSolicitud;

	/**
	 * Fecha de creacion de la cuenta.
	 */
	private LocalDateTime fechaCreacion;

	/**
	 * Constructor vacio.
	 */
	public Usuario() {
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
	public Usuario(String username, String nombre, String correo, String password, RolUsuario rol, boolean activo,
			String estadoSolicitud, LocalDateTime fechaCreacion) {
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
	 * Metodo que obtiene las autoridades o roles asignadas al usuario.
	 * @return Coleccion de autoridades del usuario.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + rol));
	}

	/**
	 * Metodo que permite obtener la contrasenia del usuario.
	 * @return Retorna la contrasenia del usuario.
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * Retorna el nombre de usuario utilizado para la autenticacion.
	 *
	 * @return nombre de usuario
	 */
	@Override
	public String getUsername() {
	    return username;
	}

	/**
	 * Indica si la cuenta del usuario no ha expirado.
	 *
	 * @return {@code true} siempre, las cuentas no expiran en este sistema
	 */
	@Override
	public boolean isAccountNonExpired() {
	    return true;
	}

	/**
	 * Indica si la cuenta del usuario no esta bloqueada.
	 * Una cuenta se considera bloqueada cuando el usuario esta inactivo.
	 *
	 * @return {@code true} si el usuario esta activo, {@code false} si esta bloqueado
	 */
	@Override
	public boolean isAccountNonLocked() {
	    return activo;
	}

	/**
	 * Indica si las credenciales del usuario no han expirado.
	 *
	 * @return {@code true} siempre, las credenciales no expiran en este sistema
	 */
	@Override
	public boolean isCredentialsNonExpired() {
	    return true;
	}

	/**
	 * Indica si el usuario esta habilitado en el sistema.
	 *
	 * @return {@code true} si el usuario esta activo, {@code false} en caso contrario
	 */
	@Override
	public boolean isEnabled() {
	    return activo;
	}

	/**
	 * Retorna el identificador unico del usuario.
	 *
	 * @return id del usuario
	 */
	public Long getId() {
	    return id;
	}

	/**
	 * Establece el identificador unico del usuario.
	 *
	 * @param id identificador a asignar
	 */
	public void setId(Long id) {
	    this.id = id;
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
	 * @return {@code true} si el usuario esta activo, {@code false} en caso contrario
	 */
	public boolean isActivo() {
	    return activo;
	}

	/**
	 * Establece el estado de activacion del usuario.
	 *
	 * @param activo {@code true} para activar el usuario, {@code false} para desactivarlo
	 */
	public void setActivo(boolean activo) {
	    this.activo = activo;
	}

	/**
	 * Retorna el estado de la solicitud de acceso del usuario.
	 *
	 * @return estado de la solicitud (por ejemplo, "PENDIENTE", "APROBADO", "RECHAZADO"),
	 *         o {@code null} si el usuario no tiene una solicitud pendiente
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
	 * Retorna la fecha y hora de creacion del usuario en el sistema.
	 *
	 * @return fecha y hora de creacion
	 */
	public LocalDateTime getFechaCreacion() {
	    return fechaCreacion;
	}

	/**
	 * Establece la fecha y hora de creacion del usuario.
	 *
	 * @param fechaCreacion fecha y hora a asignar
	 */
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
	    this.fechaCreacion = fechaCreacion;
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
	 * Establece la contrasena del usuario.
	 *
	 * @param password contrasena a asignar
	 */
	public void setPassword(String password) {
	    this.password = password;
	}

	/**
	 * Metodo toString() para la representacion de la informacion de la clase por
	 * consola.
	 * 
	 * @return Retorna la informacion de la clase en formato String.
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", nombre=" + nombre + ", correo=" + correo
				+ ", password=" + password + ", rol=" + rol + ", activo=" + activo + ", estadoSolicitud="
				+ estadoSolicitud + ", fechaCreacion=" + fechaCreacion + "]";
	}

	/**
	 * Retorna el codigo hash del conductor.
	 * 
	 * @return Resultado entero que retorna el codigo hash de la clase.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(activo, correo, estadoSolicitud, fechaCreacion, id, nombre, password, rol, username);
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return activo == other.activo && Objects.equals(correo, other.correo)
				&& Objects.equals(estadoSolicitud, other.estadoSolicitud)
				&& Objects.equals(fechaCreacion, other.fechaCreacion) && Objects.equals(id, other.id)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(password, other.password) && rol == other.rol
				&& Objects.equals(username, other.username);
	}

}