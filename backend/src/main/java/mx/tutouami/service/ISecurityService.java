package mx.tutouami.service;

import java.util.UUID;

import mx.tutouami.model.entity.RefreshToken;
import mx.tutouami.model.entity.Student;

public interface ISecurityService {
	public String generateAccountToken(Student student);
	public RefreshToken generateAccountRefreshToken(Student student);
	public String refreshJwt(String headerAutorizacion, UUID refreshToken);
	public Long getUuidFromJwt(String headerAutorizacion);
	public void jwtValidation(String headerAutorizacion);
}
