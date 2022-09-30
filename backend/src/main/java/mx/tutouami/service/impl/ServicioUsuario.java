package mx.tutouami.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.tutouami.entity.Group;
import mx.tutouami.entity.User;
import mx.tutouami.model.dto.UserDTO;
import mx.tutouami.repository.GrupoRepository;
import mx.tutouami.repository.UsuarioRepository;


@Service
public class ServicioUsuario {
	
	@Autowired 
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	/**
	 * 
	 * Permite agregar un usuario
	 * 
	 * @param nombre
	 * @param apellido
	 * @param grupo
	 * @return
	 */
	public UserDTO agregaUsuario(UserDTO usuarioDto) {
		User usuario = usuarioRepository.findByNombreAndApellido(usuarioDto.getNombre(), usuarioDto.getApellido());
		
		if(usuario != null) {
			throw new IllegalArgumentException("Ese usuario ya existe");
		}
		
		Optional <Group> optGrupo = grupoRepository.findById(usuarioDto.getGrupo());
        
		if(optGrupo.isEmpty()) {
		    throw new IllegalArgumentException("No se encontró el grupo");
		}
		        
		Group grupo = optGrupo.get();

		
		usuario = new User();
		usuario.setNombre(usuarioDto.getNombre());
		usuario.setApellido(usuarioDto.getApellido());
		usuario.setEdad(usuarioDto.getEdad());
		usuario.setGrupo(grupo);
		
		User usuarioCreado = usuarioRepository.save(usuario);

		grupo.addUsuario(usuarioCreado);
		grupoRepository.save(grupo);
		
		usuarioDto = UserDTO.creaDto(usuarioCreado);
		if(usuarioCreado != null) {
			return usuarioDto;
		} else {
			return usuarioDto;
		}
		
	}

	/**
	 * Recupera todos los usuarios existentes
	 * 
	 * @return Una lista con los usuarios (o lista vacía)
	 */
	public List <UserDTO> recuperaUsuarios() {
		
		List <UserDTO> usuariosDto = new ArrayList<>();
		
		for(User usuario:usuarioRepository.findAll()) {
			usuariosDto.add(UserDTO.creaDto(usuario));
		}
				
		return usuariosDto;
	}

}
