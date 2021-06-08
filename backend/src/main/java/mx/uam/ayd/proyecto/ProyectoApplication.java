package mx.uam.ayd.proyecto;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import mx.uam.ayd.proyecto.datos.AlumnoRepository;

import mx.uam.ayd.proyecto.datos.AsesoriaRepository;


import mx.uam.ayd.proyecto.datos.CarreraRepository;
import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.MateriaRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Alumno;

import mx.uam.ayd.proyecto.negocio.modelo.Asesoria;

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
	AsesoriaRepository asesoriaRepository;
	
	@Autowired
	AlumnoRepository  alumnoRepository;
	
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
		
		//Se agrego un alumno de prueba
		
		Alumno alumno = new Alumno();
		alumno.setNombre("Victor");
		alumno.setApellidoPaterno("Sosa");
		alumno.setApellidoMaterno("Pina");
		alumno.setEdad(21);
		alumno.setCorreo("manuel@gmail.com");
		alumno.setContrasenia("123");
		alumno.setTelefono("55555");
		alumno.setCv("El mejor");
		alumno.setTrimestre(9);
		alumno.setPuntuacion(4);
		alumno.setTotalPuntuaciones(0);
		alumno.setDescripcion("dos tres");
		alumno.setEstado("Libre");
		alumnoRepository.save(alumno);
		
		Alumno alumno1 = new Alumno();
		alumno1.setNombre("Victor");
		alumno1.setApellidoPaterno("Sosa");
		alumno1.setApellidoMaterno("Pina");
		alumno1.setEdad(21);
		alumno1.setCorreo("manuel@gmail.com");
		alumno1.setContrasenia("123");
		alumno1.setTelefono("55555");
		alumno1.setCv("El mejor");
		alumno1.setTrimestre(9);
		alumno1.setPuntuacion(4);
		alumno1.setTotalPuntuaciones(0);
		alumno1.setDescripcion("dos tres");
		alumno1.setEstado("Libre");
		alumnoRepository.save(alumno1);
		
		//Se agrego una asesoria de prueba
		/*
		Asesoria asesoria = new Asesoria();
		asesoria.setDia("04/07/2021");
		asesoria.setHoraInicio("12");
		asesoria.setHoraTermino("2");
		asesoria.setPuntuacion(3);
		asesoria.setTotalPuntuaciones(4);
		asesoria.setDetalles("Bueno");
		asesoria.setTipo("Alumno");
		asesoria.setUbicacion("AT-309");
		asesoria.setCosto(20);
		asesoria.setUrl("www.uam.com");
		asesoria.setEstado("Libre");
		asesoriaRepository.save(asesoria);
		
		alumno.addAsesoria(asesoria);
		alumnoRepository.save(alumno);
		
		materiaSistemas.addAsesoria(asesoria);
		materiaRepository.save(materiaSistemas);
		*/

		Alumno alu1 = new Alumno();
		alu1.setNombre("Gonzalo");
		alumnoRepository.save(alu1);
		
		Alumno alu2 = new Alumno();
		alu2.setNombre("Victor");
		alumnoRepository.save(alu2);
		
		Grupo grupoAdmin = new Grupo();
		grupoAdmin.setNombre("Administradores");
		grupoRepository.save(grupoAdmin);
		
		Grupo grupoOps = new Grupo();
		grupoOps.setNombre("Operadores");
		grupoRepository.save(grupoOps);
				
	}
}
