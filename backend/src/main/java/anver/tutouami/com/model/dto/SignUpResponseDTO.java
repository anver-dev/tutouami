package anver.tutouami.com.model.dto;

import java.util.Set;

import anver.tutouami.com.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDTO {
	private String email;
	private Set<Role> roles;
	private StudentDTO studentDetails;
}
