package mx.tutouami.model.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import mx.tutouami.model.entity.Advice;

/**
 * DTO de asesorias
 * 
 */
@Data
@Builder
public class AdviceDTO {
	
	private long id;
	
	@NotEmpty(message = "El campo no debe ser vacio")
	private Date date;
	
	@NotEmpty(message = "El campo no debe ser vacio")
	private Date startTime;
	
	@NotEmpty(message = "El campo no debe ser vacio")
	private Date endTime;
	
	@NotNull
	private Float price;
	
	@NotNull
	private Float score;

	@NotEmpty
	private String url;
	
	@NotEmpty(message = "El campo no debe ser vacio")
	private Integer totalScore;
	
	@NotEmpty
	private String details;
	
	@NotEmpty
	private String type;
	
	@NotEmpty
	private String location;
	
	@NotEmpty
	private String status;
		
	@NotNull
	private Long subjectId;
	
	@NotNull
	private Long studentId;

	public static AdviceDTO generate(Advice advice) {
		return AdviceDTO.builder()
				.date(advice.getDate())
				.details(advice.getDetails())
				.endTime(advice.getEndTime())
				.id(advice.getId())
				.location(advice.getLocation())
				.price(advice.getPrice())
				.score(advice.getScore())
				.startTime(advice.getStartTime())
				.status(advice.getStatus())
				.studentId(advice.getStudent().getId())
				.subjectId(advice.getSubject().getIdMateria())
				.totalScore(advice.getTotalScore())
				.type(advice.getType())
				.url(advice.getUrl())
				.build();
	}
}
