package br.com.leonardo.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.leonardo.cursomc.security.UserSS;

@Service
public class UserService {
	
	// Esse metodo vai retornar o usuario UserSS que esta logado no sistema
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception e){
			return null;
		}
	}

}
