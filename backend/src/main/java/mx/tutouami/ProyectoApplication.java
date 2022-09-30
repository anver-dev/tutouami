package mx.tutouami;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import mx.tutouami.entity.Advice;
import mx.tutouami.entity.Degree;
import mx.tutouami.entity.Group;
import mx.tutouami.entity.Student;
import mx.tutouami.entity.Subject;
import mx.tutouami.repository.StudentRepository;
import mx.tutouami.repository.AsesoriaRepository;
import mx.tutouami.repository.CarreraRepository;
import mx.tutouami.repository.GrupoRepository;
import mx.tutouami.repository.MateriaRepository;

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
	StudentRepository  studentRepository;
	
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
	/*
	 * Se hizo  la rama HU14F
	 * **/
	
	/**
	 * 
	 * Inicializa la BD con datos
	 * 
	 * 
	 */
	public void inicializaBD() {
		
		// Vamos a crear una carrera
		Degree carreraComputacion = new Degree();
		carreraComputacion.setNombre("Computación");
		carreraRepository.save(carreraComputacion);
		
		Degree carreraElectronica = new Degree();
		carreraElectronica.setNombre("Electronica");
		carreraRepository.save(carreraElectronica);
		
		//Vamos a crear tres materias
		Subject materiaCompiladores = new Subject();
		materiaCompiladores.setNombre("Compiladores");
		
		Subject materiaIngenieria = new Subject();
		materiaIngenieria.setNombre("Ingenieria de software");
		
		Subject materiaSistemas = new Subject();
		materiaSistemas.setNombre("Sistemas Operativos");
		
		materiaRepository.save(materiaCompiladores);
		materiaRepository.save(materiaIngenieria);
		materiaRepository.save(materiaSistemas);
		

		//Se agrego un alumno de prueba
		Student student = studentRepository.save(Student.builder()
				.name("Victor")
				.lastName("Sosa")
				.secondLastName("Pina")
				.age(21)
				.email("manuel@gmail.com")
				.phone("123456789")
				.cv("CV DESCRIPTION")
				.trimester(9)
				.score(7.5F)
				.totalScore(115)
				.description("DESCRIPTION")
				.status("LIBRE")
				.degree(carreraElectronica)
				.build());
		
		carreraElectronica.addAlumno(student);
		carreraRepository.save(carreraElectronica);
		
		
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
				
	}
}
