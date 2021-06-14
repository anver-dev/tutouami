package mx.uam.ayd.proyecto.datos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.dto.AsesoriaDto;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.negocio.modelo.Inscripcion;

public interface InscripcionRepository extends CrudRepository<Inscripcion, Long> {

	public Optional<Inscripcion> findByAsesoriaAndAlumno(AsesoriaDto asesoria, Alumno alumno);

	public List<Inscripcion> findByAlumno(Alumno alumno);

}
