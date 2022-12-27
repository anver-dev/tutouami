package anver.tutouami.com.security.service;

import anver.tutouami.com.model.dto.LoginDTO;
import anver.tutouami.com.model.dto.LoginResponseDTO;
import anver.tutouami.com.model.dto.SignUpRequestDTO;
import anver.tutouami.com.model.dto.SignUpResponseDTO;

public interface IAuthService {
	public LoginResponseDTO authenticateUser(LoginDTO loginDTO);
	public SignUpResponseDTO registerUser(SignUpRequestDTO signUpRequestDTO);
}
