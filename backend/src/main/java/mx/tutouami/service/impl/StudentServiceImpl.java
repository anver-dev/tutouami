package mx.tutouami.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.tutouami.entity.Student;
import mx.tutouami.exceptions.EmailAlreadyExistsException;
import mx.tutouami.exceptions.NotFoundException;
import mx.tutouami.model.dto.StudentDTO;
import mx.tutouami.model.enums.StudentStatusTypes;
import mx.tutouami.repository.StudentRepository;
import mx.tutouami.service.IStudentService;

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
				.degree(studentDTO.getDegree()).description(studentDTO.getDescription()).email(studentDTO.getEmail())
				.lastName(studentDTO.getLastName()).name(studentDTO.getName()).phone(studentDTO.getPhone()).score(0.00F)
				.secondLastName(studentDTO.getSecondLastName()).status(StudentStatusTypes.AVAILABLE.getValue())
				.trimester(studentDTO.getTrimester()).build();

		return StudentDTO.generate(studentRepository.save(studentToCreate));
	}

	private void validate(StudentDTO studentDTO) {
		studentRepository.findByEmail(studentDTO.getEmail()).ifPresent(student -> {
			throw new EmailAlreadyExistsException(
					String.format("El email %s ya está registrado", studentDTO.getEmail()), student.getEmail(),
					student.getId());
		});
	}
}
