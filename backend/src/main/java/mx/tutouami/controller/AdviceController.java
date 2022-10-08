package mx.tutouami.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.tutouami.model.SecurityExamples;
import mx.tutouami.model.dto.AdviceDTO;
import mx.tutouami.model.dto.CommentDTO;
import mx.tutouami.service.IAdviceService;
import mx.tutouami.service.ISecurityService;

/**
 * Advice controller
 * 
 * @author anver
 *
 */
@RestController
@RequestMapping("/advices")
@Api(value = "Advice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH,
		RequestMethod.DELETE })
public class AdviceController {

	@Autowired
	private IAdviceService adviceService;

	@Autowired
	private ISecurityService securityService;

	@ApiOperation(value = "Get advices", notes = "Get all advices")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Get all advices successful"),
			@ApiResponse(code = 401, message = "Not authorized") })
	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AdviceDTO>> getAll(
			@ApiParam(name = "Authorization", value = "Bearer token", example = SecurityExamples.HEADER_AUTHORIZATION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {
		securityService.jwtValidation(authorization);
		return ResponseEntity.status(HttpStatus.OK).body(adviceService.findAll());
	}

	@ApiOperation(value = "Update advice score", notes = "Update score of advice")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Score update successful"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 404, message = "Advice not found"),
			@ApiResponse(code = 500, message = "Server error") })
	@PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(
			@ApiParam(name = "Authorization", value = "Bearer token", example = SecurityExamples.HEADER_AUTHORIZATION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id, @RequestBody @Valid Float puntuacion) {
		securityService.jwtValidation(authorization);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(adviceService.updateScore(id, puntuacion));
	}

	@ApiOperation(value = "Create new comment for advice id", notes = "Create new comment")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Comment created successful"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Student or advice not found"),
			@ApiResponse(code = 500, message = "Internal error") })
	@PostMapping(path = "/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createComment(
			@ApiParam(name = "Authorization", value = "Bearer token", example = SecurityExamples.HEADER_AUTHORIZATION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@RequestBody @Valid CommentDTO comment, @PathVariable("id") Long id) {
		securityService.jwtValidation(authorization);
		return ResponseEntity.status(HttpStatus.CREATED).body(adviceService.createComment(id, comment));
	}

	@ApiOperation(value = "Update comment", notes = "Update comment of advice")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Comment updated successful"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 404, message = "Advice not found"),
			@ApiResponse(code = 500, message = "Server error") })
	@PutMapping(path = "/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommentDTO> updateComment(
			@ApiParam(name = "Authorization", value = "Bearer token", example = SecurityExamples.HEADER_AUTHORIZATION, required = true) @RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization,
			@PathVariable("id") @Valid Long id, @RequestBody @Valid CommentDTO comment) {
		securityService.jwtValidation(authorization);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(adviceService.updateComment(id, comment));
	}
}
