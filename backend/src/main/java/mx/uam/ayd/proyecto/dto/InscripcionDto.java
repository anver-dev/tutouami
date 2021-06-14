package mx.uam.ayd.proyecto.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.negocio.modelo.Asesoria;
import mx.uam.ayd.proyecto.negocio.modelo.Inscripcion;

/**
 * DTO de inscripciones
 * 
 */
@Data
public class InscripcionDto {
	
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
	private Alumno alumno;
	
	@JsonIgnore
	@NotEmpty(message = "El campo no debe ser vacio")
	private Asesoria asesoria;
	
	public static InscripcionDto creaInscripcionDto(Inscripcion inscripcion) {
		InscripcionDto dto = new InscripcionDto();
		
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
