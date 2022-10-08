package mx.tutouami.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import mx.tutouami.model.dto.AdviceDTO;
import mx.tutouami.model.dto.CommentDTO;
import mx.tutouami.model.entity.Advice;
import mx.tutouami.model.entity.Student;
import mx.tutouami.model.entity.Subject;
import mx.tutouami.repository.AdviceRepository;
import mx.tutouami.repository.CommentRepository;
import mx.tutouami.repository.SubjectRepository;
import mx.tutouami.repository.StudentRepository;
import mx.tutouami.service.IAdviceService;

@Service
@Slf4j
public class AdviceServiceImpl implements IAdviceService {

	@Autowired
	private AdviceRepository adviceRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private CommentRepository commentRepository;

	@Override
	@Transactional(readOnly = true)
	public List<AdviceDTO> findAll() {
		log.info("Find all advices");
		return adviceRepository.findAll().stream().map(a -> AdviceDTO.generate(a)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public AdviceDTO create(AdviceDTO adviceDTO, Long studentId) {
		Student student = studentRepository.findById(studentId).orElseThrow(() -> {
			throw new IllegalArgumentException("No se encontró el alumno");
		});

		Subject subject = subjectRepository.findById(adviceDTO.getSubjectId()).orElseThrow(() -> {
			throw new IllegalArgumentException("No se encontró la materia");
		});

		Advice advice = Advice.builder().date(adviceDTO.getDate()).details(adviceDTO.getDetails())
				.endTime(adviceDTO.getEndTime()).id(adviceDTO.getId()).location(adviceDTO.getLocation())
				.price(adviceDTO.getPrice()).score(adviceDTO.getScore()).startTime(adviceDTO.getStartTime())
				.status(adviceDTO.getStatus()).student(student).subject(subject).totalScore(0).type(adviceDTO.getType())
				.url(adviceDTO.getUrl()).build();

		advice = adviceRepository.save(advice);

		subject.addAdvice(advice);
		subjectRepository.save(subject);

		student.addAdvice(advice);
		studentRepository.save(student);

		return AdviceDTO.generate(advice);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AdviceDTO> findBySubject(Long subjectId) {
		Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> {
			throw new IllegalArgumentException("No se encontró la materia");
		});

		return adviceRepository.findBySubject(subject).stream().map(a -> AdviceDTO.generate(a))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public AdviceDTO findById(Long adviceId) {
		return AdviceDTO.generate(adviceRepository.findById(adviceId).orElseThrow(() -> {
			throw new IllegalArgumentException("No se encontró la asesoria");
		}));
	}

	@Override
	@Transactional
	public AdviceDTO updateStatus(Long adviceId, Long studentId, String status) {
		Advice advice = adviceRepository.findById(adviceId).orElseThrow(() -> {
			throw new IllegalArgumentException("No se encontró la asesoria");
		});

		Student student = studentRepository.findById(studentId).orElseThrow(() -> {
			throw new IllegalArgumentException("No se encontró el alumno");
		});

		Subject subject = subjectRepository.findById(advice.getSubject().getIdMateria()).orElseThrow(() -> {
			throw new IllegalArgumentException("No se encontró la materia");
		});

		advice.setStatus(status);
		advice = adviceRepository.save(advice);

		subject.addAdvice(advice);
		subjectRepository.save(subject);

		student.addAdvice(advice);
		studentRepository.save(student);

		return AdviceDTO.generate(advice);
	}
	
	@Override
	@Transactional
	public AdviceDTO updateScore(Long adviceId, Float puntuacion) {
		Advice advice = adviceRepository.findById(adviceId).orElseThrow(() -> {
			throw new IllegalArgumentException("No se encontró la asesoria");
		});

		Student student = studentRepository.findById(advice.getStudent().getId()).orElseThrow(() -> {
			throw new IllegalArgumentException("No se encontró el alumno");
		});

		Subject subject = subjectRepository.findById(advice.getSubject().getIdMateria()).orElseThrow(() -> {
			throw new IllegalArgumentException("No se encontró la materia");
		});

		Integer tmpTotalScore = advice.getTotalScore() + 1;
		Float newScore = (advice.getScore() + puntuacion) / tmpTotalScore;

		advice.setTotalScore(tmpTotalScore);
		advice.setScore(newScore);

		advice = adviceRepository.save(advice);

		subject.addAdvice(advice);
		subjectRepository.save(subject);

		student.addAdvice(advice);
		studentRepository.save(student);

		return AdviceDTO.generate(advice);
	}

	public AdviceDTO update(Long idAsesoria, Long idAlumno, AdviceDTO asesoriaDto) {
		Optional<Advice> optionalAsesoria = adviceRepository.findById(idAsesoria);
		if (optionalAsesoria.isEmpty())
			throw new IllegalArgumentException("No se encontró la asesoria");

		Advice asesoria = optionalAsesoria.get();
		return AdviceDTO.generate(asesoria);
	}

	public boolean eliminarAsesoria(AdviceDTO asesoria) {
		return false;
	}

	@Override
	public CommentDTO createComment(Long id, CommentDTO comment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommentDTO updateComment(@Valid Long id, CommentDTO comment) {
		// TODO Auto-generated method stub
		return null;
	}
}
