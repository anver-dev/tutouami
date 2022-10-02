package mx.tutouami.utils;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import mx.tutouami.exceptions.ErrorTemplate;
import mx.tutouami.model.WrapperError;

@Component
public class WrapperErrorUtil {
	
	public WrapperError generate(ErrorTemplate error) {
		return WrapperError.builder()
				.message(error.getMessage())
				.status(error.getStatus())
				.timestamp(error.getTimestamp())
				.build();
	}

	public WrapperError generateBadRequest(String message) {
		return WrapperError.builder()
				.message(message)
				.status(HttpStatus.BAD_REQUEST)
				.timestamp(LocalDateTime.now())
				.build();
	}
}
