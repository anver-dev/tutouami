package mx.tutouami.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import lombok.extern.slf4j.Slf4j;
import mx.tutouami.exceptions.UnauthorizedException;
import mx.tutouami.model.entity.RefreshToken;
import mx.tutouami.model.entity.Student;
import mx.tutouami.repository.RefreshTokenRepository;
import mx.tutouami.security.AlgorithmUtil;
import mx.tutouami.service.ISecurityService;

/**
 * Security service
 * 
 * @author anver
 *
 */
@Slf4j
@Service
public class SecurityServiceImpl implements ISecurityService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private AlgorithmUtil algorithmUtil;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Override
	public String generateAccountToken(Student student) {
		log.info(String.format("Generate token for %s", student.getName()));
		return JWT.create().withIssuer(applicationContext.getId()).withSubject(String.valueOf(student.getId()))
				.withClaim("name", student.getName()).withClaim("lastName", student.getLastName())
				.withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis() + 3600000)).sign(algorithmUtil.getAlgorithm());
	}

	@Override
	@Transactional
	public RefreshToken generateAccountRefreshToken(Student alumno) {
		return refreshTokenRepository.save(RefreshToken.builder()
				.issuedAt(System.currentTimeMillis())
				.expireAt(System.currentTimeMillis() + 600000)
				.build());
	}

	@Override
	public String refreshJwt(String headerAutorizacion, UUID refreshToken) {
		/**
		 * Optional<Student> alumnoABuscar =
		 * servicioAlumno.obtenerAlumnoPorId(obtenUuidDeJwt(headerAutorizacion));
		 * 
		 * if (alumnoABuscar.isEmpty()) { return null; }
		 * 
		 * Student alumno = alumnoABuscar.get();
		 * 
		 * Optional<RefreshToken> tokenABuscar =
		 * refreshTokenRepository.findById(refreshToken);
		 * 
		 * // Si no encontramos el refresh token con ese UUID, de una vez regresamos
		 * null if (tokenABuscar.isEmpty()) { return null; }
		 * 
		 * // Revisamos si el usuario tiene ese refresh token y no ha caducado, sino,
		 * igual // regresamos null if (!alumno.containsRefreshToken(tokenABuscar.get())
		 * || tokenABuscar.get().getExpireAt() < System.currentTimeMillis()) { return
		 * null; }
		 * 
		 * return JWT.create()
		 * 
		 * // Esto representa el issuer o emisor que está emitiendo este JWT
		 * .withIssuer(applicationContext.getId())
		 * 
		 * // Esto es el claim sub, el subject o sujeto al cual representa este JWT
		 * .withSubject(String.valueOf(alumno.getIdAlumno()))
		 * 
		 * // Estas son propiedades que queremos que tenga el token, son atributos de la
		 * // entidad en cuestión .withClaim("nombre",
		 * alumno.getNombre()).withClaim("apellido", alumno.getApellidoPaterno())
		 * 
		 * // La fecha de emisión del JWT .withIssuedAt(new
		 * Date(System.currentTimeMillis()))
		 * 
		 * // La fecha de caducidad del JWT .withExpiresAt(new
		 * Date(System.currentTimeMillis() + 3600000))
		 * 
		 * // Al final lo firmamos con el algoritmo que nos provee el servicio de //
		 * algoritmos // notar que usar un servicio de algoritmos nos permite en un
		 * futuro hacer // cambios // sobre el algoritmo (o reemplazarlo en pruebas)
		 * .sign(servicioAlgoritmo.getAlgoritmo());
		 **/
		return null;

	}

	@Override
	public Long getUuidFromJwt(String headerAutorizacion) {
		JWTVerifier verifier = JWT.require(algorithmUtil.getAlgorithm()).build();
		return Long.parseLong(verifier.verify(headerAutorizacion).getSubject());
	}

	@Override
	public void jwtValidation(String headerAutorizacion) {
		String token = headerAutorizacion.replace("Bearer ", "");
		JWTVerifier verifier = JWT.require(algorithmUtil.getAlgorithm()).build();

		try {
			if (verifier.verify(token) == null)
				throw new UnauthorizedException("Invalid token");
		} catch (JWTDecodeException e) {
			throw new UnauthorizedException("Token invalid", e);
		} catch (TokenExpiredException e) {
			throw new UnauthorizedException("Token expired", e);
		}
	}
}
