package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Comentario;

public interface ComentarioRepository extends CrudRepository<Comentario, Long> {

}
