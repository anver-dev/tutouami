package mx.uam.ayd.proyecto.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import mx.uam.ayd.proyecto.negocio.modelo.Asesoria;
import mx.uam.ayd.proyecto.negocio.modelo.Materia;

/**
 * DTO de materias
 * 
 */
@Data
public class MateriaDto {
	private long idMateria;
	private String nombre;
	
	
	private List <Asesoria> asesorias = new ArrayList <> ();
	
	 public static MateriaDto creaDto(Materia materia) {
	        MateriaDto dto = new MateriaDto();

	        dto.setIdMateria(materia.getIdMateria());
	        dto.setNombre(materia.getNombre());
	        dto.setAsesorias(materia.getAsesorias());
	        
	        return dto;
	    }
}
