/**
 * Paquete de seguridad del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import co.edu.unbosque.skillissueanalyzer.repository.UsuarioRepository;

/**
 * Implementacion de UserDetailsService para la carga de usuarios desde la base de datos
 * durante el proceso de autenticacion de spring security.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	/**
	 * Repositorio de usuarios para la consulta en base de datos.
	 */
    private final UsuarioRepository usuarioRepo;

    /**
     * Constructor de la implementacion del servicio de detalles de usuario.
     * @param usuarioRepo
     */
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    /**
     * Carga un usuario por su nombre de usuario para el proceso de autenticacion.
     *
     * @param username Nombre de usuario a buscar.
     * @return Detalles del usuario encontrado.
     * @throws UsernameNotFoundException si el usuario no existe en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                    "Usuario no encontrado: " + username));
    }
}
