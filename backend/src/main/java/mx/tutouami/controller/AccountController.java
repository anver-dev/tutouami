package mx.tutouami.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.tutouami.model.SecurityExamples;
import mx.tutouami.model.dto.AccountDTO;
import mx.tutouami.service.IAccountService;
import mx.tutouami.service.ISecurityService;
import mx.tutouami.service.IStudentService;

/**
 * Account controller.
 * 
 * @author anver
 *
 */
@RestController
@RequestMapping("/account")
@Api(value = "Account")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class AccountController {

	@Autowired
	private ISecurityService securityService;

	@Autowired
	private IStudentService studentService;

	@Autowired
	private IAccountService accountService;

	@ApiOperation(value = "Allow user login", notes = "User login")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User login succesfull"),
			@ApiResponse(code = 404, message = "User not found"),
			@ApiResponse(code = 500, message = "Server error") })
	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccountDTO> login(
			@ApiParam(value = "Email", required = true) @RequestHeader(name = "Email", required = true) String email,
			@ApiParam(value = "Password", required = true) @RequestHeader(name = "Password", required = true) String password) {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.validateLogin(email, password));
	}

	@ApiOperation(value = "Get profile from student", notes = "Get student profile")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Get student profile successful"),
			@ApiResponse(code = 404, message = "Profile not found"),
			@ApiResponse(code = 500, message = "Server error") })
	@GetMapping(path = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getProfile(
			@ApiParam(name = "Authorization", value = "Bearer token", example = SecurityExamples.HEADER_AUTHORIZATION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {
		securityService.jwtValidation(authorization);
		Long idAlumno = securityService.getUuidFromJwt(authorization);
		return ResponseEntity.status(HttpStatus.OK).body(studentService.findById(idAlumno));
	}

	@ApiOperation(value = "Refresca el token de un alumno logueado", notes = "Se actualiza el token del alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Token refrescado exitosamente"),
			@ApiResponse(code = 401, message = "No tienes los permisos necesarios"),
			@ApiResponse(code = 404, message = "No se pudo refrescar el token"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PostMapping(path = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> refresh(
			@ApiParam(name = "Authorization", value = "Bearer token", example = SecurityExamples.HEADER_AUTHORIZATION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@ApiParam(value = "X-Refresh-Token", required = true) @RequestHeader(name = "X-Refresh-Token", required = true) UUID refreshToken) {

		String jwt = securityService.refreshJwt(authorization, refreshToken);

		if (jwt == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		HashMap<String, String> json = new HashMap<>();

		json.put("type", "Bearer Token");
		json.put("token", jwt);

		return ResponseEntity.status(HttpStatus.OK).body(json);

	}

}
