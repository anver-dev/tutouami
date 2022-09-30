package mx.tutouami.repository;

import org.springframework.data.repository.CrudRepository;

import mx.tutouami.entity.Subject;

public interface MateriaRepository extends CrudRepository<Subject, Long> {

}
