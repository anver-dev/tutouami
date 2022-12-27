package anver.tutouami.com.model.dto;

import java.util.ArrayList;
import java.util.List;

import anver.tutouami.com.model.entity.Advice;
import anver.tutouami.com.model.entity.Subject;
import lombok.Data;

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
