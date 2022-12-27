package anver.tutouami.com.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import anver.tutouami.com.model.dto.LoginDTO;
import anver.tutouami.com.model.dto.LoginResponseDTO;
import anver.tutouami.com.model.dto.SignUpRequestDTO;
import anver.tutouami.com.model.dto.SignUpResponseDTO;
import anver.tutouami.com.security.service.IAuthService;
import io.swagger.annotations.Api;

/**
 * Authentication controller.
 * 
 * @author anver
 *
 */
@RestController
@RequestMapping("/auth")
@Api(value = "Authentication")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class AuthController {

	@Autowired
	private IAuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<LoginResponseDTO> authenticateUser(@Valid @RequestBody LoginDTO loginRequest) {
		return ResponseEntity.ok(authService.authenticateUser(loginRequest));
	}

	@PostMapping("/signup")
	public ResponseEntity<SignUpResponseDTO> registerUser(@Valid @RequestBody SignUpRequestDTO signUpRequest) {
		return ResponseEntity.ok(authService.registerUser(signUpRequest));
	}
}
