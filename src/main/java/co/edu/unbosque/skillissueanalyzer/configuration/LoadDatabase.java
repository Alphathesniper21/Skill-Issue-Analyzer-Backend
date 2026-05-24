/**
 * Paquete de configuracion de aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.configuration;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.skillissueanalyzer.model.RolUsuario;
import co.edu.unbosque.skillissueanalyzer.model.Usuario;
import co.edu.unbosque.skillissueanalyzer.repository.UsuarioRepository;

/**
 * Clase de configuracion para la carga de datos iniciales en la base de datos. Permite
 * la creacion de usuarios predeterminados de aplicativo, como pueden ser: Usuario administrador 
 * usuario normal si estos no existen previamente.
 */
@Configuration
public class LoadDatabase {

	/**
	 * Atributo Logger para el registro de mensajes durante el proceso de carga de datos.
	 */
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	/**
	 * Se inicializa la base de datos con los usuarios predeterminados. Crea un usuario administrador 
	 * y un usuario normal, si estos no llegan a existir.
	 * @param usuarioRepository Repositorio de usuarios para acceder a la base de datos.
	 * @param passwordEncoder Codificador de contrasenias que permiten encriptarlas para mayor
	 * proteccion.
	 * @return Se retorna un CommandLineRunner que se ejecuta al iniciar el aplicativo.
	 */
	@Bean
	CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {

			if (!usuarioRepository.existsByUsername("admin")) {
				Usuario admin = new Usuario(
						"admin",
						"Administrador del Sistema",
						"admin@skilissue.com",
						passwordEncoder.encode("Admin2026$"),
						RolUsuario.ADMIN,
						true,
						"APROBADO",
						LocalDateTime.now()
						);
				usuarioRepository.save(admin);
				log.info("Usuario admin creado — username: admin | password: Admin2026$");
			} else {
				log.info("Usuario admin ya existe, no se crea de nuevo.");
			}

			if (!usuarioRepository.existsByUsername("usuario")) {
				Usuario user = new Usuario(
						"usuario",
						"Usuario de Prueba",
						"usuario@skilissue.com",
						passwordEncoder.encode("Usuario2026$"),
						RolUsuario.USUARIO,
						true,
						null,
						LocalDateTime.now()
						);
				usuarioRepository.save(user);
				log.info("Usuario de prueba creado — username: usuario | password: Usuario2026$");
			} else {
				log.info("Usuario de prueba ya existe, no se crea de nuevo.");
			}
		};
	}
}