package mx.tutouami.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.tutouami.entity.Advice;
import mx.tutouami.entity.Comment;
import mx.tutouami.entity.Degree;
import mx.tutouami.entity.Student;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StudentDTO {

	private Long id;
	
	@NotEmpty
	private Integer enrolment;

	@NotEmpty
	private String name;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String secondLastName;

	@Positive
	private Integer age;

	@NotEmpty
	private String phone;

	@NotEmpty
	private String cv;

	@Positive
	@Size(min = 0, max = 13, message = "El numero del trimestre debe estar entre 0 y 13")
	private Integer trimester;

	@Positive
	private Float score;

	@Positive
	private Integer totalScore;

	@NotEmpty
	private String description;

	private String status;

	@NotNull
	private Degree degree;

	private Integer totalInscriptions;

	@JsonIgnore
	@Builder.Default
	private List<Advice> advices = new ArrayList<>();

	@JsonIgnore
	@Builder.Default
	private List<Comment> comments = new ArrayList<>();

	public static StudentDTO generate(Student student) {
		return StudentDTO.builder().advices(student.getAdvices()).age(student.getAge()).comments(student.getComments())
				.cv(student.getCv()).degree(student.getDegree()).description(student.getDescription())
				.id(student.getId()).totalInscriptions(student.getInscriptions().size()).lastName(student.getLastName())
				.name(student.getName()).phone(student.getPhone()).score(student.getScore())
				.secondLastName(student.getSecondLastName()).status(student.getStatus())
				.totalScore(student.getTotalScore() != null ? student.getTotalScore() : 0)
				.trimester(student.getTrimester()).build();
	}
}
