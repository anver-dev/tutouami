package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Materia;

public interface MateriaRepository extends CrudRepository<Materia, Long> {

}
