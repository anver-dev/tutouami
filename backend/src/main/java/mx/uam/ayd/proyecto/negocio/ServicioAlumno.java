package mx.uam.ayd.proyecto.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.AlumnoRepository;
import mx.uam.ayd.proyecto.datos.CarreraRepository;
import mx.uam.ayd.proyecto.dto.AlumnoDto;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.negocio.modelo.Carrera;

@Service
public class ServicioAlumno {
	@Autowired
	private AlumnoRepository alumnoRepository;
	@Autowired
	private CarreraRepository carreraRepository;

	private BCryptPasswordEncoder contraseniaEncoder;

	public AlumnoDto actualizarAlumno(Long idAlumno, AlumnoDto alumnoDto) {

		// Buscar en la BD el alumno
		Optional<Alumno> opAlumno = alumnoRepository.findById(idAlumno);
		if (opAlumno.isEmpty())
			throw new IllegalArgumentException("No se encontró el alumno");

		Alumno alumno = opAlumno.get();

		Optional<Carrera> opCarrera = carreraRepository.findById(alumnoDto.getCarrera());
		if (opCarrera.isEmpty())
			throw new IllegalArgumentException("No se encontró la carrera");
		Carrera carrera = opCarrera.get();

		alumno.setNombre(alumnoDto.getNombre());
		alumno.setApellidoPaterno(alumnoDto.getApellidoPaterno());
		alumno.setApellidoMaterno(alumnoDto.getApellidoMaterno());
		alumno.setCarrera(carrera);
		alumno.setCorreo(alumnoDto.getCorreo());
		alumno.setPuntuacion(alumnoDto.getPuntuacion());
		alumno.setContrasenia(creaContrasenia(alumnoDto.getContrasenia()));
		alumno.setEdad(alumnoDto.getEdad());
		alumno.setTelefono(alumnoDto.getTelefono());
		alumno.setDescripcion(alumnoDto.getDescripcion());
		alumno.setCv(alumnoDto.getCv());
		alumno.setEstado(alumnoDto.getEstado());
		alumno.setTotalPuntuaciones(alumnoDto.getTotalPuntuaciones());
		alumno.setTrimestre(alumnoDto.getTrimestre());

		alumno = alumnoRepository.save(alumno);

		carrera.addAlumno(alumno);
		carreraRepository.save(carrera);

		return AlumnoDto.creaAlumnoDto(alumno);
	}

	public AlumnoDto agregarAlumno(AlumnoDto alumnoDto) {
		Alumno opAlumno = alumnoRepository.findByCorreo(alumnoDto.getCorreo());

		if (opAlumno != null)
			throw new IllegalArgumentException("El alumno ya existe");

		Optional<Carrera> opCarrera = carreraRepository.findById(alumnoDto.getCarrera());
		if (opCarrera.isEmpty()) {
			throw new IllegalArgumentException("No se encontró la carrera");
		}

		Carrera carrera = opCarrera.get();
		Alumno alumno = new Alumno();

		alumno.setNombre(alumnoDto.getNombre());
		alumno.setApellidoPaterno(alumnoDto.getApellidoPaterno());
		alumno.setApellidoMaterno(alumnoDto.getApellidoMaterno());
		alumno.setEdad(alumnoDto.getEdad());
		alumno.setCorreo(alumnoDto.getCorreo());
		alumno.setContrasenia(creaContrasenia(alumnoDto.getContrasenia()));
		alumno.setTelefono(alumnoDto.getTelefono());
		alumno.setCv(alumnoDto.getCv());
		alumno.setTrimestre(alumnoDto.getTrimestre());
		alumno.setPuntuacion(alumnoDto.getPuntuacion());
		alumno.setTotalPuntuaciones(alumnoDto.getTotalPuntuaciones());
		alumno.setDescripcion(alumnoDto.getDescripcion());
		alumno.setEstado(alumnoDto.getEstado());
		alumno.setCarrera(carrera);

		Alumno alumnoCreado = alumnoRepository.save(alumno);

		carrera.addAlumno(alumnoCreado);
		carreraRepository.save(carrera);

		alumnoDto = AlumnoDto.creaAlumnoDto(alumnoCreado);

		return alumnoDto;
	}

	public Optional<Alumno> obtenerAlumnoPorCorreoYContrasenia(String correo, String contrasenia) {
		
		Alumno alumno = alumnoRepository.findByCorreo(correo);
		Optional<Alumno> opAlumno;
		if(alumno != null) 
			opAlumno = Optional.of(alumno);
		else 
			return Optional.empty();
		
		if (!validaContrasenia(alumno.getContrasenia(), contrasenia))
			return Optional.empty();

		return opAlumno;
	}

	public Optional<Alumno> obtenerAlumnoPorId(Long idAlumno) {
		return alumnoRepository.findById(idAlumno);
	}
	
	public Alumno guardarAlumno(Alumno alumno) {
		return alumnoRepository.save(alumno);
	}

	private String creaContrasenia(String contrasenia) {
		contraseniaEncoder = new BCryptPasswordEncoder();
		String encodedContrasenia = contraseniaEncoder.encode(contrasenia);
		return encodedContrasenia;
	}

	private boolean validaContrasenia(String contraseniaGuardada, String contraseniaAValidar) {
		return contraseniaEncoder.matches(contraseniaAValidar, contraseniaGuardada);
	}

}
