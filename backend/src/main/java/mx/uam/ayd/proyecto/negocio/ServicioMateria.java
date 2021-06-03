package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.MateriaRepository;
import mx.uam.ayd.proyecto.dto.MateriaDto;
import mx.uam.ayd.proyecto.dto.UsuarioDto;
import mx.uam.ayd.proyecto.negocio.modelo.Materia;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@Service
public class ServicioMateria {
	
	@Autowired 
	private MateriaRepository materiaRepository;

	public List<MateriaDto> recuperaMaterias() {
		List<MateriaDto> materiasDto = new ArrayList<>();

		for (Materia materia : materiaRepository.findAll()) {
			materiasDto.add(MateriaDto.creaDto(materia));
		}

		return materiasDto;
	}

}
