package anver.tutouami.com;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import anver.tutouami.com.model.entity.Account;
import anver.tutouami.com.model.entity.Degree;
import anver.tutouami.com.model.entity.Student;
import anver.tutouami.com.model.entity.Subject;
import anver.tutouami.com.repository.AccountRepository;
import anver.tutouami.com.repository.AdviceRepository;
import anver.tutouami.com.repository.DegreeRepository;
import anver.tutouami.com.repository.GroupRepository;
import anver.tutouami.com.repository.StudentRepository;
import anver.tutouami.com.repository.SubjectRepository;

@SpringBootApplication
public class TutouamiApplication {
	
	@Autowired
	GroupRepository grupoRepository;
	
	@Autowired
	DegreeRepository carreraRepository;
	
	@Autowired
	AdviceRepository asesoriaRepository;
	
	@Autowired
	StudentRepository  studentRepository;
	
	@Autowired
	SubjectRepository materiaRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	public static void main(String[] args) {
		
		SpringApplicationBuilder builder = new SpringApplicationBuilder(TutouamiApplication.class);

		builder.headless(false);

		builder.run(args);
	}

	@PostConstruct
	public void inicia() {
		//inicializaBD();
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
