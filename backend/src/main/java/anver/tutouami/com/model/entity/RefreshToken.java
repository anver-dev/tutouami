package anver.tutouami.com.model.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class RefreshToken {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	private Long issuedAt;
	
	private Long expireAt;	
	
	@ManyToOne
	@JoinColumn(name = "user")
	private Student student;

}
