package mx.uam.ayd.proyecto.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import mx.uam.ayd.proyecto.negocio.modelo.Asesoria;
import mx.uam.ayd.proyecto.negocio.modelo.Comentario;

/**
 * DTO de comentarios
 * 
 */
@Data
public class ComentarioDto {
	
	private long idComentario;
	
	@NotEmpty(message = "El campo no debe ser vacio")
	private String contenido;
	
	@NotEmpty(message = "El campo no debe ser vacio")
	private String fechaCreacion;
	
	@NotNull
	private long alumno;
	
	@NotNull
	private long asesoria;
	
	
	/**
	 * Este método permite generar un DTO a partir de la entidad
	 * nota: es un método de clase y no se necesita un objeto
	 * para invocarlo. Se invoca como ComentarioDto.crea(param)
     * @param usuario la entidad
	 * @return dto obtenido a partir de la entidad
	 */
	
	public static ComentarioDto creaComentarioDto(Comentario comentario) {
		ComentarioDto dto = new ComentarioDto();
		
		dto.setContenido(comentario.getContenido());
		dto.setFechaCreacion(comentario.getFechaCreacion());
		dto.setAlumno(comentario.getIdAlumno());
		dto.setAsesoria(comentario.getIdAsesoria());
		
		return dto;
	}
	
}
