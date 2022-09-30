package mx.tutouami.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import mx.tutouami.model.dto.StudentDTO;
import mx.tutouami.model.SecurityExamples;
import mx.tutouami.model.dto.InscriptionDTO;
import mx.tutouami.security.ServicioSeguridad;
import mx.tutouami.service.impl.ServicioInscripcion;

@RestController
@RequestMapping("/v1") 
@Slf4j
public class InscripcionRestController {

	
	
	@Autowired
	private ServicioSeguridad servicioSeguridad;
	
	
}
