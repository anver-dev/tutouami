package mx.uam.ayd.proyecto.servicios;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.AsesoriaDto;
import mx.uam.ayd.proyecto.negocio.ServicioAsesoria;


@RestController
@RequestMapping("/v1") // Versionamiento
@Slf4j 
public class AsesoriaRestController {

	@Autowired 
	private ServicioAsesoria servicioAsesoria;

	/**
	 * Método que permite agregar una asesoria de un alumno
	 * 
	 * @param nuevoAsesoria
	 * @return
	 */
	@ApiOperation(value = "Agrega una asesoria", notes = "Se agrega una asesoria a través del DTO")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Asesoria agregada exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 500, message = "Error en el servidor")})
	@PostMapping(path = "/alumnos/{id}/asesoria", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AsesoriaDto> create(@RequestBody @Valid AsesoriaDto nuevaAsesoria,@PathVariable("id")Long id) {
		try {
			AsesoriaDto asesoriaDto = servicioAsesoria.agregaAsesoria(nuevaAsesoria,id);
			return ResponseEntity.status(HttpStatus.CREATED).body(asesoriaDto);
		} catch (Exception e) {
			HttpStatus status;
			
			if(e instanceof IllegalArgumentException) {
				status = HttpStatus.BAD_REQUEST;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			
			throw new ResponseStatusException(status, e.getMessage());
		}
	}
	
	/**
	 * Metodo para actualizar una asesoria
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 */
	@ApiOperation(value = "Actualiza una asesoria", notes = "Se actualiza la asesoria a través del DTO y de su id  y el id del alumno")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Asesoria Actualizada exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 500, message = "Error en el servidor")})
	@PutMapping(path = "/alumnos/{id}/asesoria/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AsesoriaDto> update(
			@ApiParam(name="idAsesoria",value = "identificador de la asesoria", required = true, example = "0") @RequestParam(name="idAsesoria",required = true) Long idAsesoria,
	        @ApiParam(name="idAlumno",value = "identificador del alumno", required = true, example = "0") @RequestParam(name="idAlumno",required = true) Long idAlumno,			
			@RequestBody  @Valid AsesoriaDto asesoriaDto ) {
		
		//Traza
		log.info("Actualizando la asesoria con id "+ idAsesoria +"Del alumno: "+idAlumno );
		
		try{
			//Se manda a llamar al servicio
			AsesoriaDto asesoria = servicioAsesoria.actualizar(idAsesoria,idAlumno,asesoriaDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(asesoria);
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
	


	/**
     * Recupera las asesorias con una mataria en particular
     * 
     * @return
     */
	@ApiOperation(value = "Se buscan asesorias que esten relacionas a una materia", notes = "Se buscan las asesorias que concidan con la materia buscada")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Asesorias de la materia"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 500, message = "Error en el servidor")})
	@GetMapping(path = "/alumnos/{id}/asesoria/materia/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AsesoriaDto>> retrieve(
    		@ApiParam(name="idMateria",value = "identificador de la materia", required = true, example = "0") @RequestParam(name="idMateria",required = true) Long idMateria) {
        
    		log.info("Se van a obtener las asesorias de la materia con id "+ idMateria);
    	
    		List <AsesoriaDto> asesoriasDto =  servicioAsesoria.recuperaAsesoriasMateria(idMateria);
	        return ResponseEntity.status(HttpStatus.OK).body(asesoriasDto);  

    }
	
	/**
	 * Permite recuperar todas las asesorias de un unico alumno
	 * 
	 * @return
	 */
	@ApiOperation(value = "Se obtinen las asesorias de un alumno", notes = "Se recibe el id del alumno")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Se encontraron las asesorias"),
			@ApiResponse(code = 404, message = "No se encontrada"),
			@ApiResponse(code = 500, message = "Error en el servidor")})
	@GetMapping(path = "/alumnos/{id}/asesoria", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<AsesoriaDto>> retrieveAll(
			@ApiParam(name="id",value = "identificador del alumno", required = true, example = "0") @RequestParam(name="id",required = true) Long id) {
		
		log.info("Se van a obtener las asesorias del alumno con id "+ id);
		
		List <AsesoriaDto> asesoriasDto =  servicioAsesoria.recuperaAsesorias(id);	
		return ResponseEntity.status(HttpStatus.OK).body(asesoriasDto);
		
	}
	
	

}
