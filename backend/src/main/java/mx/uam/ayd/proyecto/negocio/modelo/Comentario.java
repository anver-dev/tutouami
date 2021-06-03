package mx.uam.ayd.proyecto.negocio.modelo;

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
public class Comentario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idComentario;
	private String contenido;
	private String fechaCreacion;
	
	@ManyToOne
    private Alumno alumno;
	
	@ManyToOne
    private Asesoria asesoria;
}
