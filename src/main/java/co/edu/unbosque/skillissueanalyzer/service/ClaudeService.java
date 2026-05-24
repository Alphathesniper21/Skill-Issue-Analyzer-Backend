/**
 * Paquete que contiene los servicios del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.edu.unbosque.skillissueanalyzer.dto.MalaPracticaDTO;
import co.edu.unbosque.skillissueanalyzer.util.AESUtil;

/**
 * Servicio que se comunica con la API de Claude (Anthropic) para analizar
 * codigo Java en busca de malas practicas de programacion.
 *
 * Envia el codigo fuente al modelo y parsea la respuesta JSON
 * estructurada que devuelve Claude.
 */
@Service
public class ClaudeService {

	/**
	 * Logger para registrar mensajes.
	 */
	private static final Logger log = LoggerFactory.getLogger(ClaudeService.class);

	/**
	 * API key de Claude almacenada cifrada con AES-GCM en application.properties.
	 * Se descifra en tiempo de ejecucion mediante {@link AESUtil#decrypt(String)}.
	 */
	@Value("${claude.api.key}")
	private String apiKeyCifrada;

	/**
	 * URL de la API key de Claude.
	 */
	@Value("${claude.api.url}")
	private String apiUrl;

	/**
	 * Model de ka API Key de Claude.
	 */
	@Value("${claude.api.model}")
	private String model;
	
	/**
	 * Numero maximo de tokens permitidos por la API Key de Claude.
	 */
	@Value("${claude.api.max-tokens}")
	private int maxTokens;

	/**
	 * Cliente HTTP utilizado para realizar las peticiones a la API de Claude.
	 */
	private final RestTemplate restTemplate;
	
	/**
	 * Mapeador de objetos utilizando para serializar y deserealizar JSON
	 * en las respuestas de la API de Claude.
	 */
	private final ObjectMapper objectMapper;
	
	/**
	 * Constructor vacio del servicio.
	 */
	public ClaudeService() {
		this.restTemplate = new RestTemplate();
		this.objectMapper = new ObjectMapper();
	}

	/**
	 * Envia el codigo Java a Claude y retorna la lista de malas practicas detectadas.
	 *
	 * @param codigoJava Codigo fuente Java a analizar.
	 * @return Lista de MalaPracticaDTO con los problemas detectados.
	 *         Retorna lista vacia si la API falla.
	 */
	public List<MalaPracticaDTO> analizarCodigo(String codigoJava) {
		String prompt = buildPrompt(codigoJava);

		String requestBody = """
				{
				  "model": "%s",
				  "max_tokens": %d,
				  "messages": [
				    {
				      "role": "user",
				      "content": %s
				    }
				  ]
				}
				""".formatted(model, maxTokens, toJsonString(prompt));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-api-key", AESUtil.decrypt(apiKeyCifrada));
		headers.set("anthropic-version", "2023-06-01");

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		try {
			ResponseEntity<String> response =
					restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
			return parseClaudeResponse(response.getBody());
		} catch (ResourceAccessException e) {
			log.error("No se pudo conectar con la API de Claude (timeout o red): {}", e.getMessage());
			return List.of();
		} catch (HttpClientErrorException e) {
			log.error("Error del cliente al llamar a la API de Claude [{}]: {}", e.getStatusCode(), e.getMessage());
			return List.of();
		} catch (HttpServerErrorException e) {
			log.error("Error del servidor de Claude [{}]: {}", e.getStatusCode(), e.getMessage());
			return List.of();
		}
	}

	/**
	 * Parsea la respuesta de Claude extrayendo el array JSON de malas practicas.
	 *
	 * @param responseBody Cuerpo de la respuesta de la API de Claude.
	 * @return Lista de MalaPracticaDTO parseados.
	 */
	public List<MalaPracticaDTO> parseClaudeResponse(String responseBody) {
		List<MalaPracticaDTO> result = new ArrayList<>();
		try {
			JsonNode root    = objectMapper.readTree(responseBody);
			JsonNode content = root.path("content");

			if (!content.isArray() || content.isEmpty()) return result;

			String text = content.get(0).path("text").asText("").trim();

			text = text.replaceAll("(?s)```json\\s*", "")
					   .replaceAll("(?s)```\\s*", "")
					   .trim();

			if (text.isEmpty() || text.equals("[]")) return result;

			JsonNode array = objectMapper.readTree(text);
			if (!array.isArray()) return result;

			for (JsonNode node : array) {
				MalaPracticaDTO dto = new MalaPracticaDTO();
				dto.setLinea(node.path("linea").asInt(0));
				dto.setTipo(sanitize(node.path("tipo").asText("Desconocido"), 80));
				dto.setDescripcion(sanitize(node.path("descripcion").asText(""), 500));
				dto.setSugerencia(sanitize(node.path("sugerencia").asText(""), 500));

				String severidad = node.path("severidad").asText("MEDIA").toUpperCase();
				if (!List.of("BAJA", "MEDIA", "ALTA", "CRITICA").contains(severidad)) {
					severidad = "MEDIA";
				}
				dto.setSeveridad(severidad);

				result.add(dto);
			}
		} catch (JsonProcessingException e) {
			log.error("Error al parsear el JSON de la respuesta de Claude: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("Argumento invalido al procesar la respuesta de Claude: {}", e.getMessage());
		}
		return result;
	}

	/**
	 * Construye el prompt para Claude con instrucciones precisas de formato JSON.
	 *
	 * @param codigo El codigo Java a analizar.
	 * @return El prompt completo listo para enviar.
	 */
	public String buildPrompt(String codigo) {
		return """
				Analiza el siguiente codigo Java en busca de malas practicas de programacion.

				Responde UNICAMENTE con un array JSON valido. Sin texto adicional, sin bloques markdown, sin explicaciones.
				Si no encuentras malas practicas responde con exactamente: []

				Cada elemento del array JSON debe tener EXACTAMENTE estos campos:
				- "linea": numero de linea aproximado donde ocurre el problema (numero entero)
				- "tipo": nombre corto de la mala practica (string, max 80 chars)
				- "descripcion": descripcion clara del problema encontrado (string, max 500 chars)
				- "severidad": exactamente uno de: "BAJA", "MEDIA", "ALTA", "CRITICA"
				- "sugerencia": como corregir el problema (string, max 500 chars)

				Criterios de severidad:
				- CRITICA: God Class, rompe principios SOLID, vulnerabilidades de seguridad
				- ALTA: Long Method (+30 lineas), Empty Catch Block, Variables de una letra, Deep Nesting (+4 niveles)
				- MEDIA: Magic Numbers, Dead Code, Feature Envy, Static Abuse, Missing Javadoc en metodos publicos
				- BAJA: Nombres poco descriptivos, comentarios obvios, imports no usados, codigo comentado

				Tipos a buscar: God Class, Magic Number, Long Method, Empty Catch Block,
				Dead Code, Duplicate Code, Feature Envy, Data Clumps, Primitive Obsession,
				Variable Naming, Deep Nesting, Static Abuse, Missing Javadoc, Commented Code,
				Inappropriate Intimacy, Large Class, Long Parameter List, Speculative Generality,
				Mutable Static Fields.

				Codigo Java a analizar:
				```java
				%s
				```
				""".formatted(codigo);
	}

	/**
	 * Serializa un String a formato JSON string con comillas y escapes correctos.
	 */
	public String toJsonString(String text) {
		try {
			return objectMapper.writeValueAsString(text);
		} catch (JsonProcessingException e) {
			return "\"" + text.replace("\"", "\\\"") + "\"";
		}
	}

	/**
	 * Trunca un texto al maximo indicado si excede la longitud.
	 */
	public String sanitize(String text, int maxLength) {
		if (text == null) return "";
		return text.length() > maxLength ? text.substring(0, maxLength) : text;
	}
		
}