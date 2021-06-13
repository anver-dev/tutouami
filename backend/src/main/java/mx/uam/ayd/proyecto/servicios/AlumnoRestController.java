package mx.uam.ayd.proyecto.servicios;


import java.util.Optional;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.web.bind.annotation.DeleteMapping;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.config.Seguridad;
import mx.uam.ayd.proyecto.dto.AlumnoDto;

import mx.uam.ayd.proyecto.dto.AsesoriaDto;
import mx.uam.ayd.proyecto.dto.MateriaDto;
import mx.uam.ayd.proyecto.negocio.ServicioAlumno;

import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.seguridad.ServicioSeguridad;

import mx.uam.ayd.proyecto.negocio.ServicioMateria;



/**
 * Restcontroller para entidad alumno
 * 
 * @author anver
 *
 */
@Slf4j
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH })
public class AlumnoRestController {

	@Autowired
	private ServicioAlumno servicioAlumno;

	@Autowired
	private ServicioSeguridad servicioSeguridad;

 
	
	/**
     * Permite recuperar todos los alumnos
     * 
     * @return
     */
    @GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <List<AlumnoDto>> retrieveAll() {
        
    	log.info("Se consulta endpoint /alumnos");
        List <AlumnoDto> alumnos =  servicioAlumno.recuperaAlumnos();
        
        return ResponseEntity.status(HttpStatus.OK).body(alumnos);
        
    }
    
    /**
	 * Método que permite eliminar a un usuario
	 * Mediante el id
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Elimina alumno", notes = "Elimina a un alumno mediante su ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Alumno eliminado exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro el alumno"),
			@ApiResponse(code = 500, message = "Error al eliminar al alumno")})
	@DeleteMapping(path = "/alumnos/{id}")
	public ResponseEntity <?> delete(@PathVariable("id") @Valid Long id) {
		
		log.info("Buscando al alumno con id para eliminarlo: " + id);
		
		try {
			AlumnoDto alumno = servicioAlumno.retrieve(id);
			if (alumno != null) {
				servicioAlumno.delete(id);
				return ResponseEntity.status(HttpStatus.OK).body("Alumno eliminado correctamente");

			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro alumno");
		}
		
	}


	/**
	 * Metodo para actualizar un alumno
	 * 
	 * @param id del alumno
	 * @param usuario datos a actualizar
	 * @return
	 */
	@ApiOperation(value = "Actualiza el perfil de un alumno", notes = "Se actualiza el alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Alumno actualizado exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PatchMapping(path = "/alumnos/{idAlumno}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AlumnoDto> update(@PathVariable("idAlumno") Long idAlumno,
			@RequestBody @Valid AlumnoDto alumnoDto) {

		// Comienza HU-10
		log.info("Actualizando el alumno con id: " + idAlumno);

		try {
			
			// Se manda a llamar al servicio
			AlumnoDto alumno = servicioAlumno.actualizarAlumno(idAlumno, alumnoDto);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(alumno);
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
	 * Agrega un nuevo alumno
	 * 
	 * @param nuevoAlumnoDto alumno a agregar
	 * @return alumnoDto alumno creado
	 */
	@ApiOperation(value = "Agrega un alumno", notes = "Se agrega el alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Alumno agregado exitosamente"),
			@ApiResponse(code = 404, message = "No hay alumno que agregar"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
	 * Obtiene un alumno segun su id
	 * @param correo correo del alumno
	 * @param authorization token de autorizacion para el alumno
	 * @return
	 */
	@ApiOperation(value = "Obtiene un alumno", notes = "Se obtuvo el alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se obtuvo el alumno exitosamente"),
			@ApiResponse(code = 404, message = "No existe el alumno"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@GetMapping(path = "/alumnos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveAll(@PathVariable("correo") Long idAlumnoAbuscar,
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {

		try {

			// Revisamos si es un JWT válido para esta petición, quitamos la parte de bearer
			// del header para tener solo el JWT
			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {

				// Obtenemos el UUID que viene en el JWT, quitamos la parte de bearer del header
				// para tener solo el JWT
				//Long idAlumno = servicioSeguridad.obtenUuidDeJwt(authorization.replace("Bearer ", ""));
				Optional<Alumno> opAlumnoEncontrado = servicioAlumno.obtenerAlumnoPorId(idAlumnoAbuscar);
				
				// Comparamos el UUID solicitado al controlador con el que viene en el token
				// solo aceptamos peticiones para el usuario del token, si esta UUID
				// es la misma y el usuario existe, regresamos el usuario
					return ResponseEntity.status(HttpStatus.CREATED)
							.body(AlumnoDto.creaAlumnoDto(servicioAlumno.obtenerAlumnoPorId(opAlumnoEncontrado.get().getIdAlumno()).get()));

			}

			// Cualquier otro caso, regresamos un no autorizado
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}


}
