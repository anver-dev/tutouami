package mx.tutouami.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.tutouami.model.dto.StudentDTO;
import mx.tutouami.repository.StudentRepository;
import mx.tutouami.service.IStudentService;

@Slf4j
@Service
public class StudentServiceImpl implements IStudentService {

	@Autowired
	private StudentRepository studentRepository;

	//private BCryptPasswordEncoder contraseniaEncoder;

	/**
	 * public StudentDTO actualizarAlumno(Long idAlumno, StudentDTO alumnoDto) {
	 * 
	 * // Buscar en la BD el alumno Optional<Student> opAlumno =
	 * alumnoRepository.findById(idAlumno); if (opAlumno.isEmpty()) throw new
	 * IllegalArgumentException("No se encontró el alumno");
	 * 
	 * Student alumno = opAlumno.get();
	 * 
	 * Optional<Degree> opCarrera =
	 * carreraRepository.findById(alumnoDto.getCarrera()); if (opCarrera.isEmpty())
	 * throw new IllegalArgumentException("No se encontró la carrera"); Degree
	 * carrera = opCarrera.get();
	 * 
	 * alumno.setNombre(alumnoDto.getNombre());
	 * alumno.setApellidoPaterno(alumnoDto.getApellidoPaterno());
	 * alumno.setApellidoMaterno(alumnoDto.getApellidoMaterno());
	 * alumno.setCarrera(carrera); alumno.setCorreo(alumnoDto.getCorreo());
	 * alumno.setPuntuacion(alumnoDto.getPuntuacion());
	 * alumno.setContrasenia(creaContrasenia(alumnoDto.getContrasenia()));
	 * alumno.setEdad(alumnoDto.getEdad());
	 * alumno.setTelefono(alumnoDto.getTelefono());
	 * alumno.setDescripcion(alumnoDto.getDescripcion());
	 * alumno.setCv(alumnoDto.getCv()); alumno.setEstado(alumnoDto.getEstado());
	 * alumno.setTotalPuntuaciones(alumnoDto.getTotalPuntuaciones());
	 * alumno.setTrimestre(alumnoDto.getTrimestre());
	 * 
	 * alumno = alumnoRepository.save(alumno);
	 * 
	 * carrera.addAlumno(alumno); carreraRepository.save(carrera);
	 * 
	 * return StudentDTO.creaAlumnoDto(alumno); }
	 * 
	 * 
	 * public StudentDTO agregarAlumno(StudentDTO alumnoDto) { Student opAlumno =
	 * alumnoRepository.findByCorreo(alumnoDto.getCorreo());
	 * 
	 * if (opAlumno != null) throw new IllegalArgumentException("El alumno ya
	 * existe");
	 * 
	 * Optional<Degree> opCarrera =
	 * carreraRepository.findById(alumnoDto.getCarrera()); if (opCarrera.isEmpty())
	 * { throw new IllegalArgumentException("No se encontró la carrera"); }
	 * 
	 * Degree carrera = opCarrera.get(); Student alumno = new Student();
	 * 
	 * alumno.setNombre(alumnoDto.getNombre());
	 * alumno.setApellidoPaterno(alumnoDto.getApellidoPaterno());
	 * alumno.setApellidoMaterno(alumnoDto.getApellidoMaterno());
	 * alumno.setEdad(alumnoDto.getEdad()); alumno.setCorreo(alumnoDto.getCorreo());
	 * alumno.setContrasenia(creaContrasenia(alumnoDto.getContrasenia()));
	 * alumno.setTelefono(alumnoDto.getTelefono()); alumno.setCv(alumnoDto.getCv());
	 * alumno.setTrimestre(alumnoDto.getTrimestre());
	 * alumno.setPuntuacion(alumnoDto.getPuntuacion());
	 * alumno.setTotalPuntuaciones(alumnoDto.getTotalPuntuaciones());
	 * alumno.setDescripcion(alumnoDto.getDescripcion());
	 * alumno.setEstado(alumnoDto.getEstado()); alumno.setCarrera(carrera);
	 * 
	 * Student alumnoCreado = alumnoRepository.save(alumno);
	 * 
	 * carrera.addAlumno(alumnoCreado); carreraRepository.save(carrera);
	 * 
	 * alumnoDto = StudentDTO.creaAlumnoDto(alumnoCreado);
	 * 
	 * return alumnoDto; }
	 * 
	 * public Optional<Student> obtenerAlumnoPorCorreoYContrasenia(String correo,
	 * String contrasenia) {
	 * 
	 * Student alumno = alumnoRepository.findByCorreo(correo); Optional<Student>
	 * opAlumno; if(alumno != null) opAlumno = Optional.of(alumno); else return
	 * Optional.empty();
	 * 
	 * if (!validaContrasenia(alumno.getContrasenia(), contrasenia)) return
	 * Optional.empty();
	 * 
	 * return opAlumno; }
	 * 
	 * public Optional<Student> obtenerAlumnoPorId(Long idAlumno) { return
	 * alumnoRepository.findById(idAlumno); }
	 * 
	 * public Student guardarAlumno(Student alumno) { return
	 * alumnoRepository.save(alumno); }
	 * 
	 * private String creaContrasenia(String contrasenia) { contraseniaEncoder = new
	 * BCryptPasswordEncoder(); String encodedContrasenia =
	 * contraseniaEncoder.encode(contrasenia); return encodedContrasenia; }
	 * 
	 * private boolean validaContrasenia(String contraseniaGuardada, String
	 * contraseniaAValidar) { return contraseniaEncoder.matches(contraseniaAValidar,
	 * contraseniaGuardada); }
	 * 
	 * 
	 * /**
	 * 
	 * @param id
	 * @return alumno con el id
	 * 
	 *         public StudentDTO retrieve(Long id){ Optional<Student> optAlumno =
	 *         alumnoRepository.findById(id); log.info("Recuperando alumno con id:
	 *         "+ id); Student alumno = optAlumno.get(); return
	 *         StudentDTO.creaDto(alumno);
	 * 
	 *         }
	 * 
	 *         /** Elimina a un alumno con un determinado id
	 * 
	 * @param id
	 * @return
	 * 
	 * 
	 *         public boolean delete(Long id) { alumnoRepository.deleteById(id);
	 *         Optional <Student> optAlumno = alumnoRepository.findById(id); return
	 *         optAlumno.isPresent(); }
	 * 
	 * 
	 * 
	 * 
	 *         public List<StudentDTO> recuperaAlumnos() { List<StudentDTO>
	 *         alumnoDto = new ArrayList<>();
	 * 
	 *         for (Student alummno : alumnoRepository.findAll()) {
	 *         alumnoDto.add(StudentDTO.creaDto(alummno)); }
	 * 
	 *         return alumnoDto; }
	 * 
	 **/
	@Override
	public List<StudentDTO> findAll() {
		log.info("Get all students");
		return studentRepository.findAll().stream().map(s -> StudentDTO.generate(s)).collect(Collectors.toList());
	}

	@Override
	public StudentDTO findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
