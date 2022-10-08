package mx.tutouami.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Comment entity
 * 
 * @author anver
 *
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String content;
	
	@Column(name = "create_at", updatable = false)
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@ManyToOne
    private Student student;
	
	@ManyToOne
    private Advice advice;
	
	
	
}
