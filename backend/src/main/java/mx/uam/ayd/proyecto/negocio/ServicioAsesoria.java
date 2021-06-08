package mx.uam.ayd.proyecto.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.datos.AlumnoRepository;
import mx.uam.ayd.proyecto.datos.AsesoriaRepository;
import mx.uam.ayd.proyecto.datos.MateriaRepository;
import mx.uam.ayd.proyecto.dto.AsesoriaDto;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.negocio.modelo.Asesoria;
import mx.uam.ayd.proyecto.negocio.modelo.Materia;
import mx.uam.ayd.proyecto.servicios.AsesoriaRestController;
@Service
@Slf4j
public class ServicioAsesoria {
	
	@Autowired 
	private AsesoriaRepository asesoriaRepository;
	
	@Autowired 
	private AlumnoRepository alumnoRepository;
	
	@Autowired 
	private MateriaRepository materiaRepository;
	
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
		
		for(Asesoria asesorias: asesoriaRepository.findAll()) {
			if((id == asesorias.getIdAlumno()) & (asesoriaDto.getDia().equals(asesorias.getDia())) & (asesoriaDto.getHoraInicio().equals(asesorias.getHoraInicio())) & (asesoriaDto.getHoraTermino().equals(asesorias.getHoraTermino()))) {
				throw new IllegalArgumentException("No se puede repetir");
			} 
		}

		asesoria = asesoriaRepository.save(asesoria);
	
		materia.addAsesoria(asesoria);
		materiaRepository.save(materia);
		
		alumno.addAsesoria(asesoria);
		alumnoRepository.save(alumno);
	
		return AsesoriaDto.creaAsesoriaDto(asesoria);
	}
	
	/**
	 * Se elimina una asesoria con su id
	 * 
	 * @param idAsesoria el id de la asesoria
	 * @param idAlumno el id del usuario
	 */
	public void eliminarAsesoria(Long idAsesoria, Long idAlumno) {
		
		Optional<Asesoria> optAsesoria = asesoriaRepository.findById(idAsesoria);
		if(optAsesoria.isEmpty())
			throw new IllegalArgumentException("La asessoria no existe");
		
		
		Optional<Alumno> optAlumno = alumnoRepository.findById(idAlumno);
		if(optAlumno.isEmpty())
			throw new IllegalArgumentException("El alumno no esta en la BD");
		
		if(optAsesoria.get().getIdAlumno() == optAlumno.get().getIdAlumno()) {
			log.info("Se va a eliminar la asesoria con id: "+ idAsesoria);
			asesoriaRepository.deleteById(idAsesoria);
		}
		
	}
	
	/**
	 * Regresa una asessoria en especial
	 * 
	 * @param idAsesoria
	 * @return
	 */
	public AsesoriaDto retrieve(Long idAsesoria) {
		Optional<Asesoria> optAsesoria = asesoriaRepository.findById(idAsesoria);
		return AsesoriaDto.creaAsesoriaDto(optAsesoria.get());
	}

}
