package mx.tutouami.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.tutouami.model.entity.Account;

/**
 * 
 * Repositorio para usuarios
 * 
 * @author humbertocervantes
 *
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	@Query
	(value = "SELECT a.id_account, a.email, a.password \n"
			+ "FROM account a \n"
			+ "WHERE a.email = ?1 AND a.password = HASH('SHA256', ?2, 1000)",
	nativeQuery = true)
	public Optional<Account> findByEmailAndPassword(String email, String password);
}
