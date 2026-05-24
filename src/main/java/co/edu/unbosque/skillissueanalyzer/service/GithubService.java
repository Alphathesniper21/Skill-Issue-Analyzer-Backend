/**
 * Paquete que contiene los servicios del aplicativo. 
 */
package co.edu.unbosque.skillissueanalyzer.service;

import co.edu.unbosque.skillissueanalyzer.exception.ValidacionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que permite la extraccion de codigo fuente Java desde repositorios
 * publicos de GitHub. Interactua con la API de GitHub para obtener la
 * estructura del repositorio y descargar los archivos Java encontrados.
 */
@Service
public class GithubService {

	/**
	 * Numero maximo de archivos java a procesar por repositorio.
	 */
	private static final int MAX_ARCHIVOS = 10;

	/**
	 * Numero maximo de caracteres permitidos en el codigo fuente.
	 */
	private static final int MAX_CHARS = 15_000;

	/**
	 * Cliente HTTP utilizada para realizar las peticiones a la API de GitHub.
	 */
	private final HttpClient http = HttpClient.newHttpClient();

	/**
	 * Mapeador de objetos utilizado para deserealizar las respuestas JSON de la
	 * API.
	 */
	private final ObjectMapper mapper = new ObjectMapper();

	/**
	 * Recibe una URL de GitHub (https://github.com/owner/repo) y retorna el codigo
	 * fuente Java concatenado, listo para Claude.
	 * 
	 * @return Codigo fuente Java concatenado de los archivos encontrados.
	 */
	public String extraerCodigoDeRepo(String repoUrl) {

		String[] partes = parsearUrl(repoUrl);
		String owner = partes[0];
		String repo = partes[1];

		String sha = obtenerShaRama(owner, repo);
		List<String> archivosJava = listarArchivosJava(owner, repo, sha);

		if (archivosJava.isEmpty()) {
			throw new ValidacionException("No se encontraron archivos .java en el repositorio: " + repoUrl);
		}

		StringBuilder sb = new StringBuilder();
		int procesados = 0;

		for (String path : archivosJava) {
			if (procesados >= MAX_ARCHIVOS || sb.length() >= MAX_CHARS)
				break;

			String contenido = descargarArchivo(owner, repo, path);
			sb.append("// ── ").append(path).append(" ──\n");
			sb.append(contenido).append("\n");
			procesados++;
		}

		if (sb.length() > MAX_CHARS) {
			return sb.substring(0, MAX_CHARS) + "\n// [... truncado por limite de analisis ...]\n";
		}

		return sb.toString();
	}

	/**
	 * Parsea la URL del repositorio GitHub y extrae el owner y el nombre del
	 * repositorio.
	 * 
	 * @param url URL del repositorio GitHub.
	 * @return Arreglo con el owner en la posicion 0 y el nombre del repositorio en
	 *         la posicion 1.
	 */
	public String[] parsearUrl(String url) {
		String limpia = url.trim().replaceAll("\\.git$", "").replaceAll("/$", "");
		String[] partes = limpia.replace("https://github.com/", "").split("/");

		if (partes.length < 2 || partes[0].isBlank() || partes[1].isBlank()) {
			throw new ValidacionException("URL de GitHub invalida. Formato esperado: https://github.com/owner/repo");
		}
		return new String[] { partes[0], partes[1] };
	}

	/**
	 * Obtiene el SHA del ultimo commit de la rama principal (main o master) del
	 * repositorio.
	 * 
	 * @param owner Propietario del repositorio.
	 * @param repo  Nombre del repositorio.
	 * @return SHA del ultimo commit de la rama principal.
	 */
	public String obtenerShaRama(String owner, String repo) {
		for (String rama : List.of("main", "master")) {
			try {
				String url = "https://api.github.com/repos/%s/%s/branches/%s".formatted(owner, repo, rama);
				String json = get(url);
				JsonNode node = mapper.readTree(json);
				return node.path("commit").path("sha").asText();
			} catch (Exception ignored) {
			}
		}
		throw new ValidacionException(
				"No se pudo acceder al repositorio. Verifica que sea publico y que exista la rama main o master.");
	}

	/**
	 * Lista todos los archivos Java encontrados en el arbol del repositorio.
	 * 
	 * @param owner Propietario del repositorio.
	 * @param repo  Nombre del repositorio.
	 * @param sha   SHA del commit a partir del cual se lista el arbol.
	 * @return Lista de rutas de archivos Java encontrados en el repositorio.
	 */
	public List<String> listarArchivosJava(String owner, String repo, String sha) {
		try {
			String url = "https://api.github.com/repos/%s/%s/git/trees/%s?recursive=1".formatted(owner, repo, sha);
			String json = get(url);
			JsonNode tree = mapper.readTree(json).path("tree");

			List<String> paths = new ArrayList<>();
			for (JsonNode nodo : tree) {
				String path = nodo.path("path").asText();
				if (path.endsWith(".java")) {
					paths.add(path);
				}
			}
			return paths;
		} catch (ValidacionException e) {
			throw e;
		} catch (JsonProcessingException e) {
			throw new ValidacionException("Error al parsear respuesta del repo: " + e.getMessage());
		}
	}

	/**
	 * Descarga el contenido de un archivo Java desde el repositorio GitHub.
	 * 
	 * @param owner Propietario del repositorio.
	 * @param repo  Nombre del repositorio.
	 * @param path  Ruta del archivo dentro del repositorio.
	 * @return Contenido del archivo como cadena de texto.
	 */
	public String descargarArchivo(String owner, String repo, String path) {
		try {
			String url = "https://raw.githubusercontent.com/%s/%s/HEAD/%s".formatted(owner, repo, path);
			return get(url);
		} catch (Exception e) {
			return "// Error al descargar " + path + "\n";
		}
	}

	/**
	 * Realiza una peticion HTTP GET a la URL indicada y retorna el cuerpo de la
	 * respuesta.
	 *
	 * @param url URL a la que se realiza la peticion GET.
	 * @return Cuerpo de la respuesta como cadena de texto.
	 */
	public String get(String url) {
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
					.header("Accept", "application/vnd.github+json").header("User-Agent", "SkillIssueAnalyzer/1.0")
					.GET().build();

			HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 404) {
				throw new ValidacionException("Repositorio no encontrado o privado: " + url);
			}
			if (response.statusCode() != 200) {
				throw new ValidacionException("Error al acceder a GitHub (HTTP " + response.statusCode() + ")");
			}
			return response.body();
		} catch (ValidacionException e) {
			throw e;
		} catch (IOException | InterruptedException e) {
			throw new ValidacionException("Error de conexion con GitHub: " + e.getMessage());
		}
	}
}