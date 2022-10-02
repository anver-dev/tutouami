package mx.uam.ayd.proyecto.negocio;


import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.mockito.stubbing.Answer;

import mx.tutouami.entity.Group;
import mx.tutouami.entity.Account;
import mx.tutouami.repository.GrupoRepository;
import mx.tutouami.repository.AccountRepository;
import mx.tutouami.service.impl.AccountServiceImpl;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 
 * Pruebas unitarias del servicio
 * 
 * @author humbertocervantes
 *
 */
@ExtendWith(MockitoExtension.class)
class ServicioUsuarioTest {
	
	// Al usar la anotación @Mock, el framework Mockito crea un sustituto
	// de la clase que regresa valores por default
	@Mock
	private AccountRepository usuarioRepository;
	
	@Mock
	private GrupoRepository grupoRepository;
	
	// Esta anotación hace que se inyecten todos los Mocks al módulo que quiero
	// probar para que no haya nullPointerException por que las dependencias
	// no están satisfechas en tiempo de pruebas
	@InjectMocks
	private AccountServiceImpl servicio;

	
	@BeforeEach
	void setUp() throws Exception {
		// Este método se ejecuta antes de cada caso de prueba
	}

	@AfterEach
	void tearDown() throws Exception {
		// Este método se ejecuta después de cada caso de prueba
	}

	@Test
	void testRecuperaUsuarios() {

		
		// Prueba 1: corroborar que regresa una lista vacía si no hay usuarios en la "BD"
		
		// en este momento, la invocación a usuarioRepository.findAll() regresa una lista vacía
		//List <Usuario> usuarios = servicio.recuperaUsuarios();
		
		//assertTrue(usuarios.isEmpty());

		// Prueba 2: corroborar que regresa una lista con usuarios
		LinkedList <Account> lista = new LinkedList <> ();

		// Tengo que crear un Iterable <Usuario> para que el método 
		// usuarioRepository.findAll() no me regrese una lista vacía
		// cuando lo invoco
		Account usuario1 = new Account();

		Account usuario2 = new Account();
		
		lista.add(usuario1);
		lista.add(usuario2);
		
		// Al usar when, lo que hacemos es que definimos un comportamiento
		// para la invoación del método.
		// A partir de este punto, la invocación a usuarioRepository.findAll() ya
		// no me regresa una lista vacía, si no que me regresa una listaLigada
		// vista como Iterable que tiene dos elementos
		when(usuarioRepository.findAll()).thenReturn(lista);
		
		//usuarios = servicio.recuperaUsuarios();
		
		//assertEquals(2,usuarios.size()); // Corroboro que tenga dos elementos
		
		

	}

	@Test
	void testAgregaUsuario() {
		
		String nombre = "Juan";
		String apellido = "Perez";
		String idGrupo = "CK53";
		
		Group grupo = new Group();
		grupo.setNombre(idGrupo);
		

		
		when(grupoRepository.findByNombre(idGrupo)).thenReturn(grupo);
		
		when(usuarioRepository.save(any(Account.class))).thenAnswer(new Answer <Account> () {

			@Override
			public Account answer(InvocationOnMock invocation) throws Throwable {
				

				Account usuario = invocation.getArgument(0);
				
				//System.out.println("Guardando usuario: "+usuario);
				
				return usuario;
			}
			
		});
		
		
		//assertEquals(true,servicio.agregaUsuario(nombre,apellido,idGrupo));
	}
	
}
