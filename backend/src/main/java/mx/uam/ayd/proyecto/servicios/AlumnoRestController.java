package mx.uam.ayd.proyecto.servicios;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import mx.uam.ayd.proyecto.config.Seguridad;
import mx.uam.ayd.proyecto.dto.AlumnoDto;
import mx.uam.ayd.proyecto.dto.AsesoriaDto;
import mx.uam.ayd.proyecto.dto.InscripcionDto;
import mx.uam.ayd.proyecto.negocio.ServicioAlumno;
import mx.uam.ayd.proyecto.negocio.ServicioAsesoria;
import mx.uam.ayd.proyecto.negocio.ServicioInscripcion;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.seguridad.ServicioSeguridad;

/**
 * Restcontroller para entidad alumno
 * 
 * @author anver
 *
 */
@Slf4j
@RestController
@RequestMapping("/v1")
@Api(value = "Alumno")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE })
public class AlumnoRestController {

	@Autowired
	private ServicioAlumno servicioAlumno;

	@Autowired
	private ServicioSeguridad servicioSeguridad;

	@Autowired
	private ServicioInscripcion servicioInscripcion;

	@Autowired
	private ServicioAsesoria servicioAsesoria;

	////// ALUMNO
	/**
	 * Permite recuperar todos los alumnos
	 * 
	 * @return
	 */
	@ApiOperation(value = "Obtiene alumnos", notes = "Obtiene todos los alumnos registrados")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Alumnos mostrados con exito"),
			@ApiResponse(code = 404, message = "No se encontro el alumno")})
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AlumnoDto>> retrieveAll(
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {

		if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {
			log.info("Se obtienen los alumnos");
			List<AlumnoDto> alumnos = servicioAlumno.recuperaAlumnos();

			return ResponseEntity.status(HttpStatus.OK).body(alumnos);
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	/**
	 * Método que permite eliminar a un usuario mediante el id
	 * 
	 * @param idAlumno id del alumno a eliminar
	 * @return
	 */
	@ApiOperation(value = "Elimina alumno", notes = "Elimina a un alumno mediante su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Alumno eliminado exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro el alumno"),
			@ApiResponse(code = 500, message = "Error al eliminar al alumno") })
	@DeleteMapping(path = "/alumnos/{idAlumno}")
	public ResponseEntity<?> delete(
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@PathVariable("idAlumno") @Valid Long idAlumno) {

		log.info("Buscando al alumno con id para eliminarlo: " + idAlumno);

		try {
			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {
				AlumnoDto alumno = servicioAlumno.retrieve(idAlumno);
				if (alumno != null) {
					servicioAlumno.delete(idAlumno);
					return ResponseEntity.status(HttpStatus.OK).body("Alumno eliminado correctamente");

				} else
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro alumno");
		}

	}

	/**
	 * Metodo para actualizar un alumno
	 * 
	 * @param id      del alumno
	 * @param usuario datos a actualizar
	 * @return
	 */
	@ApiOperation(value = "Actualiza el perfil de un alumno", notes = "Se actualiza el alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Alumno actualizado exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para actualizar"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PatchMapping(path = "/alumnos/{idAlumno}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AlumnoDto> update(
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@PathVariable("idAlumno") Long idAlumno, @RequestBody @Valid AlumnoDto alumnoDto) {

		log.info("Actualizando el alumno con id: " + idAlumno);

		try {
			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {
				AlumnoDto alumno = servicioAlumno.actualizarAlumno(idAlumno, alumnoDto);

				return ResponseEntity.status(HttpStatus.OK).body(alumno);
			}

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (Exception ex) {

			HttpStatus status;

			if (ex instanceof IllegalArgumentException)
				status = HttpStatus.NOT_FOUND;
			else
				status = HttpStatus.INTERNAL_SERVER_ERROR;

			throw new ResponseStatusException(status, ex.getMessage());
		}
	}

	/**
	 * Obtiene un alumno segun su id
	 * 
	 * @param idAlumnoAbuscar id del alumno a buscar
	 * @param authorization   header de autorizacion
	 * @return
	 */
	@ApiOperation(value = "Obtiene un alumno", notes = "Se obtiene el alumno segun su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se obtuvo el alumno exitosamente"),
			@ApiResponse(code = 401, message = "No tienes los permisos necesarios"),
			@ApiResponse(code = 404, message = "No existe el alumno"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@GetMapping(path = "/alumnos/{idAlumno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveAll(@PathVariable("idAlumno") Long idAlumnoAbuscar,
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {

		try {
			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {

				Optional<Alumno> opAlumnoEncontrado = servicioAlumno.obtenerAlumnoPorId(idAlumnoAbuscar);

				if (!opAlumnoEncontrado.isPresent())
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

				return ResponseEntity.status(HttpStatus.CREATED).body(AlumnoDto.creaAlumnoDto(
						servicioAlumno.obtenerAlumnoPorId(opAlumnoEncontrado.get().getIdAlumno()).get()));

			}

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	////// ALUMNO - ASESORIA

	/**
	 * Método que permite agregar una asesoria a un alumno
	 * 
	 * @param nuevoAsesoria
	 * @return
	 */
	@ApiOperation(value = "Agrega una asesoria", notes = "Se agrega una asesoria a través del DTO")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Asesoria agregada exitosamente"),
			@ApiResponse(code = 401, message = "No tienes los permisos necesarios"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 500, message = "Error al agregar la asesoria") })
	@PostMapping(path = "/alumnos/{id}/asesoria", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AsesoriaDto> create(
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@RequestBody @Valid AsesoriaDto nuevaAsesoria, @PathVariable("id") Long id) {
		try {

			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {
				AsesoriaDto asesoriaDto = servicioAsesoria.agregaAsesoria(nuevaAsesoria, id);
				return ResponseEntity.status(HttpStatus.OK).body(asesoriaDto);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (Exception e) {
			HttpStatus status;

			if (e instanceof IllegalArgumentException) {
				status = HttpStatus.NOT_FOUND;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}

			throw new ResponseStatusException(status, e.getMessage());
		}
	}

	/**
	 * Comienza HU-05: Como alumno quiero seleccionar una asesoría para eliminarla
	 * de mi perfil. Metodo para eliminar una asesoria
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Eliminar asesoria", notes = "Se elimina una asesoria apartir de su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se elimino la asesoria"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 409, message = "No se pudo eliminar"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@DeleteMapping(path = "/alumnos/{idAlumno}/asesoria/{idAsesoria}")
	public ResponseEntity<?> delete(
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@PathVariable("idAlumno") Long idAlumno, @PathVariable("idAsesoria") Long idAsesoria) {

		try {

			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {
				AsesoriaDto asesoria = servicioAsesoria.buscarID(idAsesoria);
				if (asesoria != null) {
					log.info("Se va eliminar la asesoria con id " + idAsesoria + ", del alumno con id: " + idAlumno);
					if(servicioAsesoria.eliminarAsesoria(asesoria))
						return ResponseEntity.status(HttpStatus.OK).body("Se elimino la asesoria");
					else
						return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede eliminar la asesoria ya que cuenta con inscripciones");
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La asesoria no existe");
				}
			}

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (Exception ex) {
			log.info("Error: " + ex);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la asesoria");
		}
	}
	
	/**
	 * Comienza HU-04: Como alumno quiero seleccionar una asesoría para editar la
	 * información. Metodo para actualizar una asesoria
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 */
	@ApiOperation(value = "Actualiza una asesoria", notes = "Se actualiza la asesoria a través del DTO y de su id  y el id del alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Asesoria Actualizada exitosamente"),
			@ApiResponse(code = 401, message = "No tienes los permisos necesarios"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PatchMapping(path = "/alumnos/{idAlumno}/asesoria/{idAsesoria}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AsesoriaDto> update(
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@PathVariable("idAlumno") Long idAlumno,
			@PathVariable("idAsesoria") Long idAsesoria, @RequestBody @Valid AsesoriaDto asesoriaDto) {

		// Traza
		log.info("Actualizando la asesoria con id " + idAsesoria + " Del alumno: " + idAlumno);

		try {
			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {
				AsesoriaDto asesoria = servicioAsesoria.actualizar(idAsesoria, idAlumno, asesoriaDto);
				return ResponseEntity.status(HttpStatus.CREATED).body(asesoria);
			}
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (Exception ex) {

			HttpStatus status;

			if (ex instanceof IllegalArgumentException) {
				status = HttpStatus.BAD_REQUEST;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}

			throw new ResponseStatusException(status, ex.getMessage());
		}
	}
	
	/**
	 * Actualiza el estado de una inscripcion
	 * 
	 * @param authorization header verifica que el alumno este logueado
	 * @param idAlumno      identificador del alumno
	 * @param idAsesoria    identificador de la asesoria
	 * @param estado        nuevo valor del estado
	 * @return
	 */
	@ApiOperation(value = "Actualiza el estado de una inscripcion", notes = "Se actualiza el estado de la inscripcion a través del id de la asesoria y el id del alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Asesoria actualizada exitosamente"),
			@ApiResponse(code = 401, message = "No tienes los permisos necesarios"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PatchMapping(path = "/alumnos/{idAlumno}/inscripcion/{idInscripcion}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@PathVariable("idAlumno") Long idAlumno, @PathVariable("idInscripcion") Long idInscripcion,
			@RequestBody @Valid String estado) {

		// Traza
		log.info("Actualizando estado de la asesoria con id " + idInscripcion + " Del alumno: " + idAlumno);

		try {
			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {
				Optional<InscripcionDto> opAsesoria = servicioInscripcion.actualizarEstado(idInscripcion, idAlumno,
						estado);

				if (!opAsesoria.isPresent())
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

				return ResponseEntity.status(HttpStatus.OK).body(opAsesoria);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		} catch (Exception ex) {

			HttpStatus status;

			if (ex instanceof IllegalArgumentException)
				status = HttpStatus.BAD_REQUEST;
			else
				status = HttpStatus.INTERNAL_SERVER_ERROR;

			throw new ResponseStatusException(status, ex.getMessage());
		}
	}
	
	/**
	 * Comienza HU-04: Como alumno quiero seleccionar una asesoría para editar la
	 * información.
	 * 
	 * 
	 * Permite recuperar todas las asesorias de un unico alumno
	 * 
	 * @return
	 */
	@ApiOperation(value = "Se obtinen las asesorias de un alumno", notes = "Se recibe el id del alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se encontraron las asesorias"),
			@ApiResponse(code = 401, message = "No tienes los permisos necesarios")})
	@GetMapping(path = "/alumnos/{idAlumno}/asesoria", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AsesoriaDto>> retrieve(
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@PathVariable("idAlumno") Long idAlumno) {
		
		if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {
			List<AsesoriaDto> asesoriasDto = servicioAsesoria.recuperaAsesorias(idAlumno);
			return ResponseEntity.status(HttpStatus.OK).body(asesoriasDto);
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	
	//////ALUMNO - INSCRIPCION
	
	/**
	 * Consulta todas las incripciones de un alumno
	 * 
	 * @param idAlumno
	 * @return
	 */
	@ApiOperation(value = "Consulta todas las inscripciones de un alumno", notes = "Se actualiza el estado de la inscripcion a través del id de la asesoria y el id del alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Inscripciones obtenidas exitosamente"),
			@ApiResponse(code = 401, message = "No tienes los permisos necesarios"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@GetMapping(path = "/alumnos/{idAlumno}/inscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<InscripcionDto>> retrieveAll(
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@PathVariable("idAlumno") Long idAlumno) {

		try {
			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {
				log.info("Se consultan inscripciones");
				List<InscripcionDto> inscripciones = servicioInscripcion.recuperaInscripcionesPorAlumno(idAlumno);

				return ResponseEntity.status(HttpStatus.OK).body(inscripciones);
			}

			// Cualquier otro caso, regresamos un no autorizado
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	/**
	 * Registra una nueva inscripcion a un alumno
	 * 
	 * @param nuevaInscripcionDto inscripcion a agregar
	 * @return inscripcionDto inscripcion creada
	 */
	@ApiOperation(value = "Registra una inscripcion", notes = "Se registro la inscripcion")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Inscripcion registrada exitosamente"),
			@ApiResponse(code = 401, message = "No tienes los permisos necesarios"),
			@ApiResponse(code = 404, message = "No se encontro el alumno"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PostMapping(path = "/alumnos/{idAlumno}/inscripciones", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InscripcionDto> create(
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@PathVariable("idAlumno") Long idAlumno, @RequestBody AsesoriaDto asesoriaDto) {

		log.info("Empezando HU-01");
		try {
			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {
				InscripcionDto alumnoDto = servicioInscripcion.agregaInscripcion(asesoriaDto.getIdAsesoria(), idAlumno);

				if (alumnoDto == null)
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

				return ResponseEntity.status(HttpStatus.OK).body(alumnoDto);
			}

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

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
	
	
	
	
	

}
