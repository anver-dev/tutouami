package mx.tutouami.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import mx.tutouami.utils.WrapperErrorUtil;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

	@Autowired
	private WrapperErrorUtil errorUtil;

	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception,
			HttpServletRequest request) {
		log.error(String.format("ERROR: %s", exception.getMessage()));
		return new ResponseEntity<>(
				errorUtil.generateBadRequest(String.format("Verifica tu petición. Payload erroneo")),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
		log.error(String.format("Resource %s", exception.getMessage()));
		return new ResponseEntity<>(errorUtil.generate(exception), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = EmailAlreadyExistsException.class)
	public ResponseEntity<Object> handleEmailAlreadyException(EmailAlreadyExistsException exception) {
		log.error(String.format(exception.getDebugMessage()));
		return new ResponseEntity<>(errorUtil.generate(exception), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = EnrolmentAlreadyExistsException.class)
	public ResponseEntity<Object> handleEnrolmentAlreadyExistsException(EnrolmentAlreadyExistsException exception) {
		log.error(String.format(exception.getDebugMessage()));
		return new ResponseEntity<>(errorUtil.generate(exception), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = UnauthorizedException.class)
	public ResponseEntity<Object> handleNotAuthorizedException(UnauthorizedException exception) {
		log.error(String.format(exception.getDebugMessage()));
		return new ResponseEntity<>(errorUtil.generate(exception), HttpStatus.UNAUTHORIZED);
	}
}
