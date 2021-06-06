package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.AlumnoRepository;
import mx.uam.ayd.proyecto.datos.AsesoriaRepository;
import mx.uam.ayd.proyecto.datos.MateriaRepository;
import mx.uam.ayd.proyecto.dto.AsesoriaDto;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.negocio.modelo.Asesoria;
import mx.uam.ayd.proyecto.negocio.modelo.Materia;

@Service
public class ServicioAsesoria {
	
	@Autowired 
	private AsesoriaRepository asesoriaRepository;
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Autowired
	private MateriaRepository materiaRepository;
	/**
	 * En este metodo se recuperan las asesorias
	 * @param id 
	 * 
	 * @return lista de asesorias
	 */
	public List<AsesoriaDto> recuperaAsesorias(Long id) {
		
		List<AsesoriaDto> asesorias = new ArrayList<AsesoriaDto>();
		for (Asesoria asesoria : asesoriaRepository.findAll()) {
			if(id == asesoria.getIdAlumno() )
				asesorias.add(AsesoriaDto.creaAsesoriaDto(asesoria));
		}
		
		return asesorias;
	}

	/**
	 * Metodo para acualizar una asesoria
	 * @param idAsesoria 
	 * @param idAlumno
	 * @param asesoriaDto
	 * @return regreso dto con los cambios
	 */
	public AsesoriaDto actualizar(Long idAsesoria,Long idAlumno, AsesoriaDto asesoriaDto) {
		
		Optional<Asesoria> optionalAsesoria = asesoriaRepository.findById(idAsesoria);
		
		if(optionalAsesoria.isEmpty()) 
			throw new IllegalArgumentException("No se encontró la asesoria");
		Asesoria asesoria = optionalAsesoria.get();
		
		Optional<Alumno> optionalAlumno = alumnoRepository.findById(idAlumno);
		
		if(optionalAlumno.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el alumno");
		}
		Alumno alumno = optionalAlumno.get();		
		
		Optional<Materia> optMateria = materiaRepository.findById(asesoriaDto.getMateria());
		
		if(optMateria.isEmpty()) {
			throw new IllegalArgumentException("No se encontró la materia");
		}
		
		Materia materia = optMateria.get();
		
		asesoria.setDia(asesoriaDto.getDia());
		asesoria.setTipo(asesoriaDto.getTipo());
		asesoria.setDetalles(asesoriaDto.getDetalles());
		asesoria.setHoraInicio(asesoriaDto.getHoraInicio());
		asesoria.setHoraTermino(asesoriaDto.getHoraTermino());
		asesoria.setCosto(asesoriaDto.getCosto());
		asesoria.setUbicacion(asesoriaDto.getUbicacion());
		asesoria.setIdAlumno(alumno.getIdAlumno());
		asesoria.setMateria(materia);
		
		asesoria = asesoriaRepository.save(asesoria);
		
		materia.addAsesoria(asesoria);
		materiaRepository.save(materia);
		
		alumno.addAsesoria(asesoria);
		alumnoRepository.save(alumno);
		
		return AsesoriaDto.creaAsesoriaDto(asesoria);
	}
	
	/**
	 * Agregar una nueva asesoria
	 * 
	 * @param asesoriaDto
	 * @param id
	 * @return
	 */
	public AsesoriaDto agregaAsesoria(AsesoriaDto asesoriaDto, Long id) {

		// Vemos si esta en la BD el alumno
		Optional<Alumno> optAlumno = alumnoRepository.findById(id);
		
		if(optAlumno.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el alumno");
		}
		
		Alumno alumno = optAlumno.get();
		
		Optional<Materia> optMateria = materiaRepository.findById(asesoriaDto.getMateria());
		
		if(optMateria.isEmpty()) {
			throw new IllegalArgumentException("No se encontró la materia");
		}
		
		Materia materia = optMateria.get();
		
		Asesoria asesoria = new Asesoria();

		asesoria.setDia(asesoriaDto.getDia());
		asesoria.setTipo(asesoriaDto.getTipo());
		asesoria.setDetalles(asesoriaDto.getDetalles());
		asesoria.setHoraInicio(asesoriaDto.getHoraInicio());
		asesoria.setHoraTermino(asesoriaDto.getHoraTermino());
		asesoria.setCosto(asesoriaDto.getCosto());
		asesoria.setUbicacion(asesoriaDto.getUbicacion());
		asesoria.setMateria(materia);
		asesoria.setIdAlumno(alumno.getIdAlumno());
		asesoria = asesoriaRepository.save(asesoria);
		
		materia.addAsesoria(asesoria);
		materiaRepository.save(materia);
		
		alumno.addAsesoria(asesoria);
		alumnoRepository.save(alumno);
		
		return AsesoriaDto.creaAsesoriaDto(asesoria);
	}
}
