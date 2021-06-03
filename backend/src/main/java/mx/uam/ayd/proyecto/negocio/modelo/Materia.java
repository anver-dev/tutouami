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

import lombok.Data;

/**
 * Entidad de negocio Materia
 * 
 * @author anver
 *
 */
@Entity
@Data
public class Materia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idMateria;
	private String nombre;
	
	@OneToMany(targetEntity = Asesoria.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idMateria")
	private final List <Asesoria> asesorias = new ArrayList <> ();
	
	/**
	 * 
	 * Permite agregar una asesoria a la materia
	 * 
	 * @param asesoria la asesoria que deseo agregar a la materia
	 * @return true si la asesoria se agregó correctamente, false si no
	 * @throws IllegalArgumentException si la asesoria es nula
	 */
	public boolean addAsesoria(Asesoria asesoria) {

		if(asesoria == null) {
			throw new IllegalArgumentException("El usuario no puede ser null");
		}
		
		if(asesorias.contains(asesoria)) {
			return false;
		}
		
		return asesorias.add(asesoria);
				
	}
	
}
