package mx.tutouami.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ErrorTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4506519527565077062L;

	public UnauthorizedException(HttpStatus status) {
		super(status);
	}

	public UnauthorizedException(HttpStatus status, String message, Throwable ex) {
		super(status, message, ex);
	}

	public UnauthorizedException(HttpStatus status, Throwable ex) {
		super(status, ex);
	}

	public UnauthorizedException(String message) {
		super(message);
		this.setStatus(HttpStatus.UNAUTHORIZED);
		this.setDebugMessage("NA");
	}

	public UnauthorizedException(String message, Throwable e) {
		super(message);
		this.setStatus(HttpStatus.UNAUTHORIZED);
		this.setDebugMessage(e.getMessage());
	}
}
