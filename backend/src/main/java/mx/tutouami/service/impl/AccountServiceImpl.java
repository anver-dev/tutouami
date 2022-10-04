package mx.tutouami.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.tutouami.entity.Account;
import mx.tutouami.entity.RefreshToken;
import mx.tutouami.entity.Student;
import mx.tutouami.exceptions.UnauthorizedException;
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
				() -> new UnauthorizedException(String.format("Account with email: %s not found", email)));
		
		Student student = studentRepository.findByAccount(account).orElseThrow(
				() -> new UnauthorizedException(String.format("Account with email: %s not found", email)));

		String jsonWebToken = securityService.generateAccountToken(student);
		RefreshToken refreshJsonWebToken = securityService.generateAccountRefreshToken(student);
		
		if (jsonWebToken == null)
			throw new UnauthorizedException(String.format("Account with email: %s not found", email));

		return AccountDTO.generate(account, TokenDTO.builder().type(SecurityTypes.TOKEN.getValue()).value(jsonWebToken)
				.refreshToken(refreshJsonWebToken.getId().toString()).build());
	}
}
