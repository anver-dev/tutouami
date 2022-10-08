package mx.tutouami.repository;

import org.springframework.data.repository.CrudRepository;

import mx.tutouami.model.entity.Group;

/**
 * Repositorio para Grupos
 * 
 * @author humbertocervantes
 *
 */
public interface GroupRepository extends CrudRepository <Group, Long> {
	
	/**
	 * Encuentra un grupo a partir de un nombre
	 * 
	 * @param nombre
	 * @return
	 */
	public Group findByNombre(String nombre);
	

}
