package mx.uam.ayd.proyecto.servicios;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import mx.uam.ayd.proyecto.negocio.ServicioAlumno;
import mx.uam.ayd.proyecto.negocio.ServicioAsesoria;
import mx.uam.ayd.proyecto.seguridad.ServicioSeguridad;

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
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Asesoria agregada exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 500, message = "Error al agregar la asesoria") })
	@PostMapping(path = "/alumnos/{id}/asesoria", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AsesoriaDto> create(@RequestBody @Valid AsesoriaDto nuevaAsesoria,
			@PathVariable("id") Long id) {
		try {
			AsesoriaDto asesoriaDto = servicioAsesoria.agregaAsesoria(nuevaAsesoria, id);
			return ResponseEntity.status(HttpStatus.CREATED).body(asesoriaDto);
		} catch (Exception e) {
			HttpStatus status;

			if (e instanceof IllegalArgumentException) {
				status = HttpStatus.BAD_REQUEST;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}

			throw new ResponseStatusException(status, e.getMessage());
		}
	}

	/**


     * Permite recuperar todas las materias
     * 
     * @return
     *
    @GetMapping(path = "/asesorias", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <List<AsesoriaDto>> retrieveAll() {
        
    	log.info("Se consulta endpoint /materias");
        List <AsesoriaDto> asesorias =  servicioAsesoria.recuperaAsesorias();
        
        return ResponseEntity.status(HttpStatus.OK).body(asesorias);
        
    }
    */


	/**
	 * Permite recuperar todos los alumnos
	 * 
	 * @return
	 */
	@GetMapping(path = "/asesorias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AsesoriaDto>> retrieveAll() {

		log.info("Se consulta endpoint /asesorias");
		List<AsesoriaDto> asesorias = servicioAsesoria.recuperaAsesorias();

		return ResponseEntity.status(HttpStatus.OK).body(asesorias);

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
			@ApiResponse(code = 404, message = "No encontrada"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@DeleteMapping(path = "/alumnos/{idAlumno}/asesoria/{idAsesoria}")
	public ResponseEntity<?> delete(@PathVariable("idAlumno") Long idAlumno,
			@PathVariable("idAsesoria") Long idAsesoria) {

		try {
			AsesoriaDto asesoria = servicioAsesoria.buscarID(idAsesoria);
			if (asesoria != null) {
				log.info("Se va eliminar la asesoria con id " + idAsesoria + ", del alumno con id: " + idAlumno);
				servicioAsesoria.eliminarAsesoria(idAlumno, idAsesoria);
				return ResponseEntity.status(HttpStatus.OK).body("Se elimino la asesoria");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body("La asesoria no existe");
			}

		} catch (Exception ex) {
			log.info("Error: " + ex);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la asesoria");
		}
	}

	/**
	 * Comienza HU-04: Como alumno quiero seleccionar una asesoría para editar la información.
	 * Metodo para actualizar una asesoria
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 */
	@ApiOperation(value = "Actualiza una asesoria", notes = "Se actualiza la asesoria a través del DTO y de su id  y el id del alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Asesoria Actualizada exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PatchMapping(path = "/alumnos/{idAlumno}/asesoria/{idAsesoria}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AsesoriaDto> update(  @PathVariable("idAlumno") Long idAlumno,
												@PathVariable("idAsesoria") Long idAsesoria,
												@RequestBody @Valid AsesoriaDto asesoriaDto) {

		// Traza
		log.info("Actualizando la asesoria con id " + idAsesoria + " Del alumno: " + idAlumno);

		try {
			// Se manda a llamar al servicio
			AsesoriaDto asesoria = servicioAsesoria.actualizar(idAsesoria, idAlumno, asesoriaDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(asesoria);
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
	 * Comienza HU-04: Como alumno quiero seleccionar una asesoría para editar la información.
	 * 

	 * Permite recuperar todas las asesorias de un unico alumno
	 * 
	 * @return
	 */
	@ApiOperation(value = "Se obtinen las asesorias de un alumno", notes = "Se recibe el id del alumno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se encontraron las asesorias"),
			@ApiResponse(code = 404, message = "No se encontrada"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@GetMapping(path = "/alumnos/{idAlumno}/asesoria", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AsesoriaDto>> retrieveAll(@PathVariable("idAlumno") Long idAlumno){
		List<AsesoriaDto> asesoriasDto = servicioAsesoria.recuperaAsesorias(idAlumno);
		return ResponseEntity.status(HttpStatus.OK).body(asesoriasDto);

	}
	
	/**
	 * Comienza HU-08: Cómo alumno quiero buscar una materia para poder solicitar asesoría.
     * Recupera las asesorias con una mataria en particular
     * 
     * @return
     */
	@ApiOperation(value = "Se buscan asesorias que esten relacionas a una materia", notes = "Se buscan las asesorias que concidan con la materia buscada")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Asesorias de la materia"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 500, message = "Error en el servidor")})
	@GetMapping(path = "/alumnos/asesoria/materia/{idMateria}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AsesoriaDto>> retrieve( @PathVariable("idMateria") Long idMateria ){     
    		log.info("Se van a obtener las asesorias de la materia con id "+ idMateria);
    	
    		List <AsesoriaDto> asesoriasDto =  servicioAsesoria.recuperaAsesoriasMateria(idMateria);
	        return ResponseEntity.status(HttpStatus.OK).body(asesoriasDto);  

    }

}
