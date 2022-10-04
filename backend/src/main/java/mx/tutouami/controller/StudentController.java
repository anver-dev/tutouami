package mx.tutouami.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import mx.tutouami.model.dto.StatusDTO;
import mx.tutouami.model.dto.StudentDTO;
import mx.tutouami.service.ISecurityService;
import mx.tutouami.service.IStudentService;

/**
 * Controller for the students
 * 
 * @author anver
 *
 */
@RestController
@RequestMapping("/students")
@Api(value = "Student")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH,
		RequestMethod.DELETE })
public class StudentController {

	@Autowired
	private IStudentService studentService;

	@Autowired
	private ISecurityService securityService;

	@ApiOperation(value = "Get all", notes = "Get all students")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Students found successful"),
			@ApiResponse(code = 401, message = "No authorized"),
			@ApiResponse(code = 404, message = "Students not found"),
			@ApiResponse(code = 500, message = "Server error") })
	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentDTO>> getAll(
			@ApiParam(name = "Authorization", value = "Bearer token", example = SecurityExamples.HEADER_AUTHORIZATION, required = true) 
			@RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {
		securityService.jwtValidation(authorization);
		return ResponseEntity.status(HttpStatus.OK).body(studentService.findAll());

	}

	@ApiOperation(value = "Get student", notes = "Get student by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Student found successful"),
			@ApiResponse(code = 401, message = "No authorized"),
			@ApiResponse(code = 404, message = "Student not found"),
			@ApiResponse(code = 500, message = "Server error") })
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentDTO> getById(@PathVariable("id") Long id,
			@ApiParam(name = "Authorization", value = "Bearer token", example = SecurityExamples.HEADER_AUTHORIZATION, required = true) 
			@RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {
		securityService.jwtValidation(authorization);
		return ResponseEntity.status(HttpStatus.OK).body(studentService.findById(id));
	}

	@ApiOperation(value = "Create student", notes = "Create new student")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Student created successful"),
			@ApiResponse(code = 401, message = "No authorized"), @ApiResponse(code = 500, message = "Server error") })
	@PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentDTO> create(@RequestBody StudentDTO student,
			@ApiParam(name = "Authorization", value = "Bearer token", example = SecurityExamples.HEADER_AUTHORIZATION, required = true) 
			@RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {
		securityService.jwtValidation(authorization);
		return ResponseEntity.status(HttpStatus.OK).body(studentService.create(student));
	}

	@ApiOperation(value = "Update student status", notes = "Update status of the student")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Status updated successful"),
			@ApiResponse(code = 401, message = "No authorized"),
			@ApiResponse(code = 404, message = "Student not found"),
			@ApiResponse(code = 500, message = "Server error") })
	@PatchMapping(path = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentDTO> updateStatus(@PathVariable("id") Long id, @RequestBody StatusDTO status,
			@ApiParam(name = "Authorization", value = "Bearer token", example = SecurityExamples.HEADER_AUTHORIZATION, required = true) 
			@RequestHeader(value = "Authorization", name = "Authorization", required = true) String authorization) {
		securityService.jwtValidation(authorization);
		return ResponseEntity.status(HttpStatus.OK).body(studentService.updateStatus(id, status));
	}
}
