package mx.tutouami.utils;

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
}
