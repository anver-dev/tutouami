package mx.uam.ayd.proyecto;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import mx.uam.ayd.proyecto.datos.CarreraRepository;
import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.MateriaRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Carrera;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Materia;

/**
 * 
 * Clase principal que arranca la aplicación 
 * construida usando el principio de 
 * inversión de control
 * 
 * Ejemplo de cambio en Rama
 * 
 * @author humbertocervantes
 *
 */
@SpringBootApplication
public class ProyectoApplication {
	
	@Autowired
	GrupoRepository grupoRepository;
	
	@Autowired
	CarreraRepository carreraRepository;
	
	@Autowired
	MateriaRepository materiaRepository;
	
	/**
	 * 
	 * Método principal
	 * 
	 */
	public static void main(String[] args) {
		
		SpringApplicationBuilder builder = new SpringApplicationBuilder(ProyectoApplication.class);

		builder.headless(false);

		builder.run(args);
	}

	/**
	 * Metodo que arranca la aplicacion
	 * inicializa la bd y arranca el controlador
	 * otro comentario
	 */
	@PostConstruct
	public void inicia() {
		
		inicializaBD();
		
		//controlPrincipal.inicia();
	}
	
	
	/**
	 * 
	 * Inicializa la BD con datos
	 * 
	 * 
	 */
	public void inicializaBD() {
		
		// Vamos a crear una carrera
		Carrera carreraComputacion = new Carrera();
		carreraComputacion.setNombre("Computación");
		carreraRepository.save(carreraComputacion);
		
		//Vamos a crear tres materias
		Materia materiaCompiladores = new Materia();
		materiaCompiladores.setNombre("Compiladores");
		
		Materia materiaIngenieria = new Materia();
		materiaIngenieria.setNombre("Ingenieria de software");
		
		Materia materiaSistemas = new Materia();
		materiaSistemas.setNombre("Sistemas Operativos");
		
		materiaRepository.save(materiaCompiladores);
		materiaRepository.save(materiaIngenieria);
		materiaRepository.save(materiaSistemas);
		
		Grupo grupoAdmin = new Grupo();
		grupoAdmin.setNombre("Administradores");
		grupoRepository.save(grupoAdmin);
		
		Grupo grupoOps = new Grupo();
		grupoOps.setNombre("Operadores");
		grupoRepository.save(grupoOps);
				
	}
}
