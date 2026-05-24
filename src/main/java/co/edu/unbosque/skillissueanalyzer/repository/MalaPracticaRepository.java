/**
 * Paquete que contiene los repositorios del aplicativo.
 */
package co.edu.unbosque.skillissueanalyzer.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.skillissueanalyzer.model.MalaPractica;

/**
 * Repositorio de la entidad MalaPractica.
 * Las malas practicas se gestionan en cascada desde Analisis,
 * pero este repositorio permite consultas directas si se necesitan.
 */
public interface MalaPracticaRepository extends CrudRepository<MalaPractica, Long> {

}