package mx.tutouami.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.tutouami.entity.Account;
import mx.tutouami.entity.Student;
import mx.tutouami.exceptions.NotAuthorizedException;
import mx.tutouami.model.dto.AccountDTO;
import mx.tutouami.model.dto.TokenDTO;
import mx.tutouami.model.enums.SecurityTypes;
import mx.tutouami.repository.AccountRepository;
import mx.tutouami.repository.StudentRepository;
import mx.tutouami.service.IAccountService;

@Slf4j
@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private SecurityServiceImpl securityService;

	@Override
	public AccountDTO validateLogin(String email, String password) {
		log.info(String.format("Try access with email:: %s", email));

		Account account = accountRepository.findByEmailAndPassword(email, password).orElseThrow(
				() -> new NotAuthorizedException(String.format("Occount with email: %s not found", email)));
		log.info("ACCOUNT:: {}", account);
		Student student = studentRepository.findByAccount(account).orElseThrow(
				() -> new NotAuthorizedException(String.format("Occount with email: %s not found", email)));

		String jsonWebToken = securityService.generateAccountToken(student);
		String refreshJsonWebToken = securityService.generateAccountRefreshToken(email, password);

		if (jsonWebToken == null)
			throw new NotAuthorizedException(String.format("Occount with email: %s not found", email));

		return AccountDTO.generate(account, TokenDTO.builder().type(SecurityTypes.TOKEN.getValue()).value(jsonWebToken)
				.refreshToken(refreshJsonWebToken).build());
	}
}
