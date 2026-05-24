/**
 * Paquete de las clases controller que permiten la gestion de los endpoints del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import co.edu.unbosque.skillissueanalyzer.dto.AuthResponseDTO;
import co.edu.unbosque.skillissueanalyzer.dto.LoginDTO;
import co.edu.unbosque.skillissueanalyzer.dto.RegistroDTO;
import co.edu.unbosque.skillissueanalyzer.model.RolUsuario;
import co.edu.unbosque.skillissueanalyzer.model.Usuario;
import co.edu.unbosque.skillissueanalyzer.security.JwtUtil;
import co.edu.unbosque.skillissueanalyzer.service.UsuarioService;

/**
 * Clase de prueba para el controlador de autenticacion.
 *
 * Esta clase contiene pruebas unitarias para verificar el correcto funcionamiento
 * de los endpoints del AuthController.
 */
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private Authentication authentication;

    /**
     * Inicializa los mocks antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba que el metodo login retorna un token JWT correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y el AuthResponseDTO con el token esperado.
     */
    @Test
    void testLogin() {
        LoginDTO loginDTO = new LoginDTO("admin", "Admin2026$");

        Usuario usuario = new Usuario();
        usuario.setUsername("admin");
        usuario.setNombre("Administrador del Sistema");
        usuario.setRol(RolUsuario.ADMIN);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(usuario);
        when(jwtUtil.generateAccessToken(usuario)).thenReturn("token.jwt.test");

        ResponseEntity<AuthResponseDTO> response = authController.login(loginDTO);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("token.jwt.test", response.getBody().getToken());
        assertEquals("admin", response.getBody().getUsername());
    }

    /**
     * Prueba que el metodo registro registra un usuario normal correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y el mensaje de exito para un usuario normal.
     */
    @Test
    void testRegistroUsuarioNormal() {
        RegistroDTO dto = new RegistroDTO();
        dto.setRol("USUARIO");
        doNothing().when(usuarioService).registrar(dto);

        ResponseEntity<String> response = authController.registro(dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Registro exitoso. Ya puedes iniciar sesión.", response.getBody());
    }

    /**
     * Prueba que el metodo registro registra un administrador correctamente.
     *
     * Verifica que el endpoint devuelve un ResponseEntity con estado 200
     * y el mensaje de solicitud pendiente para un administrador.
     */
    @Test
    void testRegistroAdministrador() {
        RegistroDTO dto = new RegistroDTO();
        dto.setRol("ADMINISTRADOR");
        doNothing().when(usuarioService).registrar(dto);

        ResponseEntity<String> response = authController.registro(dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Solicitud enviada. Un administrador debe aprobar tu cuenta.", response.getBody());
    }
}