package mx.uam.ayd.proyecto.servicios;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.ComentarioDto;
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
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Comentario agregado exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro al alumno o a la asesoria para agregar el comentario"),
			@ApiResponse(code = 500, message = "Error al agregar el comentario") })
	@PostMapping(path = "/alumnos/{idAlumno}/asesoria/{idAsesoria}/comentario", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody @Valid ComentarioDto nuevoComentario,
			@PathVariable("idAlumno") Long idAlumno, @PathVariable("idAsesoria") Long idAsesoria) {
		try {
			ComentarioDto comentarioDto = servicioComentario.agregarComentario(idAlumno, idAsesoria, nuevoComentario);
			return ResponseEntity.status(HttpStatus.CREATED).body(comentarioDto);
		} catch (Exception e) {
			HttpStatus status;
			if (e instanceof IllegalArgumentException) {
				status = HttpStatus.BAD_REQUEST;
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No puedes comentarte a ti mismo");
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
	public ResponseEntity<List<ComentarioDto>> retrieveAll() {

		log.info("Se consulta endpoint /comentarios");
		List<ComentarioDto> comentarios = servicioComentario.recuperaComentarios();
		return ResponseEntity.status(HttpStatus.OK).body(comentarios);

	}

	/**
	 * Método que permite eliminar a un usuario Mediante el id
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Elimina un comentario de una asesoria", notes = "Elimina un comentario mediante su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Comentario eliminado exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro el comentario"),
			@ApiResponse(code = 500, message = "Error al eliminar al alumno") })
	@DeleteMapping(path = "/alumnos/{idAlumno}/asesoria/{idAsesoria}/comentario/{idComentario}")
	public ResponseEntity<?> delete(@PathVariable("idAlumno") @Valid Long idAlumno,
			@PathVariable("idAsesoria") @Valid Long idAsesoria,
			@PathVariable("idComentario") @Valid Long idComentario) {

		log.info("Buscando el alumno con id: " + idAlumno);
		log.info("Buscando la asesoria con id: " + idAsesoria);
		log.info("Buscando el comentario con id para eliminarlo: " + idComentario);

		try {
			ComentarioDto comentario = servicioComentario.retrieve(idComentario);
			if (comentario != null) {
				servicioComentario.delete(idComentario, idAlumno, idAsesoria);
				return ResponseEntity.status(HttpStatus.OK).body("Comentario eliminado correctamente");

			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el comentario");
		}

	}

	/**
	 * 
	 * Comienza HU-15 Como alumno quiero poder seleccionar un comentario para editar
	 * el contenido
	 * 
	 * Metodo para actualizar un comentario
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 */
	@ApiOperation(value = "Actualiza un comentario", notes = "Se actualiza la comentario de una asesoria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Comentario de la asesoria actualizado exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro "),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PutMapping(path = "/alumnos/{idAlumno}/asesoria/{idAsesoria}/comentario/{idComentario}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ComentarioDto> update(@PathVariable("idAlumno") @Valid Long idAlumno,
			@PathVariable("idAsesoria") @Valid Long idAsesoria, @PathVariable("idComentario") @Valid Long idComentario,
			@RequestBody @Valid ComentarioDto comentarioDto) {

		// Traza
		log.info("Actualizando el comentario con id " + idComentario);
		log.info("Del alumno: " + idAlumno);
		log.info("De la asesoria: " + idAsesoria);

		try {
			// Se manda a llamar al servicio
			ComentarioDto comentario = servicioComentario.actualizar(idAlumno, idComentario, idAsesoria, comentarioDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(comentario);
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

}
