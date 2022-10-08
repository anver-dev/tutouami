package mx.tutouami.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import mx.tutouami.model.entity.Degree;

public interface DegreeRepository extends JpaRepository<Degree, Long> {

}
