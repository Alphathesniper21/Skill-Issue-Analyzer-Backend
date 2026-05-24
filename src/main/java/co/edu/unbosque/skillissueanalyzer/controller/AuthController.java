/**
 * Paquete de las clases controller que permiten la gestion de los endpoints del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import co.edu.unbosque.skillissueanalyzer.dto.AuthResponseDTO;
import co.edu.unbosque.skillissueanalyzer.dto.LoginDTO;
import co.edu.unbosque.skillissueanalyzer.dto.RegistroDTO;
import co.edu.unbosque.skillissueanalyzer.model.Usuario;
import co.edu.unbosque.skillissueanalyzer.security.JwtUtil;
import co.edu.unbosque.skillissueanalyzer.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Clase controlador REST que permite la autenticacion de usuarios. Gestiona las
 * operaciones de inicio de sesion y registro.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticacion", description = "API publica para registro e inicio de sesion de usuarios")
public class AuthController {

	/**
	 * Atributo gestor de autenticacion que permite la validacion de credenciales de
	 * usuarios.
	 */
	private final AuthenticationManager authManager;
	
	/**
	 * Atributo de utilidad para las operaciones con tokens JWT (JSON Web Token).
	 */
	private final JwtUtil jwtUtil;
	
	/**
	 * Atributo de servicio para las operaciones de gestion de usuarios.
	 */
	private final UsuarioService usuarioService;

	/**
	 * Constructor completo del controlador de autenticacion.
	 * @param authManager Gestor de autenticacion.
	 * @param jwtUtil Utilidad para tokens JWT.
	 * @param usuarioService Servicio de usuarios.
	 */
	public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UsuarioService usuarioService) {
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
		this.usuarioService = usuarioService;
	}

	/**
	 * Metodo que permite el manejo de las solicitudes de inicio de sesion. Autentica al usuario y genera un token JWT si las credenciales
	 * ingresadas son validas.
	 * @param dto DTO que contiene las credenciales del usuario para su inicio de sesion.
	 * @return ResponseEntity con el token JWT y el rol del usuario si la autenticacion fue exitosa, de lo
	 * contrario retornara un error si falla.
	 */
	@PostMapping("/login")
	@Operation(
	        summary = "Iniciar sesion de usuario",
	        description = """
	            Autentica al usuario con sus credenciales y retorna un token JWT si son validas.

	            Acceso: Publico, no requiere autenticacion.

	            Paso a paso:
	            1. Envia tu nombre de usuario y contrasena en formato JSON.
	            2. Si las credenciales son correctas, recibiras un token JWT.
	            3. Usa ese token en el encabezado de futuras peticiones: Authorization: Bearer tu_token_jwt.

	            Nota: El token tiene un tiempo de expiracion limitado. Si expira, deberas iniciar sesion nuevamente.
	        """)
	    @ApiResponses(value = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "Inicio de sesion exitoso",
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = AuthResponseDTO.class),
	                examples = @ExampleObject(value = """
	                    {
	                      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
	                      "username": "juanito",
	                      "nombre": "Juan Perez",
	                      "rol": "USER"
	                    }
	                """))),
	        @ApiResponse(
	            responseCode = "401",
	            description = "Credenciales invalidas",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(value = "Nombre de usuario o contrasena invalidos")))
	    })
	public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO dto) {
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getContrasena()));
		Usuario usuario = (Usuario) auth.getPrincipal();
		String token = jwtUtil.generateAccessToken(usuario);

		return ResponseEntity
				.ok(new AuthResponseDTO(token, usuario.getUsername(), usuario.getNombre(), usuario.getRol().name()));
	}

	/**
	 * Metodo que permite el manejo de las solicitudes de registro de nuevos usuarios. Verifica si existe y 
	 * crea un nuevo usuario si esta disponible. De lo contrario, el registro no se hara.
	 * @param dto DTO con la informacion del nuevo usuario.
	 * @return Retorna un ResponseEntity con un mensaje de exito si el registro es exitoso, de lo contrario
	 * retorna un mensaje de falla.
	 */
	@PostMapping("/registro")
	@Operation(
	        summary = "Registrar un nuevo usuario",
	        description = """
	            Crea una nueva cuenta de usuario en el sistema.

	            Acceso: Publico, no requiere autenticacion.

	            Paso a paso:
	            1. Envia el nombre de usuario, contrasena y rol deseados en formato JSON.
	            2. Si el nombre de usuario esta disponible, se creara la cuenta.
	            3. Si el rol es ADMINISTRADOR, la cuenta quedara pendiente de aprobacion.
	            4. Si el rol es USER, podras iniciar sesion de inmediato.

	            Roles disponibles: USER, ADMINISTRADOR.
	        """)
	    @ApiResponses(value = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "Registro exitoso o solicitud enviada correctamente",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(value = "Registro exitoso. Ya puedes iniciar sesion."))),
	        @ApiResponse(
	            responseCode = "409",
	            description = "El nombre de usuario ya existe",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(value = "El nombre de usuario ya esta en uso"))),
	        @ApiResponse(
	            responseCode = "400",
	            description = "Datos invalidos o error en el registro",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(value = "Error al registrar el usuario")))
	    })
	public ResponseEntity<String> registro(@RequestBody RegistroDTO dto) {
		usuarioService.registrar(dto);
		boolean esAdmin = "ADMINISTRADOR".equalsIgnoreCase(dto.getRol());
		String msg = esAdmin ? "Solicitud enviada. Un administrador debe aprobar tu cuenta."
				: "Registro exitoso. Ya puedes iniciar sesión.";
		return ResponseEntity.ok(msg);
	}
}
