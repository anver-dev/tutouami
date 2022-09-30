package mx.uam.ayd.proyecto.negocio.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Entidad de negocio Carrera
 * 
 * @author anver
 *
 */
@Entity
@Data
public class Carrera {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCarrera;
	private String nombre;
	
	@OneToMany(targetEntity = Alumno.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idCarrera")
	@JsonIgnore
	private final List <Alumno> alumnos = new ArrayList <> ();
	
	/**
	 * 
	 * Permite agregar un alumno a la carrera
	 * 
	 * @param alumno el alumno que deseo agregar a la carrera
	 * @return true si el alumno se agregó correctamente, false si no
	 * @throws IllegalArgumentException si el alumno es nulo
	 */
	public boolean addAlumno(Alumno alumno) {

		if(alumno == null) {
			throw new IllegalArgumentException("El usuario no puede ser null");
		}
		
		if(alumnos.contains(alumno)) {
			return false;
		}
		
		return alumnos.add(alumno);
				
	}
	
	
}
