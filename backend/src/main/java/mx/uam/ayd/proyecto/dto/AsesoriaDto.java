package mx.uam.ayd.proyecto.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import mx.uam.ayd.proyecto.negocio.modelo.Asesoria;

/**
 * DTO de asesorias
 * 
 */
@Data
public class AsesoriaDto {
	
	private long idAsesoria;
	
	@NotEmpty(message = "El campo no debe ser vacio")
	private String dia;
	
	@NotEmpty(message = "El campo no debe ser vacio")
	private String tipo;
	
	@NotEmpty(message = "El campo no debe ser vacio")
	private String detalles;

	@NotEmpty(message = "El campo no debe ser vacio")
	private String horaInicio;
	
	@NotEmpty(message = "El campo no debe ser vacio")
	private String horaTermino;
	
	@NotNull
	private float costo;

	@NotEmpty(message = "El campo no debe ser vacio")
	private String ubicacion;
	
	@NotNull
	private long alumno;
	
	@NotNull
	private long materia;

	
	/**
	 * Este método permite generar un DTO a partir de la entidad
	 * nota: es un método de clase y no se necesita un objeto
	 * para invocarlo. Se invoca como UsuarioDto.crea(param)
     * @param usuario la entidad
	 * @return dto obtenido a partir de la entidad
	 */
	
	public static AsesoriaDto creaAsesoriaDto(Asesoria asesoria) {
		AsesoriaDto dto = new AsesoriaDto();
		
		dto.setIdAsesoria(asesoria.getIdAsesoria());
		dto.setDia(asesoria.getDia());
		dto.setTipo(asesoria.getTipo());
		dto.setDetalles(asesoria.getDetalles());
		dto.setHoraInicio(asesoria.getHoraInicio());
		dto.setHoraTermino(asesoria.getHoraTermino());
		dto.setCosto(asesoria.getCosto());
		dto.setUbicacion(asesoria.getUbicacion());
		dto.setAlumno(asesoria.getAlumno().getIdAlumno());
		dto.setMateria(asesoria.getMateria().getIdMateria());
		
		return dto;
	}

}
