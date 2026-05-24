/**
 * Paquete que contiene los servicios del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import co.edu.unbosque.skillissueanalyzer.dto.RegistroDTO;
import co.edu.unbosque.skillissueanalyzer.dto.UsuarioDTO;
import co.edu.unbosque.skillissueanalyzer.exception.ResourceNotFoundException;
import co.edu.unbosque.skillissueanalyzer.exception.ValidacionException;
import co.edu.unbosque.skillissueanalyzer.model.RolUsuario;
import co.edu.unbosque.skillissueanalyzer.model.Usuario;
import co.edu.unbosque.skillissueanalyzer.repository.UsuarioRepository;

/**
 * Clase de prueba para el servicio de usuarios.
 *
 * Esta clase contiene pruebas unitarias para verificar el correcto funcionamiento
 * de los metodos del UsuarioService.
 */
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper mapper;

    /**
     * Inicializa los mocks antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public RegistroDTO crearRegistroDTOValido() {
        RegistroDTO dto = new RegistroDTO();
        dto.setUsername("testuser");
        dto.setNombreCompleto("Test User");
        dto.setEmail("test@correo.com");
        dto.setContrasena("Password1");
        dto.setRol("USUARIO");
        return dto;
    }

    public Usuario crearUsuario() {
        Usuario u = new Usuario();
        u.setUsername("testuser");
        u.setNombre("Test User");
        u.setCorreo("test@correo.com");
        u.setPassword("encodedPassword");
        u.setRol(RolUsuario.USUARIO);
        u.setActivo(true);
        u.setFechaCreacion(LocalDateTime.now());
        return u;
    }

    /**
     * Prueba que registrar guarda un usuario normal correctamente.
     *
     * <p>Verifica que el repositorio recibe el usuario y lo persiste.
     */
    @Test
    void testRegistrarUsuarioNormal() {
        RegistroDTO dto = crearRegistroDTOValido();
        Usuario u = crearUsuario();

        when(usuarioRepo.existsByUsername("testuser")).thenReturn(false);
        when(usuarioRepo.existsByCorreo("test@correo.com")).thenReturn(false);
        when(mapper.map(dto, Usuario.class)).thenReturn(u);
        when(passwordEncoder.encode(dto.getContrasena())).thenReturn("encodedPassword");
        when(usuarioRepo.save(any(Usuario.class))).thenReturn(u);

        usuarioService.registrar(dto);

        verify(usuarioRepo).save(any(Usuario.class));
    }

    /**
     * Prueba que registrar un administrador asigna estado PENDIENTE y activo false.
     *
     * <p>Verifica que el usuario administrador queda inactivo hasta ser aprobado.
     */
    @Test
    void testRegistrarAdministrador() {
        RegistroDTO dto = crearRegistroDTOValido();
        dto.setRol("ADMINISTRADOR");
        Usuario u = crearUsuario();

        when(usuarioRepo.existsByUsername("testuser")).thenReturn(false);
        when(usuarioRepo.existsByCorreo("test@correo.com")).thenReturn(false);
        when(mapper.map(dto, Usuario.class)).thenReturn(u);
        when(passwordEncoder.encode(dto.getContrasena())).thenReturn("encodedPassword");
        when(usuarioRepo.save(any(Usuario.class))).thenReturn(u);

        usuarioService.registrar(dto);

        verify(usuarioRepo).save(any(Usuario.class));
    }

    /**
     * Prueba que registrar lanza ValidacionException si el username ya existe.
     */
    @Test
    void testRegistrarUsernameExistente() {
        RegistroDTO dto = crearRegistroDTOValido();
        when(usuarioRepo.existsByUsername("testuser")).thenReturn(true);

        assertThrows(ValidacionException.class, () -> usuarioService.registrar(dto));
        verify(usuarioRepo, never()).save(any());
    }

    /**
     * Prueba que registrar lanza ValidacionException si el correo ya existe.
     */
    @Test
    void testRegistrarCorreoExistente() {
        RegistroDTO dto = crearRegistroDTOValido();
        when(usuarioRepo.existsByUsername("testuser")).thenReturn(false);
        when(usuarioRepo.existsByCorreo("test@correo.com")).thenReturn(true);

        assertThrows(ValidacionException.class, () -> usuarioService.registrar(dto));
        verify(usuarioRepo, never()).save(any());
    }

    /**
     * Prueba que registrar lanza ValidacionException si el username es muy corto.
     */
    @Test
    void testRegistrarUsernameCorto() {
        RegistroDTO dto = crearRegistroDTOValido();
        dto.setUsername("ab");

        assertThrows(ValidacionException.class, () -> usuarioService.registrar(dto));
    }

    /**
     * Prueba que registrar lanza ValidacionException si la contrasena es debil.
     */
    @Test
    void testRegistrarContrasenaDebil() {
        RegistroDTO dto = crearRegistroDTOValido();
        dto.setContrasena("password");

        assertThrows(ValidacionException.class, () -> usuarioService.registrar(dto));
    }

    /**
     * Prueba que registrar lanza ValidacionException si el rol es invalido.
     */
    @Test
    void testRegistrarRolInvalido() {
        RegistroDTO dto = crearRegistroDTOValido();
        dto.setRol("SUPERADMIN");
        when(usuarioRepo.existsByUsername("testuser")).thenReturn(false);
        when(usuarioRepo.existsByCorreo("test@correo.com")).thenReturn(false);

        assertThrows(ValidacionException.class, () -> usuarioService.registrar(dto));
    }

    /**
     * Prueba que getTodos retorna la lista de todos los usuarios correctamente.
     */
    @Test
    void testGetTodos() {
        Usuario u = crearUsuario();
        UsuarioDTO dto = new UsuarioDTO();
        when(usuarioRepo.findAll()).thenReturn(List.of(u));
        when(mapper.map(u, UsuarioDTO.class)).thenReturn(dto);

        List<UsuarioDTO> result = usuarioService.getTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    /**
     * Prueba que getSolicitudesPendientes retorna solo las solicitudes pendientes.
     */
    @Test
    void testGetSolicitudesPendientes() {
        Usuario u = crearUsuario();
        u.setEstadoSolicitud("PENDIENTE");
        UsuarioDTO dto = new UsuarioDTO();
        when(usuarioRepo.findByEstadoSolicitud("PENDIENTE")).thenReturn(List.of(u));
        when(mapper.map(u, UsuarioDTO.class)).thenReturn(dto);

        List<UsuarioDTO> result = usuarioService.getSolicitudesPendientes();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    /**
     * Prueba que aprobarSolicitud aprueba correctamente una solicitud pendiente.
     */
    @Test
    void testAprobarSolicitud() {
        Usuario u = crearUsuario();
        u.setEstadoSolicitud("PENDIENTE");
        u.setActivo(false);
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(u));
        when(usuarioRepo.save(any(Usuario.class))).thenReturn(u);

        usuarioService.aprobarSolicitud(1L);

        verify(usuarioRepo).save(u);
        assertEquals("APROBADO", u.getEstadoSolicitud());
        assertEquals(true, u.isActivo());
    }

    /**
     * Prueba que aprobarSolicitud lanza ValidacionException si la solicitud ya fue procesada.
     */
    @Test
    void testAprobarSolicitudYaProcesada() {
        Usuario u = crearUsuario();
        u.setEstadoSolicitud("APROBADO");
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(u));

        assertThrows(ValidacionException.class, () -> usuarioService.aprobarSolicitud(1L));
    }

    /**
     * Prueba que aprobarSolicitud lanza ResourceNotFoundException si el usuario no existe.
     */
    @Test
    void testAprobarSolicitudNoEncontrado() {
        when(usuarioRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.aprobarSolicitud(99L));
    }

    /**
     * Prueba que rechazarSolicitud elimina el usuario correctamente.
     */
    @Test
    void testRechazarSolicitud() {
        Usuario u = crearUsuario();
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(u));
        doNothing().when(usuarioRepo).delete(u);

        usuarioService.rechazarSolicitud(1L);

        verify(usuarioRepo).delete(u);
    }

    /**
     * Prueba que rechazarSolicitud lanza ResourceNotFoundException si el usuario no existe.
     */
    @Test
    void testRechazarSolicitudNoEncontrado() {
        when(usuarioRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.rechazarSolicitud(99L));
    }

    /**
     * Prueba que eliminar elimina un usuario correctamente.
     */
    @Test
    void testEliminar() {
        Usuario u = crearUsuario();
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(u));
        doNothing().when(usuarioRepo).delete(u);

        usuarioService.eliminar(1L);

        verify(usuarioRepo).delete(u);
    }

    /**
     * Prueba que eliminar lanza ResourceNotFoundException si el usuario no existe.
     */
    @Test
    void testEliminarNoEncontrado() {
        when(usuarioRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.eliminar(99L));
    }

    /**
     * Prueba que cambiarEstado actualiza el estado del usuario correctamente.
     */
    @Test
    void testCambiarEstado() {
        Usuario u = crearUsuario();
        u.setActivo(true);
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(u));
        when(usuarioRepo.save(any(Usuario.class))).thenReturn(u);

        usuarioService.cambiarEstado(1L, false);

        verify(usuarioRepo).save(u);
        assertEquals(false, u.isActivo());
    }

    /**
     * Prueba que cambiarEstado lanza ResourceNotFoundException si el usuario no existe.
     */
    @Test
    void testCambiarEstadoNoEncontrado() {
        when(usuarioRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.cambiarEstado(99L, true));
    }
}