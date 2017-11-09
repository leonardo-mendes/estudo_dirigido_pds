package br.com.leonardo.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.leonardo.cursomc.domain.Cliente;
import br.com.leonardo.cursomc.repositories.ClienteRepository;
import br.com.leonardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = repo.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(bcrypt.encode(newPass));
		
		repo.save(cliente);
		emailService.sendNewPasswordEmail(cliente,newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for(int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		Integer opt = random.nextInt(3);
		if(opt == 0) { // gera um digito
			return (char) (random.nextInt(10) + 48);
		}
		else if(opt == 1) { // gera letra maiuscula
			return (char) (random.nextInt(26) + 65);
		}
		else { //gera letra minuscula
			return (char) (random.nextInt(26) + 97);
		}
	}
	
}
