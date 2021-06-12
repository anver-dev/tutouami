package mx.uam.ayd.proyecto.servicios;

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
import org.springframework.web.bind.annotation.RequestBody;
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
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.AlumnoDto;
import mx.uam.ayd.proyecto.negocio.ServicioAlumno;
import mx.uam.ayd.proyecto.seguridad.ServicioSeguridad;

/**
 * Controlador de endpoint de cuenta.
 * 
 * @author anver
 *
 */
@Slf4j
@RestController
@RequestMapping("/v1")
@Api(value = "Cuenta")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class CuentaRestController {

	@Autowired
	private ServicioSeguridad servicioSeguridad;

	@Autowired
	private ServicioAlumno servicioAlumno;

	/**
	 * Metodo para iniciar sesion
	 * 
	 * @param correo      correo del alumno que desea iniciar sesión.
	 * @param contrasenia del alumno que desea iniciar sesión.
	 * @return
	 */
	@ApiOperation(value = "Permite el inicio de sesion del alumno", notes = "El alumno inicia sesión")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Alumno logueado exitosamente"),
			@ApiResponse(code = 404, message = "El alumno no pudo loguearse"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> login(
			@ApiParam(value = "Correo", required = true) @RequestHeader(name = "Correo", required = true) String correo,
			@ApiParam(value = "Contrasenia", required = true) @RequestHeader(name = "Contrasenia", required = true) String contrasenia) {

		log.info("Correo: " + correo + " Contrasenia: " + contrasenia);
		// Generamos el JWT del usuario.
		String jsonWebToken = servicioSeguridad.generaTokenUsuario(correo, contrasenia);
		String refreshJsonWebToken = servicioSeguridad.generaRefreshTokenUsuario(correo, contrasenia);

		// Si el JWT es nulo, regresamos un bad request
		if (jsonWebToken == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		// Construimos el JSON que se regresará
		HashMap<String, String> json = new HashMap<>();

		// Indicamos qué tipo de token es
		json.put("type", "Bearer Token");

		// Ponemos el refresh token
		json.put("refresh-token", refreshJsonWebToken);

		// Ponemos el JWT y lo regresamos
		json.put("token", jsonWebToken);

		return ResponseEntity.status(HttpStatus.OK).body(json);

	}

	/**
	 * Registra un nuevo alumno
	 * 
	 * @param nuevoAlumnoDto alumno a agregar
	 * @return alumnoDto alumno creado
	 */
	@ApiOperation(value = "Registra un alumno", notes = "Se registro el alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Alumno registrado exitosamente"),
			@ApiResponse(code = 404, message = "No hay alumno que agregar"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PostMapping(path = "/registro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AlumnoDto> create(@RequestBody AlumnoDto nuevoAlumnoDto) {

		log.info("Empezando HU-01");
		try {
			AlumnoDto alumnoDto = servicioAlumno.agregarAlumno(nuevoAlumnoDto);

			return ResponseEntity.status(HttpStatus.CREATED).body(alumnoDto);
		} catch (Exception ex) {
			HttpStatus status;

			if (ex instanceof IllegalArgumentException)
				status = HttpStatus.BAD_REQUEST;
			else {
				log.error("el error es: " + ex.getCause());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}

			throw new ResponseStatusException(status, ex.getMessage());
		}
	}

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
	@GetMapping(path = "/perfil", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveAll(
			@ApiParam(name = "Authorization", value = "Bearer token", example = ServicioSeguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {

		try {

			// Revisamos si es un JWT válido para esta petición, quitamos la parte de bearer
			// del header para tener solo el JWT
			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {

				// Obtenemos el UUID que viene en el JWT, quitamos la parte de bearer del header
				// para tener solo el JWT
				Long idAlumno = servicioSeguridad.obtenUuidDeJwt(authorization.replace("Bearer ", ""));

				// Comparamos el UUID solicitado al controlador con el que viene en el token
				// solo aceptamos peticiones para el usuario del token, si esta UUID
				// es la misma y el usuario existe, regresamos el usuario
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(AlumnoDto.creaAlumnoDto(servicioAlumno.obtenerAlumnoPorId(idAlumno).get()));

			}

			// Cualquier otro caso, regresamos un no autorizado
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

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
			@ApiResponse(code = 404, message = "No se pudo refrescar el token"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PostMapping(path = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> refresh(
			@ApiParam(name = "Authorization", value = "Bearer token", example = ServicioSeguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@ApiParam(value = "X-Refresh-Token", required = true) @RequestHeader(name = "X-Refresh-Token", required = true) UUID refreshToken) {

		// Generamos el JWT del usuario
		String jwt = servicioSeguridad.refrescaJwt(authorization.replace("Bearer ", ""), refreshToken);

		// Revisamos que tenga el

		// Si el JWT es nulo, regresamos un bad request
		if (jwt == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		// Construimos el JSON que se regresará
		HashMap<String, String> json = new HashMap<>();

		// Indicamos qué tipo de token es
		json.put("type", "Bearer Token");

		// Ponemos el token y lo regresamos
		json.put("token", jwt);

		return ResponseEntity.status(HttpStatus.OK).body(json);

	}

}
