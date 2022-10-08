package mx.tutouami.model.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.tutouami.model.entity.Comment;

/**
 * Comment DTO
 * 
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
	
	@NotNull
	private Long id;
	
	@NotEmpty(message = "Require content")
	private String content;
	
	private Date createAt;
	
	@NotNull
	private Long studentId;
	
	@NotNull
	private Long adviceId;
	
	public static CommentDTO generate(Comment comment) {
		return CommentDTO.builder()
				.adviceId(comment.getAdvice().getId())
				.studentId(comment.getStudent().getId())
				.createAt(comment.getCreateAt())
				.id(comment.getId())
				.content(comment.getContent())
				.build();
	}
}
