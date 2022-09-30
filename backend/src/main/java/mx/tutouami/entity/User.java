package mx.tutouami.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * Entidad de negocio Usuario
 * 
 * @author humbertocervantes
 *
 */
@Entity
@Data
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuario;

    private String nombre;
    
    private String apellido;
    
    private int edad;

    @ManyToOne
    private Group grupo;
}
