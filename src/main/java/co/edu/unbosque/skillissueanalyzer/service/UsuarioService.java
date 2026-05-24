/**
 * Paquete que contiene los servicios del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import co.edu.unbosque.skillissueanalyzer.dto.RegistroDTO;
import co.edu.unbosque.skillissueanalyzer.dto.UsuarioDTO;
import co.edu.unbosque.skillissueanalyzer.exception.ResourceNotFoundException;
import co.edu.unbosque.skillissueanalyzer.exception.ValidacionException;
import co.edu.unbosque.skillissueanalyzer.model.Usuario;
import co.edu.unbosque.skillissueanalyzer.model.RolUsuario; 
import co.edu.unbosque.skillissueanalyzer.repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones de negocio relacionadas con los usuarios
 * del aplicativo. Permite el registro, consulta, aprobacion, rechazo, eliminacion
 * y cambio de estado de usuarios.
 */
@Service
public class UsuarioService {

	/**
	 * Formateador de fechas.
	 */
    private static final DateTimeFormatter FMT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Repositorio de usuarios.
     */
    @Autowired
    private UsuarioRepository usuarioRepo;
    
    /**
     * Codificador de contrasenias para encriptacion.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Mapeador de objetos para la conversion de entidades y clases DTO.
     */
    @Autowired
    private ModelMapper mapper;

    /**
     * Registra un nuevo usuario en el sistema. Si el rol es ADMINISTRADOR,
     * la cuenta queda inactiva y pendiente de aprobacion.
     *
     * @param dto DTO con la informacion del nuevo usuario a registrar.
     */
    public void registrar(RegistroDTO dto) {
        validarRegistro(dto);

        boolean esAdmin = "ADMINISTRADOR".equalsIgnoreCase(dto.getRol());

        Usuario u = mapper.map(dto, Usuario.class);
        u.setPassword(passwordEncoder.encode(dto.getContrasena()));
        u.setRol(esAdmin ? RolUsuario.ADMIN : RolUsuario.USUARIO);
        u.setActivo(!esAdmin);
        u.setEstadoSolicitud(esAdmin ? "PENDIENTE" : null);
        u.setFechaCreacion(LocalDateTime.now());

        usuarioRepo.save(u);
    }

    /**
     * Retorna la lista de todos los usuarios registrados en el sistema.
     *
     * @return Lista de {@link UsuarioDTO} con todos los usuarios registrados.
     */
    public List<UsuarioDTO> getTodos() {
    	
        List<Usuario> entityList = (List<Usuario>) usuarioRepo.findAll();
        List<UsuarioDTO> dtoList = new ArrayList<>();

        entityList.forEach(entidad -> dtoList.add(mapper.map(entidad, UsuarioDTO.class)));

        return dtoList;
    }

    /**
     * Retorna la lista de usuarios con solicitudes de administrador pendientes de aprobacion.
     *
     * @return Lista de {@link UsuarioDTO} con las solicitudes pendientes.
     */
    public List<UsuarioDTO> getSolicitudesPendientes() {
    	
        List<Usuario> entityList = usuarioRepo.findByEstadoSolicitud("PENDIENTE");
        List<UsuarioDTO> dtoList = new ArrayList<>();

        entityList.forEach(entidad -> dtoList.add(mapper.map(entidad, UsuarioDTO.class)));

        return dtoList;
    }

    /**
     * Aprueba la solicitud de acceso de un usuario administrador pendiente.
     * Activa la cuenta y cambia el estado de la solicitud a APROBADO.
     *
     * @param id ID del usuario cuya solicitud se desea aprobar.
     */
    public void aprobarSolicitud(Long id) {
    	
        Optional<Usuario> encontrado = usuarioRepo.findById(id);

        if (encontrado.isPresent()) {
            Usuario u = encontrado.get();
            if (!"PENDIENTE".equals(u.getEstadoSolicitud())) {
                throw new ValidacionException("Esta solicitud ya fue procesada.");
            }
            u.setActivo(true);
            u.setEstadoSolicitud("APROBADO");
            usuarioRepo.save(u);
        } else {
            throw new ResourceNotFoundException("Usuario con id " + id + " no encontrado.");
        }
    }
    
    /**
     * Rechaza y elimina la solicitud de acceso de un usuario administrador pendiente.
     *
     * @param id ID del usuario cuya solicitud se desea rechazar.
     */
    public void rechazarSolicitud(Long id) {
    	
        Optional<Usuario> encontrado = usuarioRepo.findById(id);

        if (encontrado.isPresent()) {
            usuarioRepo.delete(encontrado.get());
        } else {
            throw new ResourceNotFoundException("Usuario con id " + id + " no encontrado.");
        }
    }

    /**
     * Elimina un usuario del sistema mediante su identificador unico.
     *
     * @param id ID del usuario a eliminar.
     */
    public void eliminar(Long id) {
    	
        Optional<Usuario> encontrado = usuarioRepo.findById(id);

        if (encontrado.isPresent()) {
            usuarioRepo.delete(encontrado.get());
        } else {
            throw new ResourceNotFoundException("Usuario con id " + id + " no encontrado.");
        }
    }

    /**
     * Cambia el estado de activacion de un usuario en el sistema.
     *
     * @param id     ID del usuario cuyo estado se desea cambiar.
     * @param activo {@code true} para activar el usuario,para desactivarlo.
     */
    public void cambiarEstado(Long id, boolean activo) {
    	
        Optional<Usuario> encontrado = usuarioRepo.findById(id);

        if (encontrado.isPresent()) {
            Usuario u = encontrado.get();
            u.setActivo(activo);
            usuarioRepo.save(u);
        } else {
            throw new ResourceNotFoundException("Usuario con id " + id + " no encontrado.");
        }
    }

    /**
     * Valida los datos del DTO de registro antes de persistir el usuario.
     * Verifica formato de username, nombre completo, correo, contrasena y rol.
     *
     * @param dto DTO con los datos del usuario a validar.
     */
    public void validarRegistro(RegistroDTO dto) {
        if (dto.getUsername() == null || dto.getUsername().trim().length() < 4) {
            throw new ValidacionException("El usuario debe tener al menos 4 caracteres.");
        }
        if (!dto.getUsername().trim().matches("[a-zA-Z0-9._\\-]+")) {
            throw new ValidacionException(
                "El usuario solo puede contener letras, numeros, puntos, guiones y guiones bajos.");
        }
        if (dto.getNombreCompleto() == null || !dto.getNombreCompleto().trim().matches("[\\p{L} \\-]+")) {
            throw new ValidacionException(
                "El nombre completo solo puede contener letras, espacios y guiones.");
        }
        if (dto.getEmail() == null || !dto.getEmail().trim().matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$")) {
            throw new ValidacionException("El correo no tiene un formato valido.");
        }
        if (dto.getContrasena() == null || dto.getContrasena().length() < 8
            || !dto.getContrasena().matches(".*[A-Z].*")
            || !dto.getContrasena().matches(".*[a-z].*")
            || !dto.getContrasena().matches(".*[0-9].*")) {
            throw new ValidacionException(
                "La contraseña debe tener al menos 8 caracteres, una mayuscula, una minuscula y un numero.");
        }
        if (usuarioRepo.existsByUsername(dto.getUsername().trim())) {
            throw new ValidacionException("El nombre de usuario ya esta en uso.");
        }
        if (usuarioRepo.existsByCorreo(dto.getEmail().trim())) { // Corregido: getCorreo()
            throw new ValidacionException("El correo electronico ya esta registrado.");
        }
        if (dto.getRol() == null ||
            (!dto.getRol().equalsIgnoreCase("USUARIO") &&
             !dto.getRol().equalsIgnoreCase("ADMINISTRADOR"))) {
            throw new ValidacionException("Rol invalido. Debe ser USUARIO o ADMINISTRADOR.");
        }
    }

}