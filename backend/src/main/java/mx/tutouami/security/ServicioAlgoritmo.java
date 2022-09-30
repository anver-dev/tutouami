package mx.tutouami.security;

import org.springframework.stereotype.Service;

import com.auth0.jwt.algorithms.Algorithm;

import lombok.Getter;
import mx.tutouami.config.Seguridad;

@Service
@Getter
public class ServicioAlgoritmo {
	
	private Algorithm algoritmo;
	
	public ServicioAlgoritmo() {
		this.algoritmo = Algorithm.HMAC256(Seguridad.SECRETO);
	}

}
