package mx.tutouami.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.tutouami.model.entity.Group;
import mx.tutouami.repository.GroupRepository;

@Service
public class GroupServiceImpl {
	
	@Autowired 
	GroupRepository grupoRepository;
	
	
	/**
	 * 
	 * Recupera todos los grupos
	 * 
	 * @return
	 */
	public List <Group> recuperaGrupos() {

		
		
		List <Group> grupos = new ArrayList<>();
		
		for(Group grupo:grupoRepository.findAll()) {
			grupos.add(grupo);
		}
				
		return grupos;
	}

}
