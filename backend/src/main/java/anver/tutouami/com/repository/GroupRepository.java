package anver.tutouami.com.repository;

import org.springframework.data.repository.CrudRepository;

import anver.tutouami.com.model.entity.Group;

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
