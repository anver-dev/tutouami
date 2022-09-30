package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Alumno;

public interface AlumnoRepository extends CrudRepository<Alumno, Long> {

	public Alumno findByCorreo(String correo);

	public Alumno findByCorreoAndContrasenia(String correo, String contrasenia);

}
