package anver.tutouami.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import anver.tutouami.com.model.entity.Role;
import anver.tutouami.com.model.enums.RoleTypes;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleTypes name);
}
