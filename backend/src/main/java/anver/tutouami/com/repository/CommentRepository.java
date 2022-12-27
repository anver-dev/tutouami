package anver.tutouami.com.repository;

import org.springframework.data.repository.CrudRepository;

import anver.tutouami.com.model.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}
