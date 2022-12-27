package anver.tutouami.com.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import anver.tutouami.com.exceptions.EnrolmentAlreadyExistsException;
import anver.tutouami.com.exceptions.NotFoundException;
import anver.tutouami.com.model.dto.StatusDTO;
import anver.tutouami.com.model.dto.StudentDTO;
import anver.tutouami.com.model.entity.Student;
import anver.tutouami.com.model.enums.StudentStatusTypes;
import anver.tutouami.com.repository.StudentRepository;
import anver.tutouami.com.service.IStudentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceImpl implements IStudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public List<StudentDTO> findAll() {
		log.info("Get all students");
		return studentRepository.findAll().stream().map(s -> StudentDTO.generate(s)).collect(Collectors.toList());
	}

	@Override
	public StudentDTO findById(Long id) {
		log.info(String.format("Get student by id:: %d", id));
		return StudentDTO.generate(studentRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Student by id: %d not found", id))));
	}

	@Override
	public StudentDTO create(StudentDTO studentDTO) {
		validate(studentDTO);

		Student studentToCreate = Student.builder().age(studentDTO.getAge()).cv(studentDTO.getCv())
				.degree(studentDTO.getDegree()).description(studentDTO.getDescription())
				.lastName(studentDTO.getLastName()).name(studentDTO.getName()).phone(studentDTO.getPhone()).score(0.00F)
				.secondLastName(studentDTO.getSecondLastName()).status(StudentStatusTypes.AVAILABLE.getValue())
				.trimester(studentDTO.getTrimester()).build();

		return StudentDTO.generate(studentRepository.save(studentToCreate));
	}

	@Override
	public StudentDTO updateStatus(Long id, StatusDTO status) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Student by id: %d not found", id)));

		student.setStatus(status.getStatus().getValue());

		return StudentDTO.generate(studentRepository.save(student));
	}

	private void validate(StudentDTO studentDTO) {
		studentRepository.findByEnrolment(studentDTO.getEnrolment()).ifPresent(student -> {
			throw new EnrolmentAlreadyExistsException(
					String.format("La matricula %s ya está registrado", studentDTO.getEnrolment()), student.getEnrolment(),
					student.getId());
		});
	}
}
