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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.AlumnoDto;
import mx.uam.ayd.proyecto.dto.AsesoriaDto;
import mx.uam.ayd.proyecto.negocio.ServicioAlumno;
import mx.uam.ayd.proyecto.negocio.ServicioAsesoria;

@RestController
@RequestMapping("/v1") // Versionamiento
@Slf4j 
public class AsesoriaRestController {
	
	@Autowired
	private ServicioAlumno servicioAlumno;
	
	@Autowired
	private ServicioAsesoria servicioAsesoria;
	
	/**
	 * Método que permite agregar una asesoria a un alumno
	 * 
	 * @param nuevoAsesoria
	 * @return
	 */
	@ApiOperation(value = "Agrega una asesoria", notes = "Se agrega una asesoria a través del DTO")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Asesoria agregada exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 500, message = "Error al agregar la asesoria")})
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
     * Permite recuperar todos los alumnos
     * 
     * @return
     */
    @GetMapping(path = "/asesorias", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <List<AsesoriaDto>> retrieveAll() {
        
    	log.info("Se consulta endpoint /asesorias");
        List <AsesoriaDto> asesorias =  servicioAsesoria.recuperaAsesorias();
        
        return ResponseEntity.status(HttpStatus.OK).body(asesorias);
        
    }

}
