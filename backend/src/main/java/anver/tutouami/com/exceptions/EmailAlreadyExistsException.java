package anver.tutouami.com.exceptions;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class EmailAlreadyExistsException extends ErrorTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4506519527565077062L;

	public EmailAlreadyExistsException(HttpStatus status) {
		super(status);
	}

	public EmailAlreadyExistsException(HttpStatus status, String message, Throwable ex) {
		super(status, message, ex);
	}

	public EmailAlreadyExistsException(HttpStatus status, Throwable ex) {
		super(status, ex);
	}

	public EmailAlreadyExistsException(String message, String email, Long studentId) {
		super(message);
		this.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		this.setDebugMessage(String.format("Email:: %s already exists for the student:: %d", email, studentId));
	}
}
