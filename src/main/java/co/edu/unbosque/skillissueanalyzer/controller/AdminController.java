/**
 * Paquete de las clases controller que permiten la gestion de los endpoints del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import co.edu.unbosque.skillissueanalyzer.dto.AnalisisDTO;
import co.edu.unbosque.skillissueanalyzer.dto.CambioEstadoDTO;
import co.edu.unbosque.skillissueanalyzer.dto.EstadisticasAdminDTO;
import co.edu.unbosque.skillissueanalyzer.dto.UsuarioDTO;
import co.edu.unbosque.skillissueanalyzer.repository.AnalisisRepository;
import co.edu.unbosque.skillissueanalyzer.repository.UsuarioRepository;
import co.edu.unbosque.skillissueanalyzer.service.AnalisisService;
import co.edu.unbosque.skillissueanalyzer.service.UsuarioService;
import java.util.List;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

/**
 * Clase controlador REST para la gestion de usuarios administradores.
 * Proporciona los endpoints necesarios para la administracion de los usuarios
 * registrados y solicitudes.
 */
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Administracion", description = "API exclusiva para administradores: gestion de usuarios, solicitudes, analisis y estadisticas")
public class AdminController {

	/**
	 * Atributo de servicio para las operaciones de gestion de los usuarios.
	 */
	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Atributo de servicio para las operaciones de gestion de analisis.
	 */
	@Autowired
	private AnalisisService analisisService;

	/**
	 * Atributo de repositorio de usuarios.
	 */
	@Autowired
	private UsuarioRepository usuarioRepo;

	/**
	 * Atributo de repositorios de analisis.
	 */
	@Autowired
	private AnalisisRepository analisisRepo;

	/**
	 * Metodo de obtencion de todos los usuarios registrados.
	 * @return Retorna una lista de todos los usuarios registrados.
	 */
	@Operation(
			summary = "Obtener todos los usuarios",
			description = """
					    Retorna la lista completa de usuarios registrados en el sistema.

					    **Acceso:** Solo administradores.

					    **Uso típico:** Visualizar el panel de gestión de usuarios.
					""")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Lista de usuarios obtenida exitosamente",
					content = @Content(
							mediaType = "application/json",
							examples = @ExampleObject(value = """
									    [
									      { "id": 1, "username": "juanito", "activo": true },
									      { "id": 2, "username": "maria99", "activo": false }
									    ]
									"""))),
			@ApiResponse(responseCode = "403", description = "Acceso denegado — se requiere rol ADMIN",
			content = @Content(mediaType = "application/json",
			examples = @ExampleObject(value = "Acceso denegado")))
	})
	@GetMapping("/usuarios")
	public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
		return ResponseEntity.ok(usuarioService.getTodos());
	}

	/**
	 * Metodo de eliminacion de usuarios mediante su identificador (ID).
	 * @param id ID del usuario a eliminar.
	 * @return Se retorna un ResponseEntity que indica el estado de la operacion con mensaje de exito
	 * o error.
	 */
	@DeleteMapping("/usuarios/{id}")
	@Operation(
			summary = "Eliminar un usuario por ID",
			description = """
					    Elimina permanentemente un usuario del sistema usando su identificador.

					    Acceso: Solo administradores.

					    Advertencia: Esta accion es irreversible.
					""")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Usuario eliminado correctamente",
					content = @Content(mediaType = "application/json",
					examples = @ExampleObject(value = "Usuario eliminado."))),
			@ApiResponse(
					responseCode = "404",
					description = "Usuario no encontrado",
					content = @Content(mediaType = "application/json",
					examples = @ExampleObject(value = "Usuario no encontrado"))),
			@ApiResponse(
					responseCode = "403",
					description = "Acceso denegado - se requiere rol ADMIN",
					content = @Content(mediaType = "application/json",
					examples = @ExampleObject(value = "Acceso denegado")))
	})
	public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
		usuarioService.eliminar(id);
		return ResponseEntity.ok("Usuario eliminado.");
	}

	/**
	 * Metodo de cambio de estado de usuario.
	 * @param id ID del usuario que se busca cambiar su estado.
	 * @param dto DTO del usuario que se busca cambiar su estado.
	 * @return Retorna un ResponseEntity que indica el estado de la operacion con mensaje de exito o de 
	 * error.
	 */
	@PatchMapping("/usuarios/{id}/estado")
	@Operation(
			summary = "Cambiar estado activo/inactivo de un usuario",
			description = """
					    Activa o desactiva la cuenta de un usuario mediante su ID.

					    Acceso: Solo administradores.

					    Uso tipico: Bloquear temporalmente un usuario sin eliminarlo del sistema.
					""")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Estado del usuario actualizado correctamente",
					content = @Content(mediaType = "application/json",
					examples = @ExampleObject(value = "Estado actualizado."))),
			@ApiResponse(
					responseCode = "404",
					description = "Usuario no encontrado",
					content = @Content(mediaType = "application/json",
					examples = @ExampleObject(value = "Usuario no encontrado"))),
			@ApiResponse(
					responseCode = "403",
					description = "Acceso denegado - se requiere rol ADMIN",
					content = @Content(mediaType = "application/json",
					examples = @ExampleObject(value = "Acceso denegado")))
	})
	public ResponseEntity<String> cambiarEstado(@PathVariable Long id, @RequestBody CambioEstadoDTO dto) {
		usuarioService.cambiarEstado(id, dto.isActivo());
		return ResponseEntity.ok("Estado actualizado.");
	}

	/**
	 * Metodo de obtencion de todas las solicitudes mandadas por el usuario. 
	 * @return Retorna una lista de todas las solicitudes pendientes por los usuarios.
	 */
	@GetMapping("/solicitudes")
	@Operation(
			summary = "Obtener solicitudes pendientes",
			description = """
					    Retorna la lista de usuarios cuya solicitud de acceso esta pendiente de revision.

					    Acceso: Solo administradores.

					    Uso tipico: Panel de aprobacion de nuevas cuentas.
					""")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Lista de solicitudes pendientes obtenida exitosamente",
					content = @Content(mediaType = "application/json",
					examples = @ExampleObject(value = """
							    [
							      { "id": 5, "username": "nuevo_user", "estadoSolicitud": "PENDIENTE" }
							    ]
							"""))),
			@ApiResponse(
					responseCode = "403",
					description = "Acceso denegado - se requiere rol ADMIN",
					content = @Content(mediaType = "application/json",
					examples = @ExampleObject(value = "Acceso denegado")))
	})
	public ResponseEntity<List<UsuarioDTO>> getSolicitudesPendientes() {
		return ResponseEntity.ok(usuarioService.getSolicitudesPendientes());
	}

	/**
	 * Metodo de aprobacion de solicitudes mandadas por un usuario.
	 * @param id ID del usuario que envio su solicitud.
	 * @return Retorna un ResponseEntity que indica el estado de la operacion dando un mensaje de aprobacion o de
	 * error.
	 */
	@PostMapping("/solicitudes/{id}/aprobar")
	@Operation(
		    summary = "Aprobar solicitud de un usuario",
		    description = """
		        Aprueba la solicitud de acceso de un usuario, habilitando su cuenta en el sistema.

		        Acceso: Solo administradores.

		        Resultado: El usuario podra iniciar sesion inmediatamente tras la aprobacion.
		    """)
		@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Solicitud aprobada exitosamente",
		        content = @Content(mediaType = "application/json",
		            examples = @ExampleObject(value = "Solicitud aprobada. El usuario ya puede acceder."))),
		    @ApiResponse(
		        responseCode = "404",
		        description = "Solicitud o usuario no encontrado",
		        content = @Content(mediaType = "application/json",
		            examples = @ExampleObject(value = "Usuario no encontrado"))),
		    @ApiResponse(
		        responseCode = "403",
		        description = "Acceso denegado - se requiere rol ADMIN",
		        content = @Content(mediaType = "application/json",
		            examples = @ExampleObject(value = "Acceso denegado")))
		})
	public ResponseEntity<String> aprobar(@PathVariable Long id) {
		usuarioService.aprobarSolicitud(id);
		return ResponseEntity.ok("Solicitud aprobada. El usuario ya puede acceder.");
	}

	/**
	 * Metodo de rechazo de solicitudes mandadas por un usuario.
	 * @param id ID del usuario que envio la solicitud.
	 * @return Retorna un ResponseEntity que indica el estado de la operacion dando un mensaje de rechazo
	 * a la solicitud o error.
	 */
	@PostMapping("/solicitudes/{id}/rechazar")
	@Operation(
		    summary = "Rechazar solicitud de un usuario",
		    description = """
		        Rechaza y elimina la solicitud de acceso de un usuario.

		        Acceso: Solo administradores.

		        Nota: El registro del usuario sera eliminado del sistema tras el rechazo.
		    """)
		@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Solicitud rechazada y eliminada correctamente",
		        content = @Content(mediaType = "application/json",
		            examples = @ExampleObject(value = "Solicitud rechazada y eliminada."))),
		    @ApiResponse(
		        responseCode = "404",
		        description = "Solicitud o usuario no encontrado",
		        content = @Content(mediaType = "application/json",
		            examples = @ExampleObject(value = "Usuario no encontrado"))),
		    @ApiResponse(
		        responseCode = "403",
		        description = "Acceso denegado - se requiere rol ADMIN",
		        content = @Content(mediaType = "application/json",
		            examples = @ExampleObject(value = "Acceso denegado")))
		})
	public ResponseEntity<String> rechazar(@PathVariable Long id) {
		usuarioService.rechazarSolicitud(id);
		return ResponseEntity.ok("Solicitud rechazada y eliminada.");
	}

	/**
	 * Metodo de de obtencion de todos los analisis.
	 * @return Retorna un ResponseEntity que contiene una lista de todos los 
	 * analisis.
	 */
	@GetMapping("/analisis")
	@Operation(
			summary = "Obtener todos los analisis",
			description = """
					    Retorna la lista completa de analisis realizados por todos los usuarios del sistema.

					    Acceso: Solo administradores.

					    Uso tipico: Auditoria y revision global de analisis generados.
					""")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Lista de analisis obtenida exitosamente",
					content = @Content(mediaType = "application/json",
					examples = @ExampleObject(value = """
							    [
							      { "id": 1, "usuarioId": 2, "resultado": "skill issue", "problemas": 3 }
							    ]
							"""))),
			@ApiResponse(
					responseCode = "403",
					description = "Acceso denegado - se requiere rol ADMIN",
					content = @Content(mediaType = "application/json",
					examples = @ExampleObject(value = "Acceso denegado")))
	})
	public ResponseEntity<List<AnalisisDTO>> getTodosAnalisis() {
		return ResponseEntity.ok(analisisService.getTodos());
	}

	/**
	 * Metodo de obtencion de todas las estadisticas (Total de usuarios, analisis, solicitudes
	 * y promedio de los problemas).
	 * @return Retorna una lista con las estadisticas solicitadas.
	 */
	@GetMapping("/estadisticas")
	@Operation(
			summary = "Obtener estadisticas generales del sistema",
			description = """
					    Retorna un resumen estadistico del sistema incluyendo totales y promedios.

					    Acceso: Solo administradores.

					    Datos incluidos:
					    - Total de usuarios registrados
					    - Total de analisis realizados
					    - Solicitudes pendientes de aprobacion
					    - Promedio de problemas detectados por analisis
					""")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Estadisticas obtenidas exitosamente",
					content = @Content(mediaType = "application/json",
					examples = @ExampleObject(value = """
							    {
							      "totalUsuarios": 42,
							      "totalAnalisis": 158,
							      "solicitudesPendientes": 5,
							      "promedioProblemas": 2.7
							    }
							"""))),
			@ApiResponse(
					responseCode = "403",
					description = "Acceso denegado - se requiere rol ADMIN",
					content = @Content(mediaType = "application/json",
					examples = @ExampleObject(value = "Acceso denegado")))
	})
	public ResponseEntity<EstadisticasAdminDTO> getEstadisticas() {
		long totalUsuarios = usuarioRepo.count();
		long totalAnalisis = analisisRepo.count();
		long solicitudesPendientes = usuarioRepo.findByEstadoSolicitud("PENDIENTE").size();
		double promedioProblemas = analisisRepo.promedioProblemas();

		return ResponseEntity
				.ok(new EstadisticasAdminDTO(totalUsuarios, totalAnalisis, solicitudesPendientes, promedioProblemas));
	}
}
