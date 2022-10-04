package mx.tutouami.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.algorithms.Algorithm;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Getter
@Slf4j
public class AlgorithmUtil {
	
	private Algorithm algorithm;
	
	@Autowired
	public AlgorithmUtil(@Value("${security.secret}") String secret) {
		log.info("SECRET:: {} ", secret);
		this.algorithm = Algorithm.HMAC256(secret);
	}
}
