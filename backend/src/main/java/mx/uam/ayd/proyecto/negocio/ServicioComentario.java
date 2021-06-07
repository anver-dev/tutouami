package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.AlumnoRepository;
import mx.uam.ayd.proyecto.datos.AsesoriaRepository;
import mx.uam.ayd.proyecto.datos.ComentarioRepository;
import mx.uam.ayd.proyecto.dto.AlumnoDto;
import mx.uam.ayd.proyecto.dto.AsesoriaDto;
import mx.uam.ayd.proyecto.dto.ComentarioDto;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.negocio.modelo.Asesoria;
import mx.uam.ayd.proyecto.negocio.modelo.Comentario;

@Service
public class ServicioComentario {
	
	@Autowired 
	private AsesoriaRepository asesoriaRepository;
	
	@Autowired 
	private AlumnoRepository alumnoRepository;
	
	@Autowired 
	private ComentarioRepository comentarioRepository;
	
	@Autowired 
	private ServicioAsesoria servicioAsesoria;
	
	///alumno/{id}/asesesoria/{id}/comentario
	
	public ComentarioDto agregarComentario(Long idAlumno, Long idAsesoria, ComentarioDto comentarioDto) {
	
		// Vemos si esta en la BD el alumno
		Optional<Alumno> optAlumno = alumnoRepository.findById(idAlumno);

		if(optAlumno.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el alumno");
		}
				
		Alumno alumno = optAlumno.get();
		
		
		// Vemos si esta en la BD la asesoria
		Optional<Asesoria> optAsesoria = asesoriaRepository.findById(idAsesoria);

		if(optAsesoria.isEmpty()) {
			throw new IllegalArgumentException("No se encontró la asesoria ");
		}
						
		Asesoria asesoria = optAsesoria.get();
		
		
		
		Comentario comentario = new Comentario();
		
		comentario.setContenido(comentarioDto.getContenido());
		comentario.setFechaCreacion(comentarioDto.getFechaCreacion());
		comentario.setIdAlumno(alumno.getIdAlumno());
		comentario.setIdAsesoria(asesoria.getIdAsesoria());
		comentario.setAlumno(alumno);
		comentario.setAsesoria(asesoria);
		
		comentario = comentarioRepository.save(comentario);
		
		alumno.addComentario(comentario);
		alumnoRepository.save(alumno);
		
		asesoria.addComentario(comentario);
		asesoriaRepository.save(asesoria);
		
		
		
		return ComentarioDto.creaComentarioDto(comentario);
	
	}

	
	/**
	 * Elimina un comentario con un determinado id
	 * 
	 * @param id
	 * @return
	 */

	public boolean delete(Long id) {
		comentarioRepository.deleteById(id);
		Optional <Comentario> optComentario = comentarioRepository.findById(id);
		return optComentario.isPresent();
	}
	
	
	
	public List<ComentarioDto> recuperaComentarios() {
		List<ComentarioDto> comentariosDto = new ArrayList<>();

		for (Comentario comentario : comentarioRepository.findAll()) {
			comentariosDto.add(ComentarioDto.creaComentarioDto(comentario));
		}

		return comentariosDto;
	}

}
