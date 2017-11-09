package br.com.leonardo.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.leonardo.cursomc.dto.EmailDTO;
import br.com.leonardo.cursomc.security.JWTUtil;
import br.com.leonardo.cursomc.security.UserSS;
import br.com.leonardo.cursomc.services.AuthService;
import br.com.leonardo.cursomc.services.UserService;

@RestController
@RequestMapping(value = "/auth") // Esse endpoint ficou definido que irá gerar novos tokens quando for necessário
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService auth;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated(); // recupera o usuário
		String token = jwtUtil.generateToken(user.getUsername()); // geramos um novo token
		response.addHeader("Authorization", "Bearer " + token); //lançamos o token na requisição
		return ResponseEntity.noContent().build();
	}
	
	// Método abaixo irá enviar o email para o usuário
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		auth.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
	
}
