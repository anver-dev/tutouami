package anver.tutouami.com.model.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO {
	private String username;
	private String email;
	private String password;
	private String confirmPassword;
	private Set<String> roles;
}