package mx.tutouami.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import mx.tutouami.utils.WrapperErrorUtil;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
	
	@Autowired
	private WrapperErrorUtil errorUtil;

	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<Object> exception(NotFoundException exception) {
		log.error(String.format("Resource %s", exception.getMessage()));
		return new ResponseEntity<>(errorUtil.generate(exception), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = EmailAlreadyExistsException.class)
	public ResponseEntity<Object> exception(EmailAlreadyExistsException exception) {
		log.error(String.format(exception.getDebugMessage()));
		return new ResponseEntity<>(errorUtil.generate(exception), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
