/**
 * Paquete de seguridades del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

/**
 * Configuracion central de Spring Security.
 *
 *   Sin estado (stateless) — Spring Security no crea ni usa sesiones HTTP.
 *   JWT — autenticacion via Bearer token en el header Authorization.
 *   BCrypt — todas las contrasenas se almacenan con BCrypt (factor 10).
 *   Rutas publicas: /auth/** y Swagger.
 *   Rutas de admin: solo rol ADMINISTRADOR.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	/**
	 * Implementacion del servicio de carga de detalles de usuario para autenticacion.
	 */
	private final UserDetailsServiceImpl userDetailsService;
	
	/**
	 * Filtro JWT que intercepta y valida los tokens en cada peticion.
	 */
	private final JwtFilter jwtFilter;

	/**
     * Constructor de la configuracion de seguridad.
     *
     * @param userDetailsService Servicio de carga de detalles de usuario.
     * @param jwtFilter          Filtro JWT para validacion de tokens.
	 */
	public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtFilter jwtFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtFilter          = jwtFilter;
	}

	/**
	 * Cadena principal de filtros de seguridad. Configura CSRF, CORS, sesiones,
     * proveedor de autenticacion, reglas de acceso y el filtro JWT.
     *
     * @param http Objeto de configuracion de seguridad HTTP.
     * @return Cadena de filtros de seguridad configurada.
     * @throws Exception si ocurre un error durante la configuracion.
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())

			.cors(cors -> cors.configurationSource(corsConfigurationSource()))

			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.authenticationProvider(authenticationProvider())

			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/auth/**").permitAll()
				.requestMatchers(
					"/swagger-ui.html",
					"/swagger-ui/**",
					"/v3/api-docs/**",
					"/v3/api-docs.yaml").permitAll()

				.requestMatchers("/admin/**").hasRole("ADMIN")

				.anyRequest().authenticated()
			)

			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	/**
     * Configuracion CORS que permite peticiones desde cualquier origen.
     * Habilita los metodos HTTP necesarios para una API REST.
     *
     * @return Fuente de configuracion CORS registrada para todas las rutas.
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(List.of("*"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(false);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	/**
     * Proveedor de autenticacion basado en UserDetailsService y BCrypt.
     *
     * @return Proveedor de autenticacion configurado.
	 */
	@Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

	/**
	 * AuthenticationManager expuesto como bean para su inyeccion en los controladores
     * que requieren autenticacion manual.
     *
     * @param config Configuracion de autenticacion de Spring.
     * @return AuthenticationManager configurado.
	 */
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	/**
	 * Encoder de contrasenas con BCrypt.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}