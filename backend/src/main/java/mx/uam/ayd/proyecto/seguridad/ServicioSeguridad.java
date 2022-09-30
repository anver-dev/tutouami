package mx.uam.ayd.proyecto.seguridad;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;

import mx.uam.ayd.proyecto.datos.RefreshTokenRepository;
import mx.uam.ayd.proyecto.negocio.ServicioAlumno;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.negocio.modelo.RefreshToken;

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
	private ServicioAlumno servicioAlumno;
	
	@Autowired
	private ServicioAlgoritmo servicioAlgoritmo;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;


	public String generaTokenUsuario(String correo, String contrasenia) {
		Optional<Alumno> alumnoABuscar = servicioAlumno.obtenerAlumnoPorCorreoYContrasenia(correo, contrasenia);

		if (!alumnoABuscar.isPresent()) 
			return null;

		Alumno alumno = alumnoABuscar.get();

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

	}

	@Transactional
	public String generaRefreshTokenUsuario(String correo, String contrasenia) {
		Optional<Alumno> opAlumnoABuscar = servicioAlumno.obtenerAlumnoPorCorreoYContrasenia(correo, contrasenia);

		if (opAlumnoABuscar.isEmpty()) {
			return null;
		}

		Alumno alumno = opAlumnoABuscar.get();

		// Le generamos su refresh token
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setIssuedAt(System.currentTimeMillis());
		refreshToken.setExpireAt(System.currentTimeMillis() + 600000);
		refreshToken = refreshTokenRepository.save(refreshToken);

		alumno.getRefreshTokens().add(refreshToken);
		servicioAlumno.guardarAlumno(alumno);

		return refreshToken.getId().toString();
	}

	public String refrescaJwt(String headerAutorizacion, UUID refreshToken) {
		Optional<Alumno> alumnoABuscar = servicioAlumno.obtenerAlumnoPorId(obtenUuidDeJwt(headerAutorizacion));

		if (alumnoABuscar.isEmpty()) {
			return null;
		}

		Alumno alumno = alumnoABuscar.get();

		Optional<RefreshToken> tokenABuscar = refreshTokenRepository.findById(refreshToken);

		// Si no encontramos el refresh token con ese UUID, de una vez regresamos null
		if (tokenABuscar.isEmpty()) {
			return null;
		}

		// Revisamos si el usuario tiene ese refresh token y no ha caducado, sino, igual
		// regresamos null
		if (!alumno.tieneElRefreshToken(tokenABuscar.get())
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
