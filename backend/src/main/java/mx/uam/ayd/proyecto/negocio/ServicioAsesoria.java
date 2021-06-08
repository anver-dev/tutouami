package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.datos.AlumnoRepository;
import mx.uam.ayd.proyecto.datos.AsesoriaRepository;
import mx.uam.ayd.proyecto.datos.MateriaRepository;
import mx.uam.ayd.proyecto.dto.AlumnoDto;
import mx.uam.ayd.proyecto.dto.AsesoriaDto;
import mx.uam.ayd.proyecto.dto.MateriaDto;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.negocio.modelo.Asesoria;
import mx.uam.ayd.proyecto.negocio.modelo.Materia;

@Service
@Slf4j
public class ServicioAsesoria {
	
	@Autowired 
	private AsesoriaRepository asesoriaRepository;
	
	@Autowired 
	private AlumnoRepository alumnoRepository;
	
	@Autowired 
	private MateriaRepository materiaRepository;

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
	 * Recuperar las asesorias de un usuario 
	 * 
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
	 * 
	 * Acualizar una asesoria
	 * 
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
		asesoria.setUrl(asesoriaDto.getUrl());
		asesoria.setTotalPuntuaciones(asesoriaDto.getTotalPuntuaciones());
		asesoria.setPuntuacion(asesoriaDto.getPuntuacion());
		asesoria.setEstado(asesoriaDto.getEstado());
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
