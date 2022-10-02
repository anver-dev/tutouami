package mx.tutouami;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import mx.tutouami.entity.Account;
import mx.tutouami.entity.Advice;
import mx.tutouami.entity.Degree;
import mx.tutouami.entity.Group;
import mx.tutouami.entity.Student;
import mx.tutouami.entity.Subject;
import mx.tutouami.repository.StudentRepository;
import mx.tutouami.repository.AccountRepository;
import mx.tutouami.repository.AsesoriaRepository;
import mx.tutouami.repository.CarreraRepository;
import mx.tutouami.repository.GrupoRepository;
import mx.tutouami.repository.MateriaRepository;

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
	
	@Autowired
	AccountRepository accountRepository;
	
	public static void main(String[] args) {
		
		SpringApplicationBuilder builder = new SpringApplicationBuilder(ProyectoApplication.class);

		builder.headless(false);

		builder.run(args);
	}

	@PostConstruct
	public void inicia() {
		inicializaBD();
	}
	
	public void inicializaBD() {
		
		Degree carreraComputacion = new Degree();
		carreraComputacion.setNombre("Computación");
		carreraRepository.save(carreraComputacion);
		
		Degree carreraElectronica = new Degree();
		carreraElectronica.setNombre("Electronica");
		carreraRepository.save(carreraElectronica);
		
		Subject materiaCompiladores = new Subject();
		materiaCompiladores.setNombre("Compiladores");
		
		Subject materiaIngenieria = new Subject();
		materiaIngenieria.setNombre("Ingenieria de software");
		
		Subject materiaSistemas = new Subject();
		materiaSistemas.setNombre("Sistemas Operativos");
		
		materiaRepository.save(materiaCompiladores);
		materiaRepository.save(materiaIngenieria);
		materiaRepository.save(materiaSistemas);
		
		Account account = accountRepository.save(Account.builder()
				.email("manuel@gmail.com")
				.password("12345")
				.build());
		
		Student student = studentRepository.save(Student.builder()
				.name("Victor")
				.lastName("Sosa")
				.secondLastName("Pina")
				.age(21)
				.phone("123456789")
				.cv("CV DESCRIPTION")
				.trimester(9)
				.score(7.5F)
				.totalScore(115)
				.description("DESCRIPTION")
				.status("LIBRE")
				.degree(carreraElectronica)
				.account(account)
				.build());
		
		carreraElectronica.addAlumno(student);
		carreraRepository.save(carreraElectronica);
	}
}
