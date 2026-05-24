/**
 * Paquete que contiene las clases DTO (Data Transfer Object) del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Clase DTO (Data Transfer Object) completo de un analisis con sus malas practicas. Se usa como respuesta en lso endpoints de 
 * analisis.
 */
public class AnalisisDTO {

	/**
	 * Identificador unico del analisis.
	 */
	private long id;
	
	/**
	 * Codigo fuente del analisis.
	 */
	private String codigoFuente;
	
	/**
	 * Fecha del analisis.
	 */
	private LocalDateTime fechaAnalisis;
	
	/**
	 * Nombre de usuario del autor del codigo.
	 */
	private String nombreUsuarioAutor;
	
	/**
	 * Numero total de problemas detectados.
	 */
	private int totalProblemas;
	
	/**
	 * Puntuacion del codigo analizado.
	 */
	private int puntuacion;
	
	/**
	 * Lista que contiene las malas practicas detectadas.
	 */
	private List<MalaPracticaDTO> malasPracticas;

	/**
	 * Constructor vacio del analisis.
	 */
	public AnalisisDTO() {
	}

	/**
	 * Constructor completo.
	 * @param codigoFuente Codigo fuente del analisis.
	 * @param fechaAnalisis Fecha de analisis.
	 * @param nombreUsuarioAutor Nombre de usuario del autor.
	 * @param totalProblemas Numero total de problemas.
	 * @param puntuacion Puntuacion del codigo.
	 * @param malasPracticas Numero de malas practicas detectadas.
	 */
	public AnalisisDTO(String codigoFuente, LocalDateTime fechaAnalisis, String nombreUsuarioAutor, int totalProblemas,
			int puntuacion, List<MalaPracticaDTO> malasPracticas) {
		this.codigoFuente = codigoFuente;
		this.fechaAnalisis = fechaAnalisis;
		this.nombreUsuarioAutor = nombreUsuarioAutor;
		this.totalProblemas = totalProblemas;
		this.puntuacion = puntuacion;
		this.malasPracticas = malasPracticas;
	}

	/**
	 * Retorna el identificador único del análisis.
	 *
	 * @return id del análisis
	 */
	public long getId() {
	    return id;
	}

	/**
	 * Establece el identificador único del análisis.
	 *
	 * @param id identificador a asignar
	 */
	public void setId(long id) {
	    this.id = id;
	}

	/**
	 * Retorna el código fuente sometido a análisis.
	 *
	 * @return código fuente como cadena de texto
	 */
	public String getCodigoFuente() {
	    return codigoFuente;
	}

	/**
	 * Establece el código fuente a analizar.
	 *
	 * @param codigoFuente código fuente a asignar
	 */
	public void setCodigoFuente(String codigoFuente) {
	    this.codigoFuente = codigoFuente;
	}

	/**
	 * Retorna la fecha y hora en que se realizó el análisis.
	 *
	 * @return fecha y hora del análisis
	 */
	public LocalDateTime getFechaAnalisis() {
	    return fechaAnalisis;
	}

	/**
	 * Establece la fecha y hora del análisis.
	 *
	 * @param fechaAnalisis fecha y hora a asignar
	 */
	public void setFechaAnalisis(LocalDateTime fechaAnalisis) {
	    this.fechaAnalisis = fechaAnalisis;
	}

	/**
	 * Retorna el nombre de usuario del autor que envió el código.
	 *
	 * @return nombre de usuario del autor
	 */
	public String getNombreUsuarioAutor() {
	    return nombreUsuarioAutor;
	}

	/**
	 * Establece el nombre de usuario del autor del código.
	 *
	 * @param nombreUsuarioAutor nombre de usuario a asignar
	 */
	public void setNombreUsuarioAutor(String nombreUsuarioAutor) {
	    this.nombreUsuarioAutor = nombreUsuarioAutor;
	}

	/**
	 * Retorna el total de problemas detectados en el análisis.
	 *
	 * @return número total de problemas encontrados
	 */
	public int getTotalProblemas() {
	    return totalProblemas;
	}

	/**
	 * Establece el total de problemas detectados.
	 *
	 * @param totalProblemas cantidad de problemas a asignar
	 */
	public void setTotalProblemas(int totalProblemas) {
	    this.totalProblemas = totalProblemas;
	}

	/**
	 * Retorna la puntuación obtenida tras el análisis del código.
	 *
	 * @return puntuación del análisis
	 */
	public int getPuntuacion() {
	    return puntuacion;
	}

	/**
	 * Establece la puntuación del análisis.
	 *
	 * @param puntuacion puntuación a asignar
	 */
	public void setPuntuacion(int puntuacion) {
	    this.puntuacion = puntuacion;
	}

	/**
	 * Retorna la lista de malas prácticas identificadas en el código.
	 *
	 * @return lista de {@link MalaPracticaDTO} con las malas prácticas detectadas
	 */
	public List<MalaPracticaDTO> getMalasPracticas() {
	    return malasPracticas;
	}

	/**
	 * Establece la lista de malas prácticas del análisis.
	 *
	 * @param malasPracticas lista de {@link MalaPracticaDTO} a asignar
	 */
	public void setMalasPracticas(List<MalaPracticaDTO> malasPracticas) {
	    this.malasPracticas = malasPracticas;
	}
	
	/**
	 * Metodo toString() para la representacion de la informacion de la clase por consola.
	 * @return Retorna la informacion de la clase en formato String.
	 */
	@Override
	public String toString() {
		return "AnalisisDTO [id=" + id + ", codigoFuente=" + codigoFuente + ", fechaAnalisis=" + fechaAnalisis
				+ ", nombreUsuarioAutor=" + nombreUsuarioAutor + ", totalProblemas=" + totalProblemas + ", puntuacion="
				+ puntuacion + ", malasPracticas=" + malasPracticas + "]";
	}

	/**
	 * Retorna el codigo hash del conductor.
	 * @return Resultado entero que retorna el codigo hash de la clase.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(codigoFuente, fechaAnalisis, id, malasPracticas, nombreUsuarioAutor, puntuacion,
				totalProblemas);
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
		AnalisisDTO other = (AnalisisDTO) obj;
		return Objects.equals(codigoFuente, other.codigoFuente) && Objects.equals(fechaAnalisis, other.fechaAnalisis)
				&& id == other.id && Objects.equals(malasPracticas, other.malasPracticas)
				&& Objects.equals(nombreUsuarioAutor, other.nombreUsuarioAutor) && puntuacion == other.puntuacion
				&& totalProblemas == other.totalProblemas;
	}

}