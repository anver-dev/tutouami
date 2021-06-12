package mx.uam.ayd.proyecto.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.MateriaDto;
import mx.uam.ayd.proyecto.negocio.ServicioMateria;
import mx.uam.ayd.proyecto.seguridad.ServicioSeguridad;

@RestController
@RequestMapping("/v1") // Versionamiento
@Slf4j 
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class MateriaRestController {
	
	@Autowired
    private ServicioMateria servicioMateria;
	
	@Autowired
	private ServicioSeguridad servicioSeguridad;
	
	/**
     * Permite recuperar todas las materias
     * 
     * @return
     */
    @GetMapping(path = "/materias", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <List<MateriaDto>> retrieveAll(
    		@ApiParam(name = "Authorization", value = "Bearer token", example = ServicioSeguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {
    	try {

			// Revisamos si es un JWT válido para esta petición, quitamos la parte de bearer
			// del header para tener solo el JWT
    		log.info("validamos el token");
			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {

				// Obtenemos el UUID que viene en el JWT, quitamos la parte de bearer del header
				// para tener solo el JWT
				// UUID uuid = servicioSeguridad.obtenUuidDeJwt(authorization.replace("Bearer ",
				// ""));

				// Comparamos el UUID solicitado al controlador con el que viene en el token
				// solo aceptamos peticiones para el usuario del token, si esta UUID
				// es la misma y el usuario existe, regresamos el usuario
//				if (id.equals(uuid) && servicioUsuarios.recuperaUsuario(uuid).isPresent()) {
//					return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioDto.creaDto(servicioUsuarios.recuperaUsuario(uuid).get()));
//				}
				log.info("Se consulta endpoint /materias");
		        List <MateriaDto> materias =  servicioMateria.recuperaMaterias();
		        
		        return ResponseEntity.status(HttpStatus.OK).body(materias);

			}

			// Cualquier otro caso, regresamos un no autorizado
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
    	
        
    }
    
}
