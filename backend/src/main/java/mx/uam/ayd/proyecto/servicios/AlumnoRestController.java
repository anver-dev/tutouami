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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.AlumnoDto;
import mx.uam.ayd.proyecto.dto.MateriaDto;
import mx.uam.ayd.proyecto.negocio.ServicioAlumno;
import mx.uam.ayd.proyecto.negocio.ServicioMateria;

@RestController
@RequestMapping("/v1") // Versionamiento
@Slf4j 
public class AlumnoRestController {
	
	@Autowired
    private ServicioAlumno servicioAlumno;
	
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

}
