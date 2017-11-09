package br.com.leonardo.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.leonardo.cursomc.domain.Cliente;
import br.com.leonardo.cursomc.repositories.ClienteRepository;
import br.com.leonardo.cursomc.security.UserSS;

@Service
// Essa classe serve somente para buscar qual o usuário esta utilizando o projeto
public class UserDetailService implements UserDetailsService{

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = repo.findByEmail(email);
		
		if(cliente == null) {
			throw new UsernameNotFoundException(email); // Essa exceção é exclusiva do Security
		}

		return new UserSS(cliente.getId(),cliente.getSenha(),cliente.getEmail(),cliente.getPerfis());
	}

}
