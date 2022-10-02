package mx.tutouami.service;

import java.util.UUID;

import mx.tutouami.entity.Student;

public interface ISecurityService {
	public String generateAccountToken(Student student);
	public String generateAccountRefreshToken(String email, String password);
	public String refreshJwt(String headerAutorizacion, UUID refreshToken);
	public Long getUuidDeJwt(String headerAutorizacion);
	public void jwtValidation(String headerAutorizacion);
}
