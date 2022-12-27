package anver.tutouami.com.model.entity;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Advice entity
 * 
 * @author anver
 *
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Advice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_advice")
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	private Date endTime;

	private Float score;

	@Column(name = "total_score")
	private Integer totalScore;
	private String details;
	private String type;
	private String location;
	private Float price;
	private String url;
	private String status;
	
	@OneToOne
	private Student student;

	@ManyToOne
	private Subject subject;

	@OneToOne
	private Inscription inscription;

	@JsonIgnore
	@OneToMany(targetEntity = Comment.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "id_advice")
	private final List<Comment> comentarios = new ArrayList<>();

	public boolean addComment(Comment comment) {
		if (comment == null)
			throw new IllegalArgumentException("El comentario no puede ser nulo");

		if (comentarios.contains(comment))
			return false;

		return comentarios.add(comment);
	}
}
