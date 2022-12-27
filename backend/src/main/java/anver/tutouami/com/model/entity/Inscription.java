package anver.tutouami.com.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

/**
 * Entidad de negocio Inscripcion
 * 
 * @author anver
 *
 */
@Entity
@Data
public class Inscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idInscripcion;
	private String estado;
	private String fechaCreacion;
	private String detalles;
	private String fechaActualizacion;
	
	 @ManyToOne
	 private Student alumno;
	 
	 @OneToOne
	 private Advice asesoria;
}
