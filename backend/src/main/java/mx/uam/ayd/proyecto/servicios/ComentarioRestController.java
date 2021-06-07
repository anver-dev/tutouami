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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.AsesoriaDto;
import mx.uam.ayd.proyecto.dto.ComentarioDto;
import mx.uam.ayd.proyecto.negocio.ServicioAsesoria;
import mx.uam.ayd.proyecto.negocio.ServicioComentario;

@RestController
@RequestMapping("/v1") // Versionamiento
@Slf4j 
public class ComentarioRestController {
	
	@Autowired
	private ServicioComentario servicioComentario;
	
	/**
	 * Método que permite agregar un comentario a una asesoria en especifico
	 * 
	 * @param nuevoComentario
	 * @param idAlumno
	 * @param idAsesoria
	 * @return
	 */
	@ApiOperation(value = "Agrega un comentario", notes = "Se agrega un comentario a través del DTO")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Comentario agregado exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro al alumno o a la asesoria para agregar el comentario"),
			@ApiResponse(code = 500, message = "Error al agregar el comentario")})
	@PostMapping(path = "/alumnos/{id}/asesoria/{id}/comentario", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ComentarioDto> create(@RequestBody @Valid ComentarioDto nuevoComentario,@PathVariable("id")Long idAlumno, @PathVariable("id")Long idAsesoria) {
		try {
			ComentarioDto comentarioDto = servicioComentario.agregarComentario(idAlumno, idAsesoria, nuevoComentario);
			return ResponseEntity.status(HttpStatus.CREATED).body(comentarioDto);
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
    @GetMapping(path = "/comentarios", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <List<ComentarioDto>> retrieveAll() {
        
    	log.info("Se consulta endpoint /comentarios");
        List <ComentarioDto> comentarios =  servicioComentario.recuperaComentarios();
        return ResponseEntity.status(HttpStatus.OK).body(comentarios);
        
    }

}
