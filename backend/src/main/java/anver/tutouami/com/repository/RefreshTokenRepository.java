package anver.tutouami.com.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import anver.tutouami.com.model.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository <RefreshToken, UUID>  {

}
