package mx.tutouami.service;

import java.util.List;

import javax.validation.Valid;

import mx.tutouami.model.dto.AdviceDTO;
import mx.tutouami.model.dto.CommentDTO;

public interface IAdviceService {
	public List<AdviceDTO> findAll();
	public AdviceDTO create(AdviceDTO adviceDTO, Long studentId);
	public List<AdviceDTO> findBySubject(Long subjectId);
	public AdviceDTO findById(Long adviceId);
	public AdviceDTO updateStatus(Long adviceId, Long studentId, String status);
	public AdviceDTO updateScore(Long adviceId, Float puntuacion);
	public CommentDTO createComment(Long id, CommentDTO comment);
	public CommentDTO updateComment(@Valid Long id, CommentDTO comment);
}
