package mx.tutouami.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.tutouami.entity.Subject;
import mx.tutouami.model.dto.SubjectDTO;
import mx.tutouami.repository.MateriaRepository;

@Service
public class ServicioMateria {
	
	@Autowired 
	private MateriaRepository materiaRepository;

	public List<SubjectDTO> recuperaMaterias() {
		List<SubjectDTO> materiasDto = new ArrayList<>();

		for (Subject materia : materiaRepository.findAll()) {
			materiasDto.add(SubjectDTO.creaDto(materia));
		}

		return materiasDto;
	}

}
