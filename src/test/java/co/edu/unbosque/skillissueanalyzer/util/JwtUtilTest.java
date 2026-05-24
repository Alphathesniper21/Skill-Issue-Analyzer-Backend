/**
 * Paquete de utilidades del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import co.edu.unbosque.skillissueanalyzer.security.JwtUtil;

/**
 * Clase de prueba para la utilidad de tokens JWT.
 *
 * Esta clase contiene pruebas unitarias para verificar el correcto funcionamiento
 * de la generacion, validacion y extraccion de claims de tokens JWT.
 */
class JwtUtilTest {

    private JwtUtil jwtUtil;

    @Mock
    private UserDetails userDetails;

    /**
     * Inicializa el JwtUtil con valores de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil();

        ReflectionTestUtils.setField(jwtUtil, "secretBase64",
                "dGVzdHNlY3JldGtleXRlc3RzZWNyZXRrZXl0ZXN0c2Vj");
        ReflectionTestUtils.setField(jwtUtil, "accessTokenExpirationMs", 86400000L);
        ReflectionTestUtils.setField(jwtUtil, "refreshTokenExpirationMs", 604800000L);

        jwtUtil.init();
    }

    /**
     * Prueba que generateAccessToken genera un token no nulo.
     *
     * Verifica que el metodo retorna un token JWT valido para un usuario autenticado.
     */
    @Test
    void testGenerateAccessToken() {
        when(userDetails.getUsername()).thenReturn("admin");
        when(userDetails.getAuthorities()).thenAnswer(i ->
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        String token = jwtUtil.generateAccessToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    /**
     * Prueba que generateRefreshToken genera un token no nulo.
     *
     * Verifica que el metodo retorna un refresh token JWT valido.
     */
    @Test
    void testGenerateRefreshToken() {
        when(userDetails.getUsername()).thenReturn("admin");
        when(userDetails.getAuthorities()).thenAnswer(i ->
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        String token = jwtUtil.generateRefreshToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    /**
     * Prueba que extractUsername retorna el username correcto del token.
     *
     * Verifica que el subject del token corresponde al username del usuario.
     */
    @Test
    void testExtractUsername() {
        when(userDetails.getUsername()).thenReturn("admin");
        when(userDetails.getAuthorities()).thenAnswer(i ->
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        String token = jwtUtil.generateAccessToken(userDetails);
        String username = jwtUtil.extractUsername(token);

        assertEquals("admin", username);
    }

    /**
     * Prueba que extractRol retorna el rol correcto del token.
     *
     * Verifica que el claim de rol en el token corresponde al rol del usuario
     * sin el prefijo ROLE_.
     */
    @Test
    void testExtractRol() {
        when(userDetails.getUsername()).thenReturn("admin");
        when(userDetails.getAuthorities()).thenAnswer(i ->
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        String token = jwtUtil.generateAccessToken(userDetails);
        String rol = jwtUtil.extractRol(token);

        assertEquals("ADMIN", rol);
    }

    /**
     * Prueba que isTokenValid retorna true para un token valido.
     *
     * Verifica que un token recien generado es valido para el usuario correspondiente.
     */
    @Test
    void testIsTokenValid() {
        when(userDetails.getUsername()).thenReturn("admin");
        when(userDetails.getAuthorities()).thenAnswer(i ->
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        String token = jwtUtil.generateAccessToken(userDetails);
        boolean valid = jwtUtil.isTokenValid(token, userDetails);

        assertTrue(valid);
    }

    /**
     * Prueba que isTokenValid retorna false para un token con username diferente.
     *
     * Verifica que un token generado para un usuario no es valido para otro usuario.
     */
    @Test
    void testIsTokenValidUsernameDiferente() {
        when(userDetails.getUsername()).thenReturn("admin");
        when(userDetails.getAuthorities()).thenAnswer(i ->
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        String token = jwtUtil.generateAccessToken(userDetails);

        UserDetails otroUsuario = org.mockito.Mockito.mock(UserDetails.class);
        when(otroUsuario.getUsername()).thenReturn("usuario");

        boolean valid = jwtUtil.isTokenValid(token, otroUsuario);

        assertFalse(valid);
    }

    /**
     * Prueba que isTokenValid retorna false para un token malformado.
     *
     * Verifica que un token invalido o malformado no pasa la validacion.
     */
    @Test
    void testIsTokenValidTokenInvalido() {
        when(userDetails.getUsername()).thenReturn("admin");

        boolean valid = jwtUtil.isTokenValid("token.invalido.jwt", userDetails);

        assertFalse(valid);
    }
}