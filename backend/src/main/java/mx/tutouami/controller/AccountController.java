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
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.tutouami.model.SecurityExamples;
import mx.tutouami.model.dto.AccountDTO;
import mx.tutouami.service.IAccountService;
import mx.tutouami.service.impl.SecurityServiceImpl;
import mx.tutouami.service.impl.StudentServiceImpl;

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
	private SecurityServiceImpl servicioSeguridad;

	@Autowired
	private StudentServiceImpl servicioAlumno;
	
	@Autowired
	private IAccountService accountService;

	@ApiOperation(value = "Allow user login", notes = "User login")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User login succesfull"),
			@ApiResponse(code = 404, message = "User not found"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccountDTO> login(
			@ApiParam(value = "Email", required = true) @RequestHeader(name = "Email", required = true) String email,
			@ApiParam(value = "Password", required = true) @RequestHeader(name = "Password", required = true) String password) {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.validateLogin(email, password));
	}

	/**
	 * Registra un nuevo alumno
	 * 
	 * @param nuevoAlumnoDto alumno a agregar
	 * @return alumnoDto alumno creado
	 * @ApiOperation(value = "Registra un alumno", notes = "Se registro el alumno")
	 * @ApiResponses(value = { @ApiResponse(code = 200, message = "Alumno registrado
	 *                     exitosamente"),
	 * @ApiResponse(code = 404, message = "No hay alumno que agregar"),
	 * @ApiResponse(code = 500, message = "Error en el servidor") })
	 * @PostMapping(path = "/cuenta/registro", consumes =
	 *                   MediaType.APPLICATION_JSON_VALUE, produces =
	 *                   MediaType.APPLICATION_JSON_VALUE) public
	 *                   ResponseEntity<StudentDTO> create(@RequestBody StudentDTO
	 *                   nuevoAlumnoDto) {
	 * 
	 *                   log.info("Empezando HU-01"); try { StudentDTO alumnoDto =
	 *                   servicioAlumno.agregarAlumno(nuevoAlumnoDto);
	 * 
	 *                   return
	 *                   ResponseEntity.status(HttpStatus.OK).body(alumnoDto); }
	 *                   catch (Exception ex) { HttpStatus status;
	 * 
	 *                   if (ex instanceof IllegalArgumentException) status =
	 *                   HttpStatus.BAD_REQUEST; else { log.error("el error es: " +
	 *                   ex.getCause()); status = HttpStatus.INTERNAL_SERVER_ERROR;
	 *                   }
	 * 
	 *                   throw new ResponseStatusException(status, ex.getMessage());
	 *                   } }
	 */

	/**
	 * Obtiene un alumno segun el token
	 * 
	 * @param authorization token de autorizacion para el alumno
	 * @return
	 */
	@ApiOperation(value = "Obtiene un alumno", notes = "Se obtuvo el alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se obtuvo el alumno exitosamente"),
			@ApiResponse(code = 404, message = "No existe el alumno"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@GetMapping(path = "/cuenta/perfil", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveAll(
			@ApiParam(name = "Authorization", value = "Bearer token", example = SecurityExamples.HEADER_AUTHORIZATION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {

		try {

			servicioSeguridad.jwtValidation(authorization.replace("Bearer ", ""));

				Long idAlumno = servicioSeguridad.getUuidFromJwt(authorization.replace("Bearer ", ""));

				return ResponseEntity.status(HttpStatus.OK).body(servicioAlumno.findById(idAlumno));


		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	/**
	 * Refrezca el token de un usuario para que este continue logueado
	 * 
	 * @param authorization header de autorizacion establecido
	 * @param refreshToken  token que se refrescara
	 * @return
	 */
	@ApiOperation(value = "Refresca el token de un alumno logueado", notes = "Se actualiza el token del alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Token refrescado exitosamente"),
			@ApiResponse(code = 401, message = "No tienes los permisos necesarios"),
			@ApiResponse(code = 404, message = "No se pudo refrescar el token"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PostMapping(path = "/cuenta/refresca", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> refresh(
			@ApiParam(name = "Authorization", value = "Bearer token", example = SecurityExamples.HEADER_AUTHORIZATION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@ApiParam(value = "X-Refresh-Token", required = true) @RequestHeader(name = "X-Refresh-Token", required = true) UUID refreshToken) {

		// Generamos el JWT del usuario
		String jwt = servicioSeguridad.refreshJwt(authorization.replace("Bearer ", ""), refreshToken);

		if (jwt == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// Construimos el JSON que se regresará
		HashMap<String, String> json = new HashMap<>();

		json.put("type", "Bearer Token");
		json.put("token", jwt);

		return ResponseEntity.status(HttpStatus.OK).body(json);

	}

}
