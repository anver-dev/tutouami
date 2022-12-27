package anver.tutouami.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import anver.tutouami.com.model.entity.Advice;
import anver.tutouami.com.model.entity.Subject;

public interface AdviceRepository extends JpaRepository<Advice, Long> {
	public List<Advice> findAll();
	public List<Advice> findBySubject(Subject subject);
}
