package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Inscripcion;

public interface InscripcionRepository extends CrudRepository<Inscripcion, Long> {

}
