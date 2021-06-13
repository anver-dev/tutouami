package mx.uam.ayd.proyecto.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;
import mx.uam.ayd.proyecto.negocio.modelo.Asesoria;
import mx.uam.ayd.proyecto.negocio.modelo.Comentario;
import mx.uam.ayd.proyecto.negocio.modelo.Carrera;


/**
 * DTO de alumnos
 * 
 */
@Data
public class AlumnoDto {
	
	private long idAlumno;
	
	@NotEmpty
	private String nombre;
	

	@NotEmpty
	private String apellidoPaterno;
	
	@NotEmpty
	private String apellidoMaterno;
	
	@Positive
	private int edad;
	
	@Email
	@NotEmpty
	private String correo;
	
	@NotEmpty
	private String contrasenia;
	
	@NotEmpty
	private String telefono;
	
	@NotEmpty
	private String cv;
	
	@Positive
	@Size(min = 0, max = 13, message = "El numero del trimestre debe estar entre 0 y 13")
	private int trimestre;
	
	@Positive
	private float puntuacion;
	
	@Positive
	private int totalPuntuaciones;
	
	@NotEmpty
	private String descripcion;
	
	@NotEmpty
	private String estado;
	
	@NotNull
	private long carrera;
	
	@NotNull
	private long inscripcion;
	
	
	private List <Asesoria> asesorias = new ArrayList <> ();
	
	private List <Comentario> comentarios = new ArrayList <> ();
	
	/**
	 * Este método permite generar un DTO a partir de la entidad
	 * nota: es un método de clase y no se necesita un objeto
	 * para invocarlo. Se invoca como AlumnoDto.crea(param)
     * @param usuario la entidad
	 * @return dto obtenido a partir de la entidad
	 */
	
	public static AlumnoDto creaDto(Alumno alumno) {
		AlumnoDto dto = new AlumnoDto();
		
		dto.setIdAlumno(alumno.getIdAlumno());
		dto.setNombre(alumno.getNombre());
		dto.setApellidoPaterno(alumno.getApellidoPaterno());
		dto.setApellidoMaterno(alumno.getApellidoMaterno());
		dto.setEdad(alumno.getEdad());
		dto.setCorreo(alumno.getCorreo());
		dto.setContrasenia(alumno.getContrasenia());
		dto.setTelefono(alumno.getTelefono());
		dto.setCv(alumno.getCv());
		dto.setPuntuacion(alumno.getPuntuacion());
		dto.setTotalPuntuaciones(alumno.getTotalPuntuaciones());
		dto.setDescripcion(alumno.getDescripcion());
		dto.setEstado(alumno.getEstado());
		dto.setCarrera(alumno.getCarrera().getIdCarrera());
		dto.setComentarios(alumno.getComentarios());
		dto.setAsesorias(alumno.getAsesorias());
		
		return dto;
	}

	/**
	 * Este mÃ©todo permite generar un DTO a partir de la entidad
	 * nota: es un mÃ©todo de clase y no se necesita un objeto
	 * para invocarlo. Se invoca como UsuarioDto.crea(param)
     * @param usuario la entidad
	 * @return dto obtenido a partir de la entidad
	 */
	
	public static AlumnoDto creaAlumnoDto(Alumno alumno) {
		AlumnoDto dto = new AlumnoDto();
		
		dto.setIdAlumno(alumno.getIdAlumno());
		dto.setNombre(alumno.getNombre());
		dto.setApellidoPaterno(alumno.getApellidoPaterno());
		dto.setApellidoMaterno(alumno.getApellidoMaterno());
		dto.setEdad(alumno.getEdad());
		dto.setCorreo(alumno.getCorreo());
		dto.setContrasenia(alumno.getContrasenia());
		dto.setTelefono(alumno.getTelefono());
		dto.setCv(alumno.getCv());
		dto.setTrimestre(alumno.getTrimestre());
		dto.setPuntuacion(alumno.getPuntuacion());
		dto.setTotalPuntuaciones(alumno.getTotalPuntuaciones());
		dto.setDescripcion(alumno.getDescripcion());
		dto.setEstado(alumno.getEstado());
		dto.setCarrera(alumno.getCarrera().getIdCarrera());
		
		return dto;
	}
	
	
	
}
