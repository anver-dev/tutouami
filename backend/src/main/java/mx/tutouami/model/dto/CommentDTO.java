package mx.tutouami.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import mx.tutouami.entity.Comment;

/**
 * DTO de comentarios
 * 
 */
@Data
public class CommentDTO {
	
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
	
	public static CommentDTO creaComentarioDto(Comment comentario) {
		CommentDTO dto = new CommentDTO();
		
		dto.setIdComentario(comentario.getIdComentario());
		dto.setContenido(comentario.getContenido());
		dto.setFechaCreacion(comentario.getFechaCreacion());
		dto.setAlumno(comentario.getIdAlumno());
		dto.setAsesoria(comentario.getIdAsesoria());
		
		
		return dto;
	}
	
}
