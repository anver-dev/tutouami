package mx.tutouami.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import mx.tutouami.model.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository <RefreshToken, UUID>  {

}
