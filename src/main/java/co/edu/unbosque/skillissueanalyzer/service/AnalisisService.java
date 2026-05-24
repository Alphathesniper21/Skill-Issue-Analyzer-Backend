/**
 * Paquete que contiene los servicios del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import co.edu.unbosque.skillissueanalyzer.dto.AnalisisDTO;
import co.edu.unbosque.skillissueanalyzer.dto.MalaPracticaDTO;
import co.edu.unbosque.skillissueanalyzer.exception.ResourceNotFoundException;
import co.edu.unbosque.skillissueanalyzer.exception.ValidacionException;
import co.edu.unbosque.skillissueanalyzer.model.Analisis;
import co.edu.unbosque.skillissueanalyzer.model.MalaPractica;
import co.edu.unbosque.skillissueanalyzer.repository.AnalisisRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Clase Servicio de analisis.
 */
@Service
public class AnalisisService {

	/**
	 * Constante para darle formato a las fechas.
	 */
	private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	/**
	 * Constante de limite de caracteres permitidos para Claude.
	 */
	private static final int MAX_CODIGO_CHARS = 15_000;

	/**
	 * Repositorio de Analisis.
	 */
	@Autowired
	private AnalisisRepository analisisRepo;

	/**
	 * Repositorio de Claude.
	 */
	@Autowired
	private ClaudeService claudeService;

	/**
	 * Repositorio de Github.
	 */
	@Autowired
	private GithubService githubService;

	/**
	 * Atributo ModelMapper.
	 */
	@Autowired
	private ModelMapper mapper;

	/**
	 * Metodo que permite el recibimiento y descomprension de los proyectos zip que
	 * adjunta el usuario.
	 * 
	 * @param archivo Archivo que adjunta el usuario.
	 * @return Retorna un DTO del analisis que indicara el diagnostico de malas
	 *         practicas.
	 */
	public AnalisisDTO analizarZip(MultipartFile archivo) {

		if (archivo == null || archivo.isEmpty()) {
			throw new ValidacionException("Debes subir un archivo .zip.");
		}
		String filename = archivo.getOriginalFilename();
		if (filename == null || !filename.toLowerCase().endsWith(".zip")) {
			throw new ValidacionException("Solo se aceptan archivos .zip.");
		}

		String codigoJava = extraerCodigoDeZip(archivo);

		if (codigoJava.isBlank()) {
			throw new ValidacionException("No se encontraron archivos .java en el zip.");
		}

		List<MalaPracticaDTO> practicasDTO = claudeService.analizarCodigo(codigoJava);

		if (practicasDTO == null) {
			throw new ValidacionException(
					"El servicio de analisis de IA no esta disponible. Verifica la API key de Claude.");
		}

		int puntuacion = calcularPuntuacion(practicasDTO);

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		Analisis analisis = new Analisis(codigoJava, username);
		analisis.setTotalProblemas(practicasDTO.size());
		analisis.setPuntuacion(puntuacion);
		analisis = analisisRepo.save(analisis);

		List<MalaPractica> entidades = new ArrayList<>();
		for (MalaPracticaDTO dto : practicasDTO) {
			entidades.add(new MalaPractica(dto.getLinea(), dto.getTipo(), dto.getDescripcion(), dto.getSeveridad(),
					dto.getSugerencia(), analisis));
		}
		analisis.setTotalProblemas(practicasDTO.size());
		analisis.setPuntuacion(puntuacion);
		analisis.setMalasPracticas(entidades);
		analisis = analisisRepo.save(analisis);

		return mapper.map(analisis, AnalisisDTO.class);
	}

	/**
	 * Metodo de obtencion de lista de Analisis realizados.
	 * 
	 * @return Retorna una lista de todos los analisis realizados.
	 */
	public List<AnalisisDTO> getMisAnalisis() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		List<Analisis> entityList = analisisRepo.findByNombreUsuarioAutorOrderByFechaAnalisisDesc(username);
		List<AnalisisDTO> dtoList = new ArrayList<>();

		entityList.forEach(entidad -> dtoList.add(mapper.map(entidad, AnalisisDTO.class)));

		return dtoList;
	}

	/**
	 * Metodo de obtencion de un analisis mediante su identificador unico (ID).
	 * 
	 * @param id ID del analisis.
	 * @return Retorna en analisis solicitado mediante su ID.
	 */
	public AnalisisDTO getById(Long id) {

		Optional<Analisis> encontrado = analisisRepo.findById(id);

		if (encontrado.isPresent()) {
			return mapper.map(encontrado.get(), AnalisisDTO.class);
		}

		throw new ResourceNotFoundException("Analisis #" + id + " no encontrado.");
	}

	/**
	 * Metodo de eliminacion de un analisis mediante su identificador unico (ID).
	 * 
	 * @param id ID del analisis a buscar.
	 */
	public void eliminar(Long id) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		Optional<Analisis> encontrado = analisisRepo.findById(id);

		if (encontrado.isPresent()) {
			if (!encontrado.get().getNombreUsuarioAutor().equals(username)) {
				throw new ValidacionException("No puedes eliminar analisis de otro usuario.");
			}
			analisisRepo.delete(encontrado.get());
		} else {
			throw new ResourceNotFoundException("Analisis #" + id + " no encontrado.");
		}
	}

	/**
	 * Recupera todos los analisis registrados en el sistema.
	 *
	 * Obtiene la lista completa de analisis almacenados en la base de datos,
	 * independientemente del usuario que los creo.
	 * 
	 * @return Retorna una lista de todos los analisis.
	 */
	public List<AnalisisDTO> getTodos() {

		List<Analisis> entityList = (List<Analisis>) analisisRepo.findAll();
		List<AnalisisDTO> dtoList = new ArrayList<>();

		entityList.forEach(entidad -> dtoList.add(mapper.map(entidad, AnalisisDTO.class)));

		return dtoList;
	}

	/**
	 * Extrae todos los archivos .java del zip y los concatena. Limita el total a
	 * MAX_CODIGO_CHARS para no exceder el contexto de Claude.
	 * 
	 * @return Retorna un string con un mensaje del estado de la operacion de
	 *         extraccion.
	 */
	public String extraerCodigoDeZip(MultipartFile archivo) {
		StringBuilder sb = new StringBuilder();
		try (ZipInputStream zis = new ZipInputStream(archivo.getInputStream(), StandardCharsets.UTF_8)) {

			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				if (!entry.isDirectory() && entry.getName().toLowerCase().endsWith(".java")) {

					sb.append("// ── ").append(entry.getName()).append(" ──\n");

					BufferedReader reader = new BufferedReader(new InputStreamReader(zis, StandardCharsets.UTF_8));
					String line;
					while ((line = reader.readLine()) != null) {
						sb.append(line).append("\n");
						if (sb.length() >= MAX_CODIGO_CHARS) {
							sb.append("\n// [... archivo truncado por limite de analisis ...]\n");
							return sb.toString();
						}
					}
					sb.append("\n");
				}
				zis.closeEntry();
			}
		} catch (IOException e) {
			throw new ValidacionException("Error al leer el archivo zip: " + e.getMessage());
		}
		return sb.toString();
	}

	/**
	 * Calcula la puntuacion (0–100) en base a la cantidad y severidad de los
	 * problemas. Mayor puntuacion: mas skill issues, peor codigo.
	 */
	public int calcularPuntuacion(List<MalaPracticaDTO> lista) {
		int score = 0;
		for (MalaPracticaDTO p : lista) {
			score += switch (p.getSeveridad()) {
			case "CRITICA" -> 20;
			case "ALTA" -> 10;
			case "MEDIA" -> 5;
			default -> 2;
			};
		}
		return Math.min(score, 100);
	}

	/**
	 * Metodo que permite analizar repositorios de la plataforma GitHub.
	 * 
	 * @param repoUrl Link del repositorio de la plataforma GitHub.
	 * @return Retorna un DTO de analisis con respecto al diagnostico del
	 *         repositorio.
	 */
	public AnalisisDTO analizarRepo(String repoUrl) {
		if (repoUrl == null || repoUrl.isBlank()) {
			throw new ValidacionException("Debes proporcionar una URL de repositorio.");
		}
		if (!repoUrl.startsWith("https://github.com/")) {
			throw new ValidacionException(
					"Solo se aceptan repositorios publicos de GitHub. " + "Formato: https://github.com/owner/repo");
		}

		String codigoJava = githubService.extraerCodigoDeRepo(repoUrl);

		List<MalaPracticaDTO> practicasDTO = claudeService.analizarCodigo(codigoJava);

		int puntuacion = calcularPuntuacion(practicasDTO);

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		Analisis analisis = new Analisis(codigoJava, username);
		analisis.setTotalProblemas(practicasDTO.size());
		analisis.setPuntuacion(puntuacion);

		List<MalaPractica> entidades = new ArrayList<>();
		for (MalaPracticaDTO dto : practicasDTO) {
			entidades.add(new MalaPractica(dto.getLinea(), dto.getTipo(), dto.getDescripcion(), dto.getSeveridad(),
					dto.getSugerencia(), analisis));
		}
		analisis.setMalasPracticas(entidades);
		analisis = analisisRepo.save(analisis);

		return mapper.map(analisis, AnalisisDTO.class);
	}
}
