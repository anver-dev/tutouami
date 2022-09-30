package mx.tutouami.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * Entidad de negocio Comentario
 * 
 * @author anver
 *
 */
@Entity
@Data
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idComentario;
	private String contenido;
	private String fechaCreacion;
	
	private Long idAlumno;
	private Long idAsesoria;
	
	@ManyToOne
    private Student alumno;
	
	@ManyToOne
    private Advice asesoria;
	
	
	
}
