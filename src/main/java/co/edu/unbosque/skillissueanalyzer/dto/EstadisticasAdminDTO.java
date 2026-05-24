/**
 * Paquete que contiene las clases DTO (Data Transfer Object) del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.dto;

import java.util.Objects;

/**
 * Clase DTO (Data Transfer Object) con estadisticas generales del sistema para el panel de administracion.
 */
public class EstadisticasAdminDTO {

	/**
	 * Numero total de usuarios registrados.
	 */
	private long totalUsuarios;
	
	/**
	 * Numero total de analisis realizados.
	 */
	private long totalAnalisis;
	
	/**
	 * Numero total de solicitudes pendientes.
	 */
	private long solicitudesPendientes;
	
	/**
	 * Promedio total de problemas.
	 */
	private double promedioProblemas;

	/**
	 * Constructor vacio.
	 */
	public EstadisticasAdminDTO() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor completo.
	 * @param totalUsuarios Numero total de usuarios.
	 * @param totalAnalisis Numero total de analisis.
	 * @param solicitudesPendientes Numero de solicitudes pendientes.
	 * @param promedioProblemas Promedio de problemas.
	 */
	public EstadisticasAdminDTO(long totalUsuarios, long totalAnalisis, long solicitudesPendientes,
			double promedioProblemas) {
		super();
		this.totalUsuarios = totalUsuarios;
		this.totalAnalisis = totalAnalisis;
		this.solicitudesPendientes = solicitudesPendientes;
		this.promedioProblemas = promedioProblemas;
	}

	/**
	 * Retorna el total de usuarios registrados en el sistema.
	 *
	 * @return número total de usuarios
	 */
	public long getTotalUsuarios() {
	    return totalUsuarios;
	}

	/**
	 * Establece el total de usuarios registrados en el sistema.
	 *
	 * @param totalUsuarios cantidad de usuarios a asignar
	 */
	public void setTotalUsuarios(long totalUsuarios) {
	    this.totalUsuarios = totalUsuarios;
	}

	/**
	 * Retorna el total de análisis realizados en el sistema.
	 *
	 * @return número total de análisis
	 */
	public long getTotalAnalisis() {
	    return totalAnalisis;
	}

	/**
	 * Establece el total de análisis realizados en el sistema.
	 *
	 * @param totalAnalisis cantidad de análisis a asignar
	 */
	public void setTotalAnalisis(long totalAnalisis) {
	    this.totalAnalisis = totalAnalisis;
	}

	/**
	 * Retorna el numero de solicitudes pendientes por procesar.
	 *
	 * @return cantidad de solicitudes pendientes
	 */
	public long getSolicitudesPendientes() {
	    return solicitudesPendientes;
	}

	/**
	 * Establece el numero de solicitudes pendientes por procesar.
	 *
	 * @param solicitudesPendientes cantidad de solicitudes pendientes a asignar
	 */
	public void setSolicitudesPendientes(long solicitudesPendientes) {
	    this.solicitudesPendientes = solicitudesPendientes;
	}

	/**
	 * Retorna el promedio de problemas detectados por analisis.
	 *
	 * @return promedio de problemas
	 */
	public double getPromedioProblemas() {
	    return promedioProblemas;
	}

	/**
	 * Establece el promedio de problemas detectados por analisis.
	 *
	 * @param promedioProblemas promedio a asignar
	 */
	public void setPromedioProblemas(double promedioProblemas) {
	    this.promedioProblemas = promedioProblemas;
	}
	
	/**
	 * Metodo toString() para la representacion de la informacion de la clase por consola.
	 * @return Retorna la informacion de la clase en formato String.
	 */
	@Override
	public String toString() {
		return "EstadisticasAdminDTO [totalUsuarios=" + totalUsuarios + ", totalAnalisis=" + totalAnalisis
				+ ", solicitudesPendientes=" + solicitudesPendientes + ", promedioProblemas=" + promedioProblemas + "]";
	}

	/**
	 * Retorna el codigo hash del conductor.
	 * @return Resultado entero que retorna el codigo hash de la clase.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(promedioProblemas, solicitudesPendientes, totalAnalisis, totalUsuarios);
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
		EstadisticasAdminDTO other = (EstadisticasAdminDTO) obj;
		return Double.compare(promedioProblemas, other.promedioProblemas) == 0
				&& solicitudesPendientes == other.solicitudesPendientes
				&& totalAnalisis == other.totalAnalisis
				&& totalUsuarios == other.totalUsuarios;
	}

}