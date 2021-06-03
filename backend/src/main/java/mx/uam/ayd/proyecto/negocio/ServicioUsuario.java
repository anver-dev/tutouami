package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.dto.UsuarioDto;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@Slf4j
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
	public UsuarioDto agregaUsuario(UsuarioDto usuarioDto) {
		Usuario usuario = usuarioRepository.findByNombreAndApellido(usuarioDto.getNombre(), usuarioDto.getApellido());
		
		if(usuario != null) {
			throw new IllegalArgumentException("Ese usuario ya existe");
		}
		
		Optional <Grupo> optGrupo = grupoRepository.findById(usuarioDto.getGrupo());
        
		if(optGrupo.isEmpty()) {
		    throw new IllegalArgumentException("No se encontró el grupo");
		}
		        
		Grupo grupo = optGrupo.get();

		
		usuario = new Usuario();
		usuario.setNombre(usuarioDto.getNombre());
		usuario.setApellido(usuarioDto.getApellido());
		usuario.setEdad(usuarioDto.getEdad());
		usuario.setGrupo(grupo);
		
		Usuario usuarioCreado = usuarioRepository.save(usuario);

		grupo.addUsuario(usuarioCreado);
		grupoRepository.save(grupo);
		
		usuarioDto = UsuarioDto.creaDto(usuarioCreado);
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
	public List <UsuarioDto> recuperaUsuarios() {
		
		List <UsuarioDto> usuariosDto = new ArrayList<>();
		
		for(Usuario usuario:usuarioRepository.findAll()) {
			usuariosDto.add(UsuarioDto.creaDto(usuario));
		}
				
		return usuariosDto;
	}

}
