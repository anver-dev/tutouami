package mx.uam.ayd.proyecto.servicios;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.AlumnoDto;
import mx.uam.ayd.proyecto.negocio.ServicioAlumno;

@RestController
@RequestMapping("/v1") // Versionamiento
@Slf4j 
public class AlumnoRestController {
	
	@Autowired
	private ServicioAlumno servicioAlumno;
	/**
	 * Metodo para actualizar una asesoria
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 */
	@ApiOperation(value = "Actualiza el perfil de un alumno", notes = "Se actualiza el alumno")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Alumno actualizado exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 500, message = "Error en el servidor")})
	@PutMapping(path = "/alumnos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AlumnoDto> update(
			
	        @ApiParam(name="idAlumno",value = "identificador del alumno", required = true, example = "0") @RequestParam(name="idAlumno",required = true) Long idAlumno,			
			@RequestBody  @Valid AlumnoDto alumnoDto ) {
		
		//Comienza HU-10
		log.info("Actualizando el alumno con id: "+idAlumno );
		
		try{
			//Se manda a llamar al servicio
			AlumnoDto alumno = servicioAlumno.actualizarAlumno(idAlumno,alumnoDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(alumno);
		} catch(Exception ex) {
			
			HttpStatus status;
			
			if(ex instanceof IllegalArgumentException) {
				status = HttpStatus.BAD_REQUEST;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			
			throw new ResponseStatusException(status, ex.getMessage());
		}		
	}
	
}
