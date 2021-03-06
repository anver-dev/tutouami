package mx.uam.ayd.proyecto.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.UsuarioDto;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;

@RestController
@RequestMapping("/v1") // Versionamiento
@Slf4j 
public class UsuarioRestController {

    @Autowired
    private ServicioUsuario servicioUsuarios;
    
    
    /**
     * Permite recuperar todos los usuarios
     * 
     * @return
     */
    @GetMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <List<UsuarioDto>> retrieveAll() {
        
        List <UsuarioDto> usuarios =  servicioUsuarios.recuperaUsuarios();
        
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        
    }

    /**
     * Método que permite agregar un usuario
     * 
     * @param nuevoUsuario
     * @return
     */
    @PostMapping(path = "/usuarios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDto> create(@RequestBody UsuarioDto nuevoUsuario) {
        
        try {
        
            UsuarioDto usuarioDto = servicioUsuarios.agregaUsuario(nuevoUsuario);

            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);

        } catch(Exception ex) {
            
            HttpStatus status;
            
            if(ex instanceof IllegalArgumentException) {
                status = HttpStatus.BAD_REQUEST;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            
            throw new ResponseStatusException(status, ex.getMessage());
        }
        
    }
    
    /**
     * Permite recuperar un usuario a partir de su ID
     * 
     * @param id
     * @return
     */
    @GetMapping(path = "/usuarios/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <UsuarioDto> retrieve(@PathVariable("id") Long id) {
		
    	return null;
    
    }

}

