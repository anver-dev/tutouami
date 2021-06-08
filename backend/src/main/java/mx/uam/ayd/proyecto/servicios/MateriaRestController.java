package mx.uam.ayd.proyecto.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.MateriaDto;
import mx.uam.ayd.proyecto.negocio.ServicioMateria;

@RestController
@RequestMapping("/v1") // Versionamiento
@Slf4j 
public class MateriaRestController {
	
	@Autowired
    private ServicioMateria servicioMateria;
	
	/**
     * Permite recuperar todas las materias
     * 
     * @return
     */
    @GetMapping(path = "/materias", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <List<MateriaDto>> retrieveAll() {
        
    	log.info("Se consulta endpoint /materias");
        List <MateriaDto> materias =  servicioMateria.recuperaMaterias();
        
        return ResponseEntity.status(HttpStatus.OK).body(materias);
        
    }
    
}
