package mx.tutouami.repository;

import org.springframework.data.repository.CrudRepository;

import mx.tutouami.model.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}
