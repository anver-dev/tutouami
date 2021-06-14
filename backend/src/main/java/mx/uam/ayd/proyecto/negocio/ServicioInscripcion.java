package mx.uam.ayd.proyecto.negocio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.InscripcionRepository;
import mx.uam.ayd.proyecto.dto.InscripcionDto;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.negocio.modelo.Asesoria;
import mx.uam.ayd.proyecto.negocio.modelo.Inscripcion;

@Service
public class ServicioInscripcion {
	@SuppressWarnings("unused")
	@Autowired
	private ServicioAsesoria servicioAsesoria;
	
	@Autowired
	private ServicioAlumno servicioAlumno;
	
	@Autowired
	private InscripcionRepository inscripcionRepository;
	
	public Optional<InscripcionDto> actualizarEstado(Long idInscripcion, Long idAlumno, @Valid String estado) {
		Optional<Inscripcion> opInscripcion = inscripcionRepository.findById(idInscripcion);
		
		if(!opInscripcion.isPresent())
			return Optional.empty();
		
		Optional<Alumno> opAlumno = servicioAlumno.obtenerAlumnoPorId(idAlumno);
		
		if(!opAlumno.isPresent())
			return Optional.empty();
		
		Alumno alumno = opAlumno.get();
		
		Inscripcion inscripcion = opInscripcion.get();
		alumno.addInscripcion(inscripcion);
		
		inscripcion.setEstado(estado);
		inscripcion.setFechaActualizacion(obtenerFechaActual());
		
		inscripcionRepository.save(inscripcion);
		servicioAlumno.guardarAlumno(alumno);
		
		return Optional.of(InscripcionDto.creaInscripcionDto(inscripcion));
	}

	public List<InscripcionDto> recuperaInscripcionesPorAlumno(Long idAlumno) {
		Optional<Alumno> opAlumno = servicioAlumno.obtenerAlumnoPorId(idAlumno);
		
		if(!opAlumno.isPresent())
			return null;
		
		Alumno alumno = opAlumno.get();
		
		List<Inscripcion> inscripciones = inscripcionRepository.findByAlumno(alumno);
		List<InscripcionDto> inscripcionesDto = new ArrayList<InscripcionDto>();
		
		for (Inscripcion inscripcion : inscripciones) {
			inscripcionesDto.add(InscripcionDto.creaInscripcionDto(inscripcion));
		}
		
		return inscripcionesDto;
	}

	public InscripcionDto agregaInscripcion(Long idAsesoria, Long idAlumno) {
		Optional<Alumno> opAlumno = servicioAlumno.obtenerAlumnoPorId(idAlumno);
		Optional<Asesoria> optionalAsesoria = servicioAsesoria.obtenerAsesoriaPorId(idAsesoria);
		
		if(!opAlumno.isPresent() || !optionalAsesoria.isPresent())
			return null;
		
		Asesoria asesoria = optionalAsesoria.get();
		Alumno alumno = opAlumno.get();
		Inscripcion inscripcion = new Inscripcion();
		
		inscripcion.setEstado("PENDIENTE");
		inscripcion.setFechaCreacion(obtenerFechaActual());
		inscripcion.setAsesoria(asesoria);
		inscripcion.setAlumno(alumno);
		
		inscripcionRepository.save(inscripcion);
		
		alumno.addInscripcion(inscripcion);
		servicioAlumno.guardarAlumno(alumno);
		
		asesoria.setInscripcion(inscripcion);
		servicioAsesoria.guardarAsesoria(asesoria);
		
		return InscripcionDto.creaInscripcionDto(inscripcion);
	}

	private String obtenerFechaActual() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaActual = dateFormat.format(date);
		
		return fechaActual;
	}

	public boolean eliminaInscripcion(Inscripcion inscripcion) {
		Optional<Inscripcion> opInscripcion = inscripcionRepository.findById(inscripcion.getIdInscripcion());
		
		if(!opInscripcion.isPresent())
			return false;
		
		Inscripcion inscripcionGuardada = opInscripcion.get();
		
		Alumno alumno = inscripcionGuardada.getAlumno();
		
		alumno.getInscripciones().remove(inscripcionGuardada);
		servicioAlumno.guardarAlumno(alumno);
		
		inscripcionRepository.delete(inscripcionGuardada);
		
		return true;
	}

}
