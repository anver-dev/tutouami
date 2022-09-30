package mx.tutouami.repository;

import org.springframework.data.repository.CrudRepository;

import mx.tutouami.entity.Comment;

public interface ComentarioRepository extends CrudRepository<Comment, Long> {

}
