package anver.tutouami.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import anver.tutouami.com.security.service.impl.AuthServiceImpl;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1") 
@Slf4j
public class InscriptionController {

	
	
	@Autowired
	private AuthServiceImpl servicioSeguridad;
	
	
}
