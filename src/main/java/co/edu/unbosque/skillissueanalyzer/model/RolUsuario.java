/**
 * Paquete que contiene las clases entidad del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.model;

/**
 * Enum que define los roles disponibles para un usuario en el aplicativo.
 */
public enum RolUsuario {

	/**
	 * Usuario con acceso completo al sistema, puede gestionar usuarios y ver todos los analisis.
	 */
	ADMIN,

	/**
	 * Usuario estandar que puede subir proyectos y consultar sus propios analisis.
	 */
	USUARIO
}
