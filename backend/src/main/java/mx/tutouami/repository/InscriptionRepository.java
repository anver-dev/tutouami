package mx.tutouami.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.tutouami.model.dto.AdviceDTO;
import mx.tutouami.model.entity.Inscription;
import mx.tutouami.model.entity.Student;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

	public Optional<Inscription> findByAsesoriaAndAlumno(AdviceDTO asesoria, Student alumno);

	public List<Inscription> findByAlumno(Student alumno);

}
