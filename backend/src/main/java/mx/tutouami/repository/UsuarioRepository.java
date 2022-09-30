package mx.tutouami.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import mx.tutouami.entity.User;

/**
 * 
 * Repositorio para usuarios
 * 
 * @author humbertocervantes
 *
 */
public interface UsuarioRepository extends CrudRepository<User, Long> {

	public User findByNombreAndApellido(String nombre, String apellido);

	public List<User> findByEdadBetween(int edad1, int edad2);

}
