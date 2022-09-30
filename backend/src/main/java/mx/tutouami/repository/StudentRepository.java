package mx.tutouami.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import mx.tutouami.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

	public List<Student> findAll();
	public Student findByEmail(String correo);
}
