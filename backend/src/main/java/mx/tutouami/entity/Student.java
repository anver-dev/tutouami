package mx.tutouami.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_student")
	private Long id;
	
	private Integer enrolment;
	
	private String name;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "second_last_name")
	private String secondLastName;

	private Integer age;
	private String phone;
	private String cv;
	private Integer trimester;
	private Float score;

	@Column(name = "total_score")
	private Integer totalScore;

	private String description;
	private String status;
	
	@Column(name = "create_at", updatable = false)
	@Temporal(TemporalType.DATE)
	private Date createAt;

	@ManyToOne
	private Degree degree;
	
	@OneToOne
	private Account account;

	@OneToMany(targetEntity = Advice.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_student")
	@JsonIgnore
	private final List<Advice> advices = new ArrayList<>();

	@OneToMany(targetEntity = Inscription.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_student")
	@JsonIgnore
	private final List<Inscription> inscriptions = new ArrayList<>();

	@OneToMany(targetEntity = Comment.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_student")
	@JsonIgnore
	private final List<Comment> comments = new ArrayList<>();

	@Builder.Default
	@OneToMany(targetEntity = RefreshToken.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user")
	@Fetch(value = FetchMode.SUBSELECT)
	private final List<RefreshToken> refreshTokens = new ArrayList<>();

	public boolean containsRefreshToken(RefreshToken refreshToken) {
		return refreshTokens.contains(refreshToken);
	}

	/**
	 * 
	 * Permite agregar una asesoria al alumno
	 * 
	 * @param advice la asesoria que deseo agregar al alumno
	 * @return true si la asesoria se agregó correctamente, false si no
	 * @throws IllegalArgumentException si la asesoria es nula
	 */
	public boolean addAdvice(Advice advice) {
		if (advice == null)
			throw new IllegalArgumentException("La asesoria no puede ser null");

		if (advices.contains(advice))
			return false;

		return advices.add(advice);
	}

	/**
	 * 
	 * Permite agregar una inscripcion al alumno
	 * 
	 * @param inscription la inscripcion que deseo agregar al alumno
	 * @return true si la inscripcion se agregó correctamente, false si no
	 * @throws IllegalArgumentException si la inscripcion es nula
	 */
	public boolean addInscription(Inscription inscription) {
		if (inscription == null)
			throw new IllegalArgumentException("La inscripcion no puede ser null");

		if (inscriptions.contains(inscription))
			return false;

		return inscriptions.add(inscription);

	}

	/**
	 * 
	 * Permite agregar una comentario al alumno
	 * 
	 * @param comment el comentario que deseo agregar al alumno
	 * @return true si el comentario se agregó correctamente, false si no
	 * @throws IllegalArgumentException si el comentario es nulo
	 */
	public boolean addComment(Comment comment) {
		if (comment == null)
			throw new IllegalArgumentException("El comentario no puede ser null");

		if (comments.contains(comment))
			return false;

		return comments.add(comment);
	}
}
