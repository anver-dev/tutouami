package mx.tutouami.exceptions;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class EnrolmentAlreadyExistsException extends ErrorTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4506519527565077062L;

	public EnrolmentAlreadyExistsException(HttpStatus status) {
		super(status);
	}

	public EnrolmentAlreadyExistsException(HttpStatus status, String message, Throwable ex) {
		super(status, message, ex);
	}

	public EnrolmentAlreadyExistsException(HttpStatus status, Throwable ex) {
		super(status, ex);
	}

	public EnrolmentAlreadyExistsException(String message, Integer enrolment, Long studentId) {
		super(message);
		this.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		this.setDebugMessage(String.format("Email:: %s already exists for the student:: %d", enrolment, studentId));
	}
}
