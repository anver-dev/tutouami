package anver.tutouami.com.security.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import anver.tutouami.com.exceptions.EmailAlreadyExistsException;
import anver.tutouami.com.model.dto.LoginDTO;
import anver.tutouami.com.model.dto.LoginResponseDTO;
import anver.tutouami.com.model.dto.SignUpRequestDTO;
import anver.tutouami.com.model.dto.SignUpResponseDTO;
import anver.tutouami.com.model.dto.StudentDTO;
import anver.tutouami.com.model.dto.TokenDTO;
import anver.tutouami.com.model.entity.Role;
import anver.tutouami.com.model.entity.User;
import anver.tutouami.com.model.enums.RoleTypes;
import anver.tutouami.com.model.enums.SecurityTypes;
import anver.tutouami.com.repository.RoleRepository;
import anver.tutouami.com.repository.UserRepository;
import anver.tutouami.com.security.service.IAuthService;
import anver.tutouami.com.security.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Security service
 * 
 * @author anver
 *
 */
@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {

	private final String ROLE_ADMIN = "admin";

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Override
	public LoginResponseDTO authenticateUser(LoginDTO loginDTO) {
		log.info(String.format("Authenticate %s user", loginDTO.getEmail()));

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return LoginResponseDTO.builder().email(userDetails.getEmail())
				.token(TokenDTO.builder().value(jwt).type(SecurityTypes.TOKEN.getValue()).build()).roles(roles).build();
	}

	@Override
	public SignUpResponseDTO registerUser(SignUpRequestDTO newUser) {
		log.info(String.format("Register email: %s", newUser.getEmail()));
		validations(newUser);

		User user = User.builder().email(newUser.getEmail())
				.password(validatePassword(newUser.getPassword(), newUser.getConfirmPassword()))
				.roles(getValidRoles(newUser.getRoles())).username(newUser.getUsername()).build();

		user = userRepository.save(user);
		
		return SignUpResponseDTO.builder()
				.email(user.getEmail())
				.roles(user.getRoles())
				.studentDetails(StudentDTO.builder()
						.build())
				.build();
	}

	private Set<Role> getValidRoles(Set<String> strRoles) {
		Set<Role> roles = new HashSet<>();

		if (strRoles != null) {
			strRoles.forEach(role -> {
				switch (role) {
				case ROLE_ADMIN:
					Role adminRole = roleRepository.findByName(RoleTypes.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(RoleTypes.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});

			return roles;
		}

		Role userRole = roleRepository.findByName(RoleTypes.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);

		return roles;
	}

	private String validatePassword(String password, String confirmPassword) {
		if (!password.equals(confirmPassword))
			throw new IllegalArgumentException("Passwords do not match");

		return password;
	}

	private void validations(SignUpRequestDTO signupRequestDTO) {
		userRepository.findByEmail(signupRequestDTO.getEmail()).ifPresent((u) -> {
			throw new EmailAlreadyExistsException("El email ya esta registrado. Intente con uno nuevo", u.getEmail(),
					u.getId());
		});
	}
}
