package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.datos.AlumnoRepository;
import mx.uam.ayd.proyecto.dto.AlumnoDto;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.servicios.AlumnoRestController;

@Service
@Slf4j
public class ServicioAlumno {

	@Autowired 
	private AlumnoRepository alumnoRepository;

	
	
	/**
	 * 
	 * @param id
	 * @return alumno con el id
	 */
	public AlumnoDto retrieve(Long id){
		Optional<Alumno> optAlumno = alumnoRepository.findById(id);
		log.info("Recuperando alumno con id: "+ id);
		Alumno alumno = optAlumno.get();
		return AlumnoDto.creaDto(alumno);
		
	}
	
	/**
	 * Elimina a un alumno con un determinado id
	 * 
	 * @param id
	 * @return
	 */

	public boolean delete(Long id) {
		alumnoRepository.deleteById(id);
		Optional <Alumno> optAlumno = alumnoRepository.findById(id);
		return optAlumno.isPresent();
	}
	

	
	
	public List<AlumnoDto> recuperaAlumnos() {
		List<AlumnoDto> alumnoDto = new ArrayList<>();

		for (Alumno alummno : alumnoRepository.findAll()) {
			alumnoDto.add(AlumnoDto.creaDto(alummno));
		}

		return alumnoDto;
	}
}
