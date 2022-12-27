package anver.tutouami.com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import anver.tutouami.com.model.dto.AdviceDTO;
import anver.tutouami.com.model.entity.Inscription;
import anver.tutouami.com.model.entity.Student;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

	public Optional<Inscription> findByAsesoriaAndAlumno(AdviceDTO asesoria, Student alumno);

	public List<Inscription> findByAlumno(Student alumno);

}
