package mx.tutouami.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import mx.tutouami.config.Seguridad;
import mx.tutouami.model.dto.AdviceDTO;
import mx.tutouami.security.ServicioSeguridad;
import mx.tutouami.service.impl.ServicioAsesoria;

/**
 * Restcontroller para entidad asesoria
 * @author anver
 *
 */
@Slf4j
@RestController
@RequestMapping("/v1") 
@Api(value = "Asesoria")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE })
public class AsesoriaRestController {

	@Autowired
	private ServicioAsesoria servicioAsesoria;

	@Autowired
	private ServicioSeguridad servicioSeguridad;

	/**
	 * Permite recuperar todos las asesorias
	 * 
	 * @return
	 */
	@ApiOperation(value = "Obtiene asesorias", notes = "Se obtienen todas las asesorias")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Asesoria obtenidas exitosamente"),
			@ApiResponse(code = 401, message = "No tienes los permisos necesarios") })
	@GetMapping(path = "/asesorias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AdviceDTO>> retrieveAll(
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {

		if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {
			log.info("Se consulta endpoint /asesorias");
			List<AdviceDTO> asesorias = servicioAsesoria.recuperaAsesorias();

			return ResponseEntity.status(HttpStatus.OK).body(asesorias);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

	}

	/**
	 * Actualiza la puntuacion de una asesoria
	 * 
	 * @param idAsesoria id de la asesoria
	 * @param puntuacion puntuacion que se agregara
	 * @return
	 */
	@ApiOperation(value = "Actualiza la puntuacion de una asesoria", notes = "Se actualiza la puntuacion de una asesoria a través de su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Asesoria actualizada exitosamente"),
			@ApiResponse(code = 401, message = "No tienes los permisos necesarios"),
			@ApiResponse(code = 404, message = "No se encontro la asesoria"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	@PatchMapping(path = "/asesoria/{idAsesoria}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(
			@ApiParam(name = "Authorization", value = "Bearer token", example = Seguridad.HEADER_AUTORIZACION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@PathVariable("idAsesoria") Long idAsesoria, @RequestBody @Valid Integer puntuacion) {

		// Traza
		log.info("Actualizando puntuacion la asesoria con id " + idAsesoria);

		try {
			if (servicioSeguridad.jwtEsValido(authorization.replace("Bearer ", ""))) {
				Optional<AdviceDTO> optionalAsesoria = servicioAsesoria.actualizarPuntuacion(idAsesoria, puntuacion);

				if (!optionalAsesoria.isPresent())
					ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la asesoria");

				return ResponseEntity.status(HttpStatus.CREATED).body(optionalAsesoria);
			}

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
