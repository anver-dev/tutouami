package mx.uam.ayd.proyecto.negocio.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de negocio Alumno
 * 
 * @author anver
 *
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Alumno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idAlumno;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private int edad;
	private String correo;
	private String contrasenia;
	private String telefono;
	private String cv;
	private int trimestre;
	private float puntuacion;
	private int totalPuntuaciones;
	private String descripcion;
	private String estado;
	
	@ManyToOne
	private Carrera carrera;


	@OneToMany(targetEntity = Asesoria.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "idAlumno")
	@JsonIgnore
	private final List<Asesoria> asesorias = new ArrayList<>();

	@OneToMany(targetEntity = Inscripcion.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "idAlumno")
	@JsonIgnore
	private final List<Inscripcion> inscripciones = new ArrayList<>();

	
	@OneToMany(targetEntity = Comentario.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "idAlumno")
	@JsonIgnore
	private final List<Comentario> comentarios = new ArrayList<>();

	@Builder.Default
	@OneToMany(targetEntity = RefreshToken.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "usuario")
	@Fetch(value = FetchMode.SUBSELECT)
	private final List<RefreshToken> refreshTokens = new ArrayList<>();

	public boolean tieneElRefreshToken(RefreshToken refreshToken) {
		return refreshTokens.contains(refreshToken);
	}

	/**
	 * 
	 * Permite agregar una asesoria al alumno
	 * 
	 * @param asesoria la asesoria que deseo agregar al alumno
	 * @return true si la asesoria se agregó correctamente, false si no
	 * @throws IllegalArgumentException si la asesoria es nula
	 */
	public boolean addAsesoria(Asesoria asesoria) {

		if (asesoria == null) {
			throw new IllegalArgumentException("La asesoria no puede ser null");
		}

		if (asesorias.contains(asesoria)) {
			return false;
		}

		return asesorias.add(asesoria);

	}

	/**
	 * 
	 * Permite agregar una inscripcion al alumno
	 * 
	 * @param inscripcion la inscripcion que deseo agregar al alumno
	 * @return true si la inscripcion se agregó correctamente, false si no
	 * @throws IllegalArgumentException si la inscripcion es nula
	 */
	public boolean addInscripcion(Inscripcion inscripcion) {

		if (inscripcion == null) {
			throw new IllegalArgumentException("La inscripcion no puede ser null");
		}

		if (inscripciones.contains(inscripcion)) {
			return false;
		}

		return inscripciones.add(inscripcion);

	}

	/**
	 * 
	 * Permite agregar una comentario al alumno
	 * 
	 * @param comentario el comentario que deseo agregar al alumno
	 * @return true si el comentario se agregó correctamente, false si no
	 * @throws IllegalArgumentException si el comentario es nulo
	 */
	public boolean addComentario(Comentario comentario) {

		if (comentario == null) {
			throw new IllegalArgumentException("El comentario no puede ser null");
		}

		if (comentarios.contains(comentario)) {
			return false;
		}

		return comentarios.add(comentario);

	}
}
