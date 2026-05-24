/**
 * Paquete que contiene las clases entidad del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import co.edu.unbosque.skillissueanalyzer.dto.MalaPracticaDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase completa de un analisis con sus malas practicas.
 */
@Entity
@Table(name = "analisis")
public class Analisis {

	/**
	 * Identificador unico del analisis.
	 */
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    /**
     * Codigo fuente del analisis.
     */
    @Column(columnDefinition = "LONGTEXT")
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
     * Numero tota lde problemas detectados.
     */
    private int totalProblemas;

    /**
     * Puntuacion del codigo analizado.
     */
    private int puntuacion; 

    @OneToMany(mappedBy = "analisis", cascade = CascadeType.ALL, orphanRemoval = true, fetch = jakarta.persistence.FetchType.EAGER)
    private List<MalaPractica> malasPracticas;

    /**
     * Constructor vacio.
     */
    public Analisis() {
    	malasPracticas = new ArrayList<>();
	}

    /**
     * Constructor con parametros.
     * @param codigoFuente Codigo fuente del analisis.
     * @param autorUsername Nombre de usuario de autor.
     */
    public Analisis(String codigoFuente, String nombreUsuarioAutor) {
        this.codigoFuente  = codigoFuente;
        this.nombreUsuarioAutor = nombreUsuarioAutor;
        this.fechaAnalisis = LocalDateTime.now();
    }

    /**
     * Retorna el identificador único del análisis.
	 *
	 * @return id del análisis
     */
	public Long getId() {
		return id;
	}
	
	/**
	 * Establece el identificador único del análisis.
	 *
	 * @param id identificador a asignar
	 */
	public void setId(Long id) {
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
	 * 	 * Retorna la lista de malas prácticas identificadas en el código.
	 *
	 * @return lista de {@link MalaPracticaDTO} con las malas prácticas detectadas.
	 */
	public List<MalaPractica> getMalasPracticas() {
		return malasPracticas;
	}

	/**
	 * Establece la lista de malas prácticas del análisis.
	 *
	 * @param malasPracticas lista de {@link MalaPracticaDTO} a asignar
	 */
	public void setMalasPracticas(List<MalaPractica> malasPracticas) {
		this.malasPracticas = malasPracticas;
	}
	
	/**
	 * Metodo toString() para la representacion de la informacion de la clase por consola.
	 * @return Retorna la informacion de la clase en formato String.
	 */
	@Override
	public String toString() {
		return "Analisis [id=" + id + ", codigoFuente=" + codigoFuente + ", fechaAnalisis=" + fechaAnalisis
				+ ", nombreUsuarioAutor=" + nombreUsuarioAutor + ", totalProblemas=" + totalProblemas + ", puntuacion="
				+ puntuacion + ", malasPracticas=" + malasPracticas + "]";
	}

	/**
	 * Retorna el codigo hash del conductor.
	 * @return Resultado entero que retorna el codigo hash de la clase.
	 */
	@Override
	public int hashCode() {
	    return Objects.hash(codigoFuente, fechaAnalisis, id, nombreUsuarioAutor, puntuacion, totalProblemas);
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
	    Analisis other = (Analisis) obj;
	    return puntuacion == other.puntuacion &&
	           totalProblemas == other.totalProblemas &&
	           Objects.equals(codigoFuente, other.codigoFuente) &&
	           Objects.equals(fechaAnalisis, other.fechaAnalisis) &&
	           Objects.equals(id, other.id) &&
	           Objects.equals(nombreUsuarioAutor, other.nombreUsuarioAutor);
	}

}
