package mx.tutouami.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import mx.tutouami.entity.Advice;
import mx.tutouami.entity.Subject;

/**
 * DTO de materias
 * 
 */
@Data
public class SubjectDTO {
	private long idMateria;
	private String nombre;
	
	
	private List <Advice> asesorias = new ArrayList <> ();
	
	 public static SubjectDTO creaDto(Subject materia) {
	        SubjectDTO dto = new SubjectDTO();

	        dto.setIdMateria(materia.getIdMateria());
	        dto.setNombre(materia.getNombre());
	        dto.setAsesorias(materia.getAsesorias());
	        
	        return dto;
	    }
}
