package anver.tutouami.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import anver.tutouami.com.model.entity.Degree;

public interface DegreeRepository extends JpaRepository<Degree, Long> {

}
