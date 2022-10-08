package mx.tutouami.repository;

import org.springframework.data.repository.CrudRepository;

import mx.tutouami.model.entity.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Long> {

}
