package mx.uam.ayd.proyecto.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import mx.uam.ayd.proyecto.dto.AsesoriaDto;
import mx.uam.ayd.proyecto.dto.MateriaDto;
import mx.uam.ayd.proyecto.negocio.ServicioAsesoria;
import mx.uam.ayd.proyecto.negocio.ServicioMateria;
import mx.uam.ayd.proyecto.seguridad.ServicioSeguridad;

/**
 * Restcontroller para entidad materia
 * 
 * @author anver
 *
 */
@Slf4j 
@RestController
@RequestMapping("/v1")
@Api(value = "Materia")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET})
public class MateriaRestController {
	
	@Autowired
    private ServicioMateria servicioMateria;
	
	@Autowired
	private ServicioSeguridad servicioSeguridad;
	
	@Autowired
	private ServicioAsesoria servicioAsesoria;
	
	
	/**
     * Permite recuperar todas las materias
     * 
     * @return
     */
	@ApiOperation(value = "Obtiene las materias", notes = "Obtiene todos las materias registradas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Materias mostrados con exito"),
			@ApiResponse(code = 404, message = "No se encontro el alumno")})
    @GetMapping(path = "/materias", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <List<MateriaDto>> retrieveAll(
    		@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {
    	try {
			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {

				log.info("Se consulta endpoint /materias");
		        List <MateriaDto> materias =  servicioMateria.recuperaMaterias();
		        
		        return ResponseEntity.status(HttpStatus.OK).body(materias);

			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
    }
    
    /**
	 * Comienza HU-08: Cómo alumno quiero buscar una materia para poder solicitar
	 * asesoría. Recupera las asesorias con una mataria en particular
	 * 
	 * @return
	 */
	@ApiOperation(value = "Se buscan asesorias que esten relacionas a una materia", notes = "Se buscan las asesorias que concidan con la materia buscada")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Asesorias de la materia"),
			@ApiResponse(code = 404, message = "No se encontro al alumno para agregar la asesoria"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@GetMapping(path = "/materias/{idMateria}/asesorias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AsesoriaDto>> retrieve(
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@PathVariable("idMateria") Long idMateria) {
		log.info("Se van a obtener las asesorias de la materia con id " + idMateria);
		if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {
			List<AsesoriaDto> asesoriasDto = servicioAsesoria.recuperaAsesoriasMateria(idMateria);
			return ResponseEntity.status(HttpStatus.OK).body(asesoriasDto);
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
    
}
