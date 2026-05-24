/**
 * Paquete principal del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Clase principal del aplicativo web. Se realizan los beans necesarios
 * para el contexto del aplicativo.
 */
@SpringBootApplication
public class SkillissueanalyzerApplication {

	/**
	 * Metodo principal (main) que inicia la ejecucion del aplicativo web.
	 * @param args Argumentos de linea de comandos pasados a la aplicacion.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SkillissueanalyzerApplication.class, args);
	}
	
	/**
	 * Metodo de creacion y registro de bean ModelMApper.
	 * Permite la conversion de objetos y DTOs.
	 * @return Retorna una instancia de ModelMapper configurada para su uso.
	 */
	@Bean
	public ModelMapper getModelMapper() {
		
		return new ModelMapper();
	}

}
