package anver.tutouami.com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import anver.tutouami.com.model.entity.Account;
import anver.tutouami.com.model.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	public List<Student> findAll();

	public Optional<Student> findByEnrolment(Integer enrolment);

	public Optional<Student> findByAccount(Account account);
}
