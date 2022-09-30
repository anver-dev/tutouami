package mx.tutouami.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import mx.tutouami.entity.Inscription;
import mx.tutouami.entity.Student;
import mx.tutouami.model.dto.AdviceDTO;

public interface InscripcionRepository extends CrudRepository<Inscription, Long> {

	public Optional<Inscription> findByAsesoriaAndAlumno(AdviceDTO asesoria, Student alumno);

	public List<Inscription> findByAlumno(Student alumno);

}
