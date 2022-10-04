package mx.tutouami.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.tutouami.entity.Advice;
import mx.tutouami.entity.Comment;
import mx.tutouami.entity.Student;
import mx.tutouami.model.dto.CommentDTO;
import mx.tutouami.repository.StudentRepository;
import mx.tutouami.repository.AdviceRepository;
import mx.tutouami.repository.ComentarioRepository;

@Service
@Slf4j
public class ServicioComentario {
	
	@Autowired 
	private AdviceRepository asesoriaRepository;
	
	@Autowired 
	private StudentRepository alumnoRepository;
	
	@Autowired 
	private ComentarioRepository comentarioRepository;
	
	
	
	///alumno/{id}/asesesoria/{id}/comentario
	
	public CommentDTO agregarComentario(Long idAlumno, Long idAsesoria, CommentDTO comentarioDto) {
	
		// Vemos si esta en la BD el alumno
		Optional<Student> optAlumno = alumnoRepository.findById(idAlumno);

		if(optAlumno.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el alumno");
		}
				
		Student alumno = optAlumno.get();
		
		
		// Vemos si esta en la BD la asesoria
		Optional<Advice> optAsesoria = asesoriaRepository.findById(idAsesoria);

		if(optAsesoria.isEmpty()) {
			throw new IllegalArgumentException("No se encontró la asesoria ");
		}
						
		Advice asesoria = optAsesoria.get();
		
		
		
		Comment comentario = new Comment();
		
		comentario.setContenido(comentarioDto.getContenido());
		comentario.setFechaCreacion(comentarioDto.getFechaCreacion());
		comentario.setIdAlumno(alumno.getId());
		comentario.setIdAsesoria(asesoria.getIdAsesoria());
		comentario.setAlumno(alumno);
		comentario.setAsesoria(asesoria);
		
		
		// Regla de negocio un usuario no puede comentarse a si mismo 
		for(Student alumno1: alumnoRepository.findAll()) {
			if(idAlumno.equals(asesoria.getIdAlumno())) {
				throw new IllegalArgumentException("No puedes comentarde a ti mismo ");
			}
		}
		
		comentario = comentarioRepository.save(comentario);
	
		alumno.addComment(comentario);
		alumnoRepository.save(alumno);
		
		asesoria.addComentario(comentario);
		asesoriaRepository.save(asesoria);
		
		
		
		return CommentDTO.creaComentarioDto(comentario);
	
	}
	
	/**
	 * 
	 * @param id
	 * @return alumno con el id
	 */
	public CommentDTO retrieve(Long id){
		Optional<Comment> optComentario = comentarioRepository.findById(id);
		log.info("Recuperando comentario con id: "+ id);
		Comment comentario = optComentario.get();
		return CommentDTO.creaComentarioDto(comentario);
		
	}

	
	/**
	 * Elimina un comentario con un determinado id
	 * En una asesoria en particular
	 * @param id
	 * @return
	 */

	public boolean delete(Long id, Long idAlumno, Long idAsesoria) {
		// Vemos si esta en la BD el alumno
		Optional<Student> optAlumno = alumnoRepository.findById(idAlumno);

		if(optAlumno.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el alumno");
		}
						
		Student alumno = optAlumno.get();
				
				
		// Vemos si esta en la BD la asesoria
		Optional<Advice> optAsesoria = asesoriaRepository.findById(idAsesoria);

		if(optAsesoria.isEmpty()) {
			throw new IllegalArgumentException("No se encontró la asesoria ");
		}
								
		Advice asesoria = optAsesoria.get();
		
		// No se puede borrar un comentario de otro alumno
		for(Student alumno1: alumnoRepository.findAll()) {
			if(idAlumno.equals(asesoria.getIdAlumno())) {
				throw new IllegalArgumentException("No puedes eliminar un comentario de otro alumno");
			}
		}
	
		comentarioRepository.deleteById(id);
		
		
		Optional <Comment> optComentario = comentarioRepository.findById(id);

		
		
		return optComentario.isPresent();
	}
	
	public List<CommentDTO> recuperaComentarios() {
		List<CommentDTO> comentariosDto = new ArrayList<>();

		for (Comment comentario : comentarioRepository.findAll()) {
			comentariosDto.add(CommentDTO.creaComentarioDto(comentario));
		}

		return comentariosDto;
	}
	
	public CommentDTO actualizar(Long idAlumno, Long idComentario, Long idAsesoria, CommentDTO comentarioDto) {
		// Vemos si esta en la BD el alumno
		Optional<Student> optAlumno = alumnoRepository.findById(idAlumno);
		if(optAlumno.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el alumno");
		}		
		Student alumno = optAlumno.get();
		
		//Vemos si esta en la BD el comentario
		Optional<Comment> optComentario = comentarioRepository.findById(idComentario);
		if(optComentario.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el comentario");
		}
		Comment comentario = optComentario.get();
		
		//Vemos si esta en la BD el alumno
		Optional<Advice> optAsesoria = asesoriaRepository.findById(idAsesoria);
		if(optAsesoria.isEmpty()) {
			throw new IllegalArgumentException("No se encontró la asesoria");
		}
		Advice asesoria = optAsesoria.get();
		
		//Se actuliza el contenido 
		comentario.setContenido(comentarioDto.getContenido());
		comentario.setFechaCreacion(comentarioDto.getFechaCreacion());
		comentario.setIdAlumno(alumno.getId());
		comentario.setIdAsesoria(asesoria.getIdAsesoria());
		comentario.setAlumno(alumno);
		comentario.setAsesoria(asesoria);
		
		comentario = comentarioRepository.save(comentario);
		
		asesoria.addComentario(comentario);
		asesoriaRepository.save(asesoria);
		
		alumno.addComment(comentario);
		alumnoRepository.save(alumno);
		
		return CommentDTO.creaComentarioDto(comentario);
	
	}
}
