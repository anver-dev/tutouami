package anver.tutouami.com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import anver.tutouami.com.model.dto.CommentDTO;
import anver.tutouami.com.model.entity.Advice;
import anver.tutouami.com.model.entity.Comment;
import anver.tutouami.com.model.entity.Student;
import anver.tutouami.com.repository.AdviceRepository;
import anver.tutouami.com.repository.CommentRepository;
import anver.tutouami.com.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;

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
