/**
 * Paquete que contiene los repositorios del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import co.edu.unbosque.skillissueanalyzer.model.Analisis;

/**
 * Repositorio de la entidad Analisis.
 * Gestiona la persistencia y consulta de los analisis de codigo.
 */
public interface AnalisisRepository extends CrudRepository<Analisis, Long> {

	/**
	 * Busca todos los analisis de un usuario ordenados por fecha descendente.
	 *
	 * @param nombreUsuarioAutor El nombre de usuario del autor.
	 * @return Lista de analisis del usuario, del mas reciente al mas antiguo.
	 */
	List<Analisis> findByNombreUsuarioAutorOrderByFechaAnalisisDesc(String nombreUsuarioAutor);

	/**
	 * Calcula el promedio de problemas detectados en todos los analisis.
	 *
	 * @return El promedio de totalProblemas, o 0 si no hay analisis.
	 */
	@Query("SELECT COALESCE(AVG(a.totalProblemas), 0) FROM Analisis a")
	double promedioProblemas();

}