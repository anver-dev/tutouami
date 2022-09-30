package mx.tutouami.exceptions;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class NotFoundException extends ErrorTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4506519527565077062L;

	public NotFoundException(HttpStatus status) {
		super(status);
	}
	
	public NotFoundException(HttpStatus status, String message, Throwable ex) {
		super(status, message, ex);
	}

	public NotFoundException(HttpStatus status, Throwable ex) {
		super(status, ex);
	}

	public NotFoundException(String message) {
		super(message);
		this.setStatus(HttpStatus.NOT_FOUND);
		this.setDebugMessage("NA");
	}
}
