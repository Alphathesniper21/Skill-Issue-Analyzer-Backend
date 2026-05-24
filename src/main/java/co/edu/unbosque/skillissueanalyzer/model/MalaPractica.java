/**
 * Paquete que contiene las clases entidad del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.model;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Clase que representa una mala practica de java detectada. No incluye la referencia al analisis para
 * evitar ciclos de serializacion.
 */
@Entity
@Table(name = "malas_practicas")
public class MalaPractica {

	/**
	 * Identificador (ID) de la mala practica.
	 */
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

    /**
     * Linea exacta donde se identifica la mala practica.
     */
    private int linea;

    /**
     * Tipo de mala practica.
     */
    private String tipo;

    /**
     * Descripcion de la mala practica.
     */
    private String descripcion;

    /**
     * Severidad de la mala practica detectada.
     */
    private String severidad; 

    /**
     * Sugerencia para solucionar la mala practica.
     */
    private String sugerencia;

    /**
     * Analisis realizada con respecto a la mala practica.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Analisis analisis;

    /**
     * Constructor vacio.
     */
    public MalaPractica() {
		// TODO Auto-generated constructor stub
	}

    /**
	 * Constructor completo.
	 * @param linea Linea donde se detecto la mala practica.
	 * @param tipo Tipo de mala practica.
	 * @param descripcion Descripcion de la mala practica.
	 * @param severidad Severidad de la mala practica detectada. 
	 * @param sugerencia Sugerencia para arreglar la mala practica.
	 * @param analisis Analisis realizada con respecto a la mala practica.
     */
    public MalaPractica(int linea, String tipo, String descripcion, String severidad, String sugerencia,
			Analisis analisis) {
		super();
		this.linea = linea;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.severidad = severidad;
		this.sugerencia = sugerencia;
		this.analisis = analisis;
	}

	/**
	 * Retorna el identificador unico de la mala practica.
	 *
	 * @return id de la mala practica
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador unico de la mala practica.
	 *
	 * @param id identificador a asignar
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Retorna el numero de linea del codigo fuente donde se detecto la mala practica.
	 *
	 * @return numero de linea
	 */
	public int getLinea() {
	    return linea;
	}

	/**
	 * Establece el numero de linea donde se detecto la mala practica.
	 *
	 * @param linea numero de linea a asignar
	 */
	public void setLinea(int linea) {
	    this.linea = linea;
	}

	/**
	 * Retorna el tipo de mala practica detectada.
	 *
	 * @return tipo de mala practica (por ejemplo, "NOMBRE", "ESTRUCTURA", "LOGICA")
	 */
	public String getTipo() {
	    return tipo;
	}

	/**
	 * Establece el tipo de mala practica detectada.
	 *
	 * @param tipo tipo de mala practica a asignar
	 */
	public void setTipo(String tipo) {
	    this.tipo = tipo;
	}

	/**
	 * Retorna la descripcion detallada de la mala practica detectada.
	 *
	 * @return descripcion de la mala practica
	 */
	public String getDescripcion() {
	    return descripcion;
	}

	/**
	 * Establece la descripcion de la mala practica detectada.
	 *
	 * @param descripcion descripcion a asignar
	 */
	public void setDescripcion(String descripcion) {
	    this.descripcion = descripcion;
	}

	/**
	 * Retorna el nivel de severidad de la mala practica.
	 *
	 * @return severidad (por ejemplo, "BAJA", "MEDIA", "ALTA")
	 */
	public String getSeveridad() {
	    return severidad;
	}

	/**
	 * Establece el nivel de severidad de la mala practica.
	 *
	 * @param severidad severidad a asignar
	 */
	public void setSeveridad(String severidad) {
	    this.severidad = severidad;
	}

	/**
	 * Retorna la sugerencia de correccion para la mala practica detectada.
	 *
	 * @return sugerencia de mejora
	 */
	public String getSugerencia() {
	    return sugerencia;
	}

	/**
	 * Establece la sugerencia de correccion para la mala practica.
	 *
	 * @param sugerencia sugerencia a asignar
	 */
	public void setSugerencia(String sugerencia) {
	    this.sugerencia = sugerencia;
	}
	
	/**
	 * Retorna el analisis al que pertenece esta mala practica.
	 *
	 * @return objeto {@link Analisis} asociado
	 */
	public Analisis getAnalisis() {
	    return analisis;
	}

	/**
	 * Establece el analisis al que pertenece esta mala practica.
	 *
	 * @param analisis objeto {@link Analisis} a asignar
	 */
	public void setAnalisis(Analisis analisis) {
	    this.analisis = analisis;
	}
	
	/**
	 * Metodo toString() para la representacion de la informacion de la clase por consola.
	 * @return Retorna la informacion de la clase en formato String.
	 */
	@Override
	public String toString() {
		return "MalaPractica [id=" + id + ", linea=" + linea + ", tipo=" + tipo + ", descripcion=" + descripcion
				+ ", severidad=" + severidad + ", sugerencia=" + sugerencia + ", analisis=" + analisis + "]";
	}

	/**
	 * Retorna el codigo hash del conductor.
	 * @return Resultado entero que retorna el codigo hash de la clase.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(analisis, descripcion, id, linea, severidad, sugerencia, tipo);
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
		MalaPractica other = (MalaPractica) obj;
		return Objects.equals(analisis, other.analisis) && Objects.equals(descripcion, other.descripcion)
				&& Objects.equals(id, other.id) && linea == other.linea && Objects.equals(severidad, other.severidad)
				&& Objects.equals(sugerencia, other.sugerencia) && Objects.equals(tipo, other.tipo);
	}

}
