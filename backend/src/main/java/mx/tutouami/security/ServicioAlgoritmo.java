package mx.tutouami.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.algorithms.Algorithm;

import lombok.Getter;

@Service
@Getter
public class ServicioAlgoritmo {
	
	private Algorithm algoritmo;
	
	private String secret;
	
	@Autowired
	public ServicioAlgoritmo(@Value("${security.secret}") String secret) {
		this.secret = secret;
	}
	
	public ServicioAlgoritmo() {
		this.algoritmo = Algorithm.HMAC256(secret);
	}

}
