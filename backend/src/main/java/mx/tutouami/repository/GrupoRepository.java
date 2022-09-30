package mx.tutouami.repository;

import org.springframework.data.repository.CrudRepository;

import mx.tutouami.entity.Group;

/**
 * Repositorio para Grupos
 * 
 * @author humbertocervantes
 *
 */
public interface GrupoRepository extends CrudRepository <Group, Long> {
	
	/**
	 * Encuentra un grupo a partir de un nombre
	 * 
	 * @param nombre
	 * @return
	 */
	public Group findByNombre(String nombre);
	

}
