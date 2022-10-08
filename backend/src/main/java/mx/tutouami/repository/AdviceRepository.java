package mx.tutouami.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.tutouami.model.entity.Advice;
import mx.tutouami.model.entity.Subject;

public interface AdviceRepository extends JpaRepository<Advice, Long> {
	public List<Advice> findAll();
	public List<Advice> findBySubject(Subject subject);
}
