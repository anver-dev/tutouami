package mx.uam.ayd.proyecto.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public AlumnoDto actualizarAlumno(Long idAlumno, AlumnoDto alumnoDto) {
		
		//Buscar en la BD el alumno
		Optional<Alumno> opAlumno = alumnoRepository.findById(idAlumno);
		if(opAlumno.isEmpty())
			throw new IllegalArgumentException("No se encontró el alumno");
		
		Alumno alumno = opAlumno.get();
		
		Optional<Carrera> opCarrera = carreraRepository.findById(alumnoDto.getCarrera());
		if(opCarrera.isEmpty())
			throw new IllegalArgumentException("No se encontró la carrera");
		Carrera carrera = opCarrera.get();
		
		alumno.setNombre(alumnoDto.getNombre());
		alumno.setApellidoPaterno(alumnoDto.getApellidoPaterno());
		alumno.setApellidoMaterno(alumnoDto.getApellidoMaterno());
		alumno.setCarrera(carrera);
		alumno.setCorreo(alumnoDto.getCorreo());
		alumno.setPuntuacion(alumnoDto.getPuntuacion());
		alumno.setContrasenia(alumnoDto.getContrasenia());
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

}
