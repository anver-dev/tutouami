package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.AlumnoRepository;
import mx.uam.ayd.proyecto.datos.AsesoriaRepository;
import mx.uam.ayd.proyecto.datos.MateriaRepository;
import mx.uam.ayd.proyecto.dto.AsesoriaDto;
import mx.uam.ayd.proyecto.dto.MateriaDto;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.negocio.modelo.Asesoria;
import mx.uam.ayd.proyecto.negocio.modelo.Materia;

@Service
public class ServicioAsesoria {

	@Autowired 
	private AsesoriaRepository asesoriaRepository;
	
	@Autowired 
	private AlumnoRepository alumnoRepository;
	
	@Autowired 
	private MateriaRepository materiaRepository;

	public AsesoriaDto agregaAsesoria(AsesoriaDto asesoriaDto, Long id) {

		// Vemos si esta en la BD el alumno
		Optional<Alumno> optAlumno = alumnoRepository.findById(id);
		
		if(optAlumno.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el alumno");
		}
		
		Alumno alumno = optAlumno.get();
		Optional<Materia> optMateria = materiaRepository.findById(asesoriaDto.getMateria());
		
		if(optMateria.isEmpty()) {
			throw new IllegalArgumentException("No se encontró la materia");
		}
		
		Materia materia = optMateria.get();
		
		Asesoria asesoria = new Asesoria();

		asesoria.setDia(asesoriaDto.getDia());
		asesoria.setTipo(asesoriaDto.getTipo());
		asesoria.setDetalles(asesoriaDto.getDetalles());
		asesoria.setHoraInicio(asesoriaDto.getHoraInicio());
		asesoria.setHoraTermino(asesoriaDto.getHoraTermino());
		asesoria.setCosto(asesoriaDto.getCosto());
		asesoria.setUbicacion(asesoriaDto.getUbicacion());
		asesoria.setMateria(materia);
		asesoria.setIdAlumno(alumno.getIdAlumno());

		asesoria = asesoriaRepository.save(asesoria);
		
		materia.addAsesoria(asesoria);
		materiaRepository.save(materia);
		
		
		alumno.addAsesoria(asesoria);
		alumnoRepository.save(alumno);
		
		
		
		return AsesoriaDto.creaAsesoriaDto(asesoria);
	}
	
	public List<AsesoriaDto> recuperaAsesorias() {
		List<AsesoriaDto> asesoriasDto = new ArrayList<>();

		for (Asesoria asesoria : asesoriaRepository.findAll()) {
			asesoriasDto.add(AsesoriaDto.creaAsesoriaDto(asesoria));
		}

		return asesoriasDto;
	}
	
	
}
