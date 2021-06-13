package mx.uam.ayd.proyecto.seguridad;

import org.springframework.stereotype.Service;

import com.auth0.jwt.algorithms.Algorithm;

import lombok.Getter;
import mx.uam.ayd.proyecto.config.Seguridad;

@Service
@Getter
public class ServicioAlgoritmo {
	
	private Algorithm algoritmo;
	
	public ServicioAlgoritmo() {
		this.algoritmo = Algorithm.HMAC256(Seguridad.SECRETO);
	}

}
