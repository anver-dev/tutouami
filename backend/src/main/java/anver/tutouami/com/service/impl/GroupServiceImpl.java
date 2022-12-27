package anver.tutouami.com.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import anver.tutouami.com.model.entity.Group;
import anver.tutouami.com.repository.GroupRepository;

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
