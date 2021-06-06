package mx.uam.ayd.proyecto.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.negocio.modelo.Asesoria;

/**
 * DTO de asesorias
 * 
 */
@Data
public class AsesoriaDto {
private long idAsesoria;
	
	@NotEmpty
	private String dia;
	
	@NotEmpty
	private String tipo;
	
	@NotEmpty
	private String detalles;

	@NotEmpty
	private String horaInicio;
	
	@NotEmpty
	private String horaTermino;
	
	@NotNull
	private float costo;

	@NotEmpty
	private String ubicacion;
	
	@NotNull
	private long materia;
	
	@NotNull
	private long alumno;


	/**
	 * Este mÃ©todo permite generar un DTO a partir de la entidad
	 * nota: es un mÃ©todo de clase y no se necesita un objeto
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
		dto.setMateria(asesoria.getMateria().getIdMateria());
		dto.setAlumno(asesoria.getIdAlumno());
		return dto;
	}

}
