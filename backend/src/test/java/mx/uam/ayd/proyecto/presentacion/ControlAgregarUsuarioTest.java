package mx.uam.ayd.proyecto.presentacion;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mx.tutouami.repository.GroupRepository;
import mx.tutouami.model.entity.Account;
import mx.tutouami.model.entity.Group;
import mx.tutouami.repository.AccountRepository;

/**
 * 
 * Prueba de integración
 * 
 * @author humbertocervantes
 *
 */
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ControlAgregarUsuarioTest {

	 // Este es el elemento que voy a probar
	
	// Los siguientes son únicamente para poder acceder a la BD
	
	@Autowired
	private GroupRepository grupoRepository;

	@Autowired
	private AccountRepository usuarioRepository;

	
	/**
	 * Este método solo se ejecuta una vez antes de todos los
	 * casos de prueba
	 * 
	 */
	@BeforeAll
	static public void prepare() {
			
		// Esto es necesario para evitar la excepción al arrancar la prueba
	    System.setProperty("java.awt.headless", "false");
	    	    
	    
	}
	
	@BeforeEach
	public void setUp() {
			
		// Creamos un grupo y lo agregamos a la BD
	    Group administradores = new Group();
	    administradores.setNombre("Prueba");
	    
	    grupoRepository.save(administradores);
	    
	    
	}
	

	@AfterEach
	public void tearDown() {
			
		// Limpiamos la BD
	    grupoRepository.deleteAll();
	    
	    
	}

	
	@Test
	public void testAgregaUsuario() { // agregaUsuario
		
		String nombre = "Juan";
		String apellido = "Perez";
		String grupo = "Prueba";
		

	}
}
