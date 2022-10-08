package mx.tutouami.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.tutouami.model.dto.InscriptionDTO;
import mx.tutouami.model.entity.Advice;
import mx.tutouami.model.entity.Inscription;
import mx.tutouami.model.entity.Student;
import mx.tutouami.repository.InscriptionRepository;

@Service
public class InscriptionServiceImpl {
	@SuppressWarnings("unused")
	@Autowired
	private AdviceServiceImpl servicioAsesoria;
	
	@Autowired
	private StudentServiceImpl servicioAlumno;
	
	@Autowired
	private InscriptionRepository inscripcionRepository;
	
	public Optional<InscriptionDTO> actualizarEstado(Long idInscripcion, Long idAlumno, @Valid String estado) {
		Optional<Inscription> opInscripcion = inscripcionRepository.findById(idInscripcion);
		
		if(!opInscripcion.isPresent())
			return Optional.empty();
		
		//Optional<Student> opAlumno = servicioAlumno.obtenerAlumnoPorId(idAlumno);
		
		Optional<Student> opAlumno = java.util.Optional.empty();
		if(!opAlumno.isPresent())
			return Optional.empty();
		
		Student alumno = opAlumno.get();
		
		Inscription inscripcion = opInscripcion.get();
		alumno.addInscription(inscripcion);
		
		inscripcion.setEstado(estado);
		inscripcion.setFechaActualizacion(obtenerFechaActual());
		
		inscripcionRepository.save(inscripcion);
		//servicioAlumno.guardarAlumno(alumno);
		
		return Optional.of(InscriptionDTO.creaInscripcionDto(inscripcion));
	}

	public List<InscriptionDTO> recuperaInscripcionesPorAlumno(Long idAlumno) {
		//Optional<Student> opAlumno = servicioAlumno.obtenerAlumnoPorId(idAlumno);
		Optional<Student> opAlumno = java.util.Optional.empty();
		if(!opAlumno.isPresent())
			return null;
		
		Student alumno = opAlumno.get();
		
		List<Inscription> inscripciones = inscripcionRepository.findByAlumno(alumno);
		List<InscriptionDTO> inscripcionesDto = new ArrayList<InscriptionDTO>();
		
		for (Inscription inscripcion : inscripciones) {
			inscripcionesDto.add(InscriptionDTO.creaInscripcionDto(inscripcion));
		}
		
		return inscripcionesDto;
	}

	public InscriptionDTO agregaInscripcion(Long idAsesoria, Long idAlumno) {
		//Optional<Student> opAlumno = servicioAlumno.obtenerAlumnoPorId(idAlumno);
		Optional<Student> opAlumno = java.util.Optional.empty();
		//Optional<Advice> optionalAsesoria = servicioAsesoria.findById(idAsesoria);
		
		Advice asesoria = new Advice();
		Student alumno = opAlumno.get();
		Inscription inscripcion = new Inscription();
		
		inscripcion.setEstado("PENDIENTE");
		inscripcion.setFechaCreacion(obtenerFechaActual());
		inscripcion.setAsesoria(asesoria);
		inscripcion.setAlumno(alumno);
		
		inscripcionRepository.save(inscripcion);
		
		alumno.addInscription(inscripcion);
		//servicioAlumno.guardarAlumno(alumno);
		
		asesoria.setInscription(inscripcion);
		//servicioAsesoria.create(asesoria);
		
		return InscriptionDTO.creaInscripcionDto(inscripcion);
	}

	private String obtenerFechaActual() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaActual = dateFormat.format(date);
		
		return fechaActual;
	}

	public boolean eliminaInscripcion(Inscription inscripcion) {
		Optional<Inscription> opInscripcion = inscripcionRepository.findById(inscripcion.getIdInscripcion());
		
		if(!opInscripcion.isPresent())
			return false;
		
		Inscription inscripcionGuardada = opInscripcion.get();
		
		Student alumno = inscripcionGuardada.getAlumno();
		
		alumno.getInscriptions().remove(inscripcionGuardada);
		//servicioAlumno.guardarAlumno(alumno);
		
		inscripcionRepository.delete(inscripcionGuardada);
		
		return true;
	}

}
