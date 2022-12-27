package anver.tutouami.com.model.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import anver.tutouami.com.model.entity.Advice;
import anver.tutouami.com.model.entity.Inscription;
import anver.tutouami.com.model.entity.Student;
import lombok.Data;

/**
 * DTO de inscripciones
 * 
 */
@Data
public class InscriptionDTO {
	
	private long idInscripcion;
	
	@NotEmpty(message = "El campo no debe ser vacio")
	private String estado;
	
	@NotEmpty(message = "El campo no debe ser vacio")
	private String fechaCreacion;
	
	@NotEmpty(message = "El campo no debe ser vacio")
	private String detalles;

	private String fechaActualizacion;
	
	@JsonIgnore
	@NotEmpty(message = "El campo no debe ser vacio")
	private Student alumno;
	
	@JsonIgnore
	@NotEmpty(message = "El campo no debe ser vacio")
	private Advice asesoria;
	
	public static InscriptionDTO creaInscripcionDto(Inscription inscripcion) {
		InscriptionDTO dto = new InscriptionDTO();
		
		dto.setIdInscripcion(inscripcion.getIdInscripcion());
		dto.setEstado(inscripcion.getEstado());
		dto.setFechaCreacion(inscripcion.getFechaCreacion());
		dto.setDetalles(inscripcion.getDetalles());
		dto.setFechaActualizacion(inscripcion.getFechaActualizacion());
		dto.setAlumno(inscripcion.getAlumno());
		dto.setAsesoria(inscripcion.getAsesoria());
		
		
		return dto;
	}
}
