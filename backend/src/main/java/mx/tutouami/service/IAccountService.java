package mx.tutouami.service;

import mx.tutouami.model.dto.AccountDTO;

public interface IAccountService {
	
	public AccountDTO validateLogin(String email, String password);
}
