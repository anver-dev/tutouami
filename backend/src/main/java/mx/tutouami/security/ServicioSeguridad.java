package mx.tutouami.security;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;

import mx.tutouami.entity.RefreshToken;
import mx.tutouami.entity.Student;
import mx.tutouami.repository.RefreshTokenRepository;
import mx.tutouami.service.impl.StudentServiceImpl;

/**
 * Servicio de seguridad
 * @author anver
 *
 */
@Service
public class ServicioSeguridad {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private StudentServiceImpl servicioAlumno;
	
	@Autowired
	private ServicioAlgoritmo servicioAlgoritmo;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;


	public String generaTokenUsuario(String correo, String contrasenia) {
		/**
		Optional<Student> alumnoABuscar = servicioAlumno.obtenerAlumnoPorCorreoYContrasenia(correo, contrasenia);

		if (!alumnoABuscar.isPresent()) 
			return null;

		Student alumno = alumnoABuscar.get();

		return JWT.create()

				// Esto representa el issuer o emisor que está emitiendo este JWT
				.withIssuer(applicationContext.getId())

				// Esto es el claim sub, el subject o sujeto al cual representa este JWT
				.withSubject(String.valueOf(alumno.getIdAlumno()))

				// Estas son propiedades que queremos que tenga el token, son atributos de la
				// entidad en cuestión
				.withClaim("nombre", alumno.getNombre()).withClaim("apellido", alumno.getApellidoPaterno())

				// La fecha de emisión del JWT
				.withIssuedAt(new Date(System.currentTimeMillis()))

				// La fecha de caducidad del JWT
				.withExpiresAt(new Date(System.currentTimeMillis() + 3600000))

				// Al final lo firmamos con el algoritmo que nos provee el servicio de
				// algoritmos
				// notar que usar un servicio de algoritmos nos permite en un futuro hacer
				// cambios
				// sobre el algoritmo (o reemplazarlo en pruebas)
				.sign(servicioAlgoritmo.getAlgoritmo());
				**/
		return null;

	}

	@Transactional
	public String generaRefreshTokenUsuario(String correo, String contrasenia) {
		/**
		Optional<Student> opAlumnoABuscar = servicioAlumno.obtenerAlumnoPorCorreoYContrasenia(correo, contrasenia);

		if (opAlumnoABuscar.isEmpty()) {
			return null;
		}

		Student alumno = opAlumnoABuscar.get();

		// Le generamos su refresh token
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setIssuedAt(System.currentTimeMillis());
		refreshToken.setExpireAt(System.currentTimeMillis() + 600000);
		refreshToken = refreshTokenRepository.save(refreshToken);

		alumno.getRefreshTokens().add(refreshToken);
		servicioAlumno.guardarAlumno(alumno);

		return refreshToken.getId().toString();
		**/
		return null;
	}

	public String refrescaJwt(String headerAutorizacion, UUID refreshToken) {
		/**
		Optional<Student> alumnoABuscar = servicioAlumno.obtenerAlumnoPorId(obtenUuidDeJwt(headerAutorizacion));

		if (alumnoABuscar.isEmpty()) {
			return null;
		}

		Student alumno = alumnoABuscar.get();

		Optional<RefreshToken> tokenABuscar = refreshTokenRepository.findById(refreshToken);

		// Si no encontramos el refresh token con ese UUID, de una vez regresamos null
		if (tokenABuscar.isEmpty()) {
			return null;
		}

		// Revisamos si el usuario tiene ese refresh token y no ha caducado, sino, igual
		// regresamos null
		if (!alumno.containsRefreshToken(tokenABuscar.get())
				|| tokenABuscar.get().getExpireAt() < System.currentTimeMillis()) {
			return null;
		}

		return JWT.create()

				// Esto representa el issuer o emisor que está emitiendo este JWT
				.withIssuer(applicationContext.getId())

				// Esto es el claim sub, el subject o sujeto al cual representa este JWT
				.withSubject(String.valueOf(alumno.getIdAlumno()))

				// Estas son propiedades que queremos que tenga el token, son atributos de la
				// entidad en cuestión
				.withClaim("nombre", alumno.getNombre()).withClaim("apellido", alumno.getApellidoPaterno())

				// La fecha de emisión del JWT
				.withIssuedAt(new Date(System.currentTimeMillis()))

				// La fecha de caducidad del JWT
				.withExpiresAt(new Date(System.currentTimeMillis() + 3600000))

				// Al final lo firmamos con el algoritmo que nos provee el servicio de
				// algoritmos
				// notar que usar un servicio de algoritmos nos permite en un futuro hacer
				// cambios
				// sobre el algoritmo (o reemplazarlo en pruebas)
				.sign(servicioAlgoritmo.getAlgoritmo());
				**/
		return null;

	}

	public Long obtenUuidDeJwt(String headerAutorizacion) {

		// Creamos un verificador de JWT con el algoritmo que usamos para crearlos
		JWTVerifier verifier = JWT.require(servicioAlgoritmo.getAlgoritmo()).build();

		// Si no se arrojó una excepción el objeto será diferente de nulo y podremos
		// obtener
		// el claim subject que tiene el UUID del usuario
		return Long.parseLong(verifier.verify(headerAutorizacion).getSubject());
	}

	public Boolean jwtEsValido(String headerAutorizacion) {

		// Creamos un verificador de JWT con el algoritmo que usamos para crearlos
		JWTVerifier verifier = JWT.require(servicioAlgoritmo.getAlgoritmo()).build();

		// Si no se arrojó una excepción el objeto será diferente de nulo
		return verifier.verify(headerAutorizacion) != null;
	}
	
	

}
