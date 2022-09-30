package mx.tutouami.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Data;

/**
 * Entidad de negocio Asesoria
 * 
 * @author anver
 *
 */
@Entity
@Data
public class Advice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idAsesoria;
	
	private String dia;
	private String horaInicio;
	private String horaTermino;
	private float puntuacion;
	private int totalPuntuaciones;
	private String detalles;
	private String tipo;
	private String ubicacion;
	private float costo;
	private String url;
	private String estado;

	private Long idAlumno;

	
	@ManyToOne
    private Subject materia;
	
	@OneToOne
    private Inscription inscripcion;
	

	@ManyToOne
	private Comment comentario;
	

	@JsonIgnore
	@OneToMany(targetEntity = Comment.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "idAsesoria")
	private final List<Comment> comentarios = new ArrayList<>();
	
	
	/**
	 * 
	 * Permite agregar una comentario al alumno
	 * 
	 * @param comentario el comentario que deseo agregar al alumno
	 * @return true si el comentario se agregó correctamente, false si no
	 * @throws IllegalArgumentException si el comentario es nulo
	 */
	public boolean addComentario(Comment comentario) {

		if (comentario == null) {
			throw new IllegalArgumentException("El usuario no puede ser null");
		}

		if (comentarios.contains(comentario)) {
			return false;
		}

		return comentarios.add(comentario);

	}
}
