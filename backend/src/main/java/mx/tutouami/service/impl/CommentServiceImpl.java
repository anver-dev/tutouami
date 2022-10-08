package mx.tutouami.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.tutouami.model.dto.CommentDTO;
import mx.tutouami.model.entity.Advice;
import mx.tutouami.model.entity.Comment;
import mx.tutouami.model.entity.Student;
import mx.tutouami.repository.StudentRepository;
import mx.tutouami.repository.AdviceRepository;
import mx.tutouami.repository.CommentRepository;

@Service
@Slf4j
public class CommentServiceImpl {
	
	@Autowired 
	private AdviceRepository asesoriaRepository;
	
	@Autowired 
	private StudentRepository alumnoRepository;
	
	@Autowired 
	private CommentRepository comentarioRepository;

}
