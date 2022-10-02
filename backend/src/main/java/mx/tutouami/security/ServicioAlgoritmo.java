package mx.tutouami.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.algorithms.Algorithm;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Getter
@Slf4j
public class ServicioAlgoritmo {
	
	private Algorithm algoritmo;
	
	@Autowired
	public ServicioAlgoritmo(@Value("${security.secret}") String secret) {
		log.info("SECRET:: {} ", secret);
		this.algoritmo = Algorithm.HMAC256(secret);
	}
}
