package mx.tutouami.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import mx.tutouami.entity.Account;
import mx.tutouami.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

	public List<Student> findAll();
	public Optional<Student> findByEnrolment(Integer enrolment);
	public Optional<Student> findByAccount(Account account);
}
