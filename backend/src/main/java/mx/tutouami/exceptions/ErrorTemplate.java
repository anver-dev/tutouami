package mx.tutouami.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ErrorTemplate extends RuntimeException implements Serializable {
	
	private static final long serialVersionUID = 4197199541911769000L;

	private HttpStatus status;
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;

	private ErrorTemplate() {
		timestamp = LocalDateTime.now();
	}

	ErrorTemplate(HttpStatus status) {
		this();
		this.status = status;
	}
	
	ErrorTemplate(String message) {
		this();
		this.message = message;
	}

	ErrorTemplate(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	ErrorTemplate(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}
}
