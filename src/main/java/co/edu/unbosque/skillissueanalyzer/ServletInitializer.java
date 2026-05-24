/**
 * Paquete principal del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Clase inicializadora de Servlet para la configuracion del despligue del 
 * aplicativo web.
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Metodo de configuracion de las fuentes de la aplicacion para su 
	 * inicializacion.
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SkillissueanalyzerApplication.class);
	}

}
