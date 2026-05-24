/**
 * Paquete de las clases controller que permiten la gestion de los endpoints del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import co.edu.unbosque.skillissueanalyzer.dto.AnalisisDTO;
import co.edu.unbosque.skillissueanalyzer.service.AnalisisService;
import java.util.List;

/**
 * Clase controlador REST para la gestion de analisis de proyectos adjuntados.
 * Proporciona los endpoints necesarios para la administracion de analisis de los proyectos
 * java.
 */
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/analisis")
public class AnalisisController {

	/**
	 * Atributo de servicio para las operaciones de gestion de analisis.
	 */
	@Autowired
    private AnalisisService analisisService;

    /** Metodo que recibe un archivo .zip extrae los archivos .java y llama a Claude 
     * y guarda el resultado.
     * @return Retorna un ResponseEntity del estado de la operacion.
     */
    @PostMapping("/zip")
    public ResponseEntity<AnalisisDTO> analizarZip(
            @RequestParam("archivo") MultipartFile archivo) {
        return ResponseEntity.ok(analisisService.analizarZip(archivo));
    }

    /** Metodo de obtencion del historial de analisis de un usuario
     * autenticado.
     * @return Retorna una ResponseEntity con la lista de analisis de proyectos 
     * adjuntados por el usuario.
     */
    @GetMapping
    public ResponseEntity<List<AnalisisDTO>> getMisAnalisis() {
        return ResponseEntity.ok(analisisService.getMisAnalisis());
    }

    /** Metodo de obtencion de un analisis de proyecto mediante su 
     * identificador (ID). 
     * @return Retorna un ResponseEntity que indica el estado de la operacion.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnalisisDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(analisisService.getById(id));
    }

    /** Metodo de eliminacion de un analisis mediante el uso de su identificador (ID).
     * @return Retorna un ResponseEntity que indica un mensaje de exito o de error.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        analisisService.eliminar(id);
        return ResponseEntity.ok("Análisis eliminado correctamente.");
    }
    
    /** Metodo que recibe una URL de un repositorio de GitHub y analiza los archivos
     * .java del repositorio.
     */
    @PostMapping("/github")
    public ResponseEntity<AnalisisDTO> analizarRepo(
            @RequestParam("repoUrl") String repoUrl) {
        return ResponseEntity.ok(analisisService.analizarRepo(repoUrl));
    }
}
