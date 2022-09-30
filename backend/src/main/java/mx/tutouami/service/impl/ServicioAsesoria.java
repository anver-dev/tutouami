package mx.tutouami.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.tutouami.entity.Advice;
import mx.tutouami.entity.Student;
import mx.tutouami.entity.Subject;
import mx.tutouami.model.dto.AdviceDTO;
import mx.tutouami.repository.StudentRepository;
import mx.tutouami.repository.AsesoriaRepository;
import mx.tutouami.repository.MateriaRepository;


@Service
@Slf4j
public class ServicioAsesoria {
	
	@Autowired 
	private AsesoriaRepository asesoriaRepository;
	
	@Autowired 
	private StudentRepository alumnoRepository;
	
	@Autowired 
	private MateriaRepository materiaRepository;
	

	/**
	 * Recuperar las asesorias de un usuario 
	 * 
	 * @param id 
	 * 
	 * @return lista de asesorias
	 */
	public List<AdviceDTO> recuperaAsesorias(Long id) {
		
		List<AdviceDTO> asesorias = new ArrayList<AdviceDTO>();
		for (Advice asesoria : asesoriaRepository.findAll()) {
			if(id == asesoria.getIdAlumno() )
				asesorias.add(AdviceDTO.creaAsesoriaDto(asesoria));
		}
		
		return asesorias;
	}

	/**
	 * 
	 * Acualizar una asesoria
	 * 
	 * @param idAsesoria 
	 * @param idAlumno
	 * @param asesoriaDto
	 * @return regreso dto con los cambios
	 */
	public AdviceDTO actualizar(Long idAsesoria,Long idAlumno, AdviceDTO asesoriaDto) {
		
		Optional<Advice> optionalAsesoria = asesoriaRepository.findById(idAsesoria);
		
		if(optionalAsesoria.isEmpty()) 
			throw new IllegalArgumentException("No se encontró la asesoria");
		Advice asesoria = optionalAsesoria.get();
		
		Optional<Student> optionalAlumno = alumnoRepository.findById(idAlumno);
		
		if(optionalAlumno.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el alumno");
		}
		Student alumno = optionalAlumno.get();		
		
		Optional<Subject> optMateria = materiaRepository.findById(asesoriaDto.getMateria());
		
		if(optMateria.isEmpty()) {
			throw new IllegalArgumentException("No se encontró la materia");
		}
		
		Subject materia = optMateria.get();
		
		asesoria.setDia(asesoriaDto.getDia());
		asesoria.setTipo(asesoriaDto.getTipo());
		asesoria.setDetalles(asesoriaDto.getDetalles());
		asesoria.setHoraInicio(asesoriaDto.getHoraInicio());
		asesoria.setHoraTermino(asesoriaDto.getHoraTermino());
		asesoria.setCosto(asesoriaDto.getCosto());
		asesoria.setUbicacion(asesoriaDto.getUbicacion());
		asesoria.setUrl(asesoriaDto.getUrl());
		asesoria.setTotalPuntuaciones(asesoriaDto.getTotalPuntuaciones());
		asesoria.setPuntuacion(asesoriaDto.getPuntuacion());
		asesoria.setEstado(asesoriaDto.getEstado());
		asesoria.setMateria(materia);
		asesoria.setIdAlumno(alumno.getId());
		asesoria = asesoriaRepository.save(asesoria);
		
		materia.addAsesoria(asesoria);
		materiaRepository.save(materia);
		
		alumno.addAdvice(asesoria);
		alumnoRepository.save(alumno);
		
		return AdviceDTO.creaAsesoriaDto(asesoria);
	}
	
	/**
	 * Agregar una nueva asesoria
	 * 
	 * @param asesoriaDto
	 * @param id
	 * @return
	 */
	public AdviceDTO agregaAsesoria(AdviceDTO asesoriaDto, Long id) {
		
		 /*
		for (Asesoria asesoria : asesoriaRepository.findAll()) {
			if( (asesoriaDto.getDia() == asesoria.getDia()) && (asesoriaDto.getHoraInicio() == asesoria.getHoraInicio()) && (id == asesoria.getIdAlumno()) ) {
				
				throw new IllegalArgumentException("La asesoria ya existe");
			}	
		}
		*/
		
		// Vemos si esta en la BD el alumno
		Optional<Student> optAlumno = alumnoRepository.findById(id);
		
		if(optAlumno.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el alumno");
		}
		
		Student alumno = optAlumno.get();
		
		Optional<Subject> optMateria = materiaRepository.findById(asesoriaDto.getMateria());
		
		if(optMateria.isEmpty()) {
			throw new IllegalArgumentException("No se encontró la materia");
		}
		
		Subject materia = optMateria.get();
		
		Advice asesoria = new Advice();

		asesoria.setDia(asesoriaDto.getDia());
		asesoria.setTipo(asesoriaDto.getTipo());
		asesoria.setDetalles(asesoriaDto.getDetalles());
		asesoria.setHoraInicio(asesoriaDto.getHoraInicio());
		asesoria.setHoraTermino(asesoriaDto.getHoraTermino());
		asesoria.setCosto(asesoriaDto.getCosto());
		asesoria.setUbicacion(asesoriaDto.getUbicacion());
		asesoria.setUrl(asesoriaDto.getUrl());
		asesoria.setTotalPuntuaciones(asesoriaDto.getTotalPuntuaciones());
		asesoria.setPuntuacion(asesoriaDto.getPuntuacion());
		asesoria.setEstado(asesoriaDto.getEstado());
		asesoria.setMateria(materia);
		asesoria.setIdAlumno(alumno.getId());
		asesoria = asesoriaRepository.save(asesoria);
		
		materia.addAsesoria(asesoria);
		materiaRepository.save(materia);
		
		alumno.addAdvice(asesoria);
		alumnoRepository.save(alumno);
		
		return AdviceDTO.creaAsesoriaDto(asesoria);
	}

	
	/**
	 * Recupera todas las asesorias 
	 * 
	 * @return
	 */
	public List<AdviceDTO> recuperaAsesorias() {
		List<AdviceDTO> asesoriasDto = new ArrayList<>();

		for (Advice asesoria : asesoriaRepository.findAll()) {
			asesoriasDto.add(AdviceDTO.creaAsesoriaDto(asesoria));
		}

		return asesoriasDto;
	}
	
	
	/**
	 * Se elimina una asesoria con su id
	 * 
	 * @param idAsesoria el id de la asesoria
	 * @param idAlumno el id del usuario
	 */
	public boolean eliminarAsesoria(AdviceDTO asesoria) {
		Optional<Advice> optionalAsesoriaGuardada = asesoriaRepository.findById(asesoria.getIdAsesoria());
		Advice asesoriaGuardada = optionalAsesoriaGuardada.get();
		
		log.info("Se va a eliminar la asesoria con id: "+ asesoriaGuardada.getIdAsesoria());
		
		if(asesoriaGuardada.getInscripcion() == null) {
			asesoriaRepository.delete(asesoriaGuardada);
			return true;
		} else {
			return false;
		}
		
		
	}

	/**
	 * 
	 * Se recuperan las asesorias que se imparten de una materia 
	 * 
	 * @param id
	 * @return
	 */
	public List<AdviceDTO> recuperaAsesoriasMateria(Long id) {
		
		List<AdviceDTO> asesorias = new ArrayList<AdviceDTO>();
		for (Advice asesoria : asesoriaRepository.findAll()) {
			if(id == asesoria.getMateria().getIdMateria())
				asesorias.add(AdviceDTO.creaAsesoriaDto(asesoria));
		}
		
		return asesorias;
	}
	
	/**
	 * Busca una asesoria en la BD por su id
	 * 
	 * @param idAsesoria
	 * @return
	 */
	public AdviceDTO buscarID(Long idAsesoria) {
		Optional<Advice> optAsesoria = asesoriaRepository.findById(idAsesoria);
		return AdviceDTO.creaAsesoriaDto(optAsesoria.get());
	}

	public Optional<AdviceDTO> actualizarEstado(Long idAsesoria, Long idAlumno, @Valid String estado) {
		Optional<Advice> optionalAsesoria = asesoriaRepository.findById(idAsesoria);
		
		if(!optionalAsesoria.isPresent())
			return Optional.empty();
		
		Advice asesoria = optionalAsesoria.get();
		Optional<Student> optionalAlumno = alumnoRepository.findById(idAlumno);
		
		if(!optionalAlumno.isPresent())
			return Optional.empty();
		
		Student alumno = optionalAlumno.get(); 
		Optional<Subject> optMateria = materiaRepository.findById(asesoria.getMateria().getIdMateria());
		Subject materia = optMateria.get();
		
		asesoria.setEstado(estado);
		
		materia.addAsesoria(asesoria);
		materiaRepository.save(materia);
		
		alumno.addAdvice(asesoria);
		alumnoRepository.save(alumno);
		
		return Optional.of(AdviceDTO.creaAsesoriaDto(asesoria));
		
	
	}

	public Optional<Advice> obtenerAsesoriaPorId(Long idAsesoria) {
		Optional<Advice> optionalAsesoria = asesoriaRepository.findById(idAsesoria);
		
		if(!optionalAsesoria.isPresent())
			return Optional.empty();
		
		return optionalAsesoria;
	}

	public Advice guardarAsesoria(Advice asesoria) {
		return asesoriaRepository.save(asesoria);
		
	}

	public Optional<AdviceDTO> actualizarPuntuacion(Long idAsesoria, @Valid Integer puntuacion) {
		Optional<Advice> optionalAsesoria = asesoriaRepository.findById(idAsesoria);
		
		if(!optionalAsesoria.isPresent())
			return Optional.empty();
		
		Advice asesoria = optionalAsesoria.get();
		
		int tmpTotalPuntuaciones = asesoria.getTotalPuntuaciones() + 1;
		float nuevaPuntuacion = (asesoria.getPuntuacion() + puntuacion)/ tmpTotalPuntuaciones;
		
		asesoria.setTotalPuntuaciones(tmpTotalPuntuaciones);
		asesoria.setPuntuacion(nuevaPuntuacion);
		
		asesoriaRepository.save(asesoria);
		
		return Optional.of(AdviceDTO.creaAsesoriaDto(asesoria));
	}
	
}
