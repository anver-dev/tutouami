package mx.tutouami.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.tutouami.model.entity.Account;
import mx.tutouami.model.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	public List<Student> findAll();

	public Optional<Student> findByEnrolment(Integer enrolment);

	public Optional<Student> findByAccount(Account account);
}
