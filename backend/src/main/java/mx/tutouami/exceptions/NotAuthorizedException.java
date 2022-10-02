package mx.tutouami.exceptions;

import org.springframework.http.HttpStatus;

import com.auth0.jwt.exceptions.JWTDecodeException;

public class NotAuthorizedException extends ErrorTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4506519527565077062L;

	public NotAuthorizedException(HttpStatus status) {
		super(status);
	}

	public NotAuthorizedException(HttpStatus status, String message, Throwable ex) {
		super(status, message, ex);
	}

	public NotAuthorizedException(HttpStatus status, Throwable ex) {
		super(status, ex);
	}

	public NotAuthorizedException(String message) {
		super(message);
		this.setStatus(HttpStatus.NOT_FOUND);
		this.setDebugMessage("NA");
	}

	public NotAuthorizedException(String message, JWTDecodeException e) {
		super(message);
		this.setStatus(HttpStatus.UNAUTHORIZED);
		this.setDebugMessage(e.getMessage());
	}
}
