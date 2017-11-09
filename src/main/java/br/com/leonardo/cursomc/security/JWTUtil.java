package br.com.leonardo.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	//Esse segredo e expirção abaixo foram definidos no application.proprieties, para ficar seguro e outra aplicação não consiguir reproduzir
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String generateToken(String username) {
		return Jwts.builder() // Esse builder é possivel por causa da dependencia do pom.xxml (io.jsonwebtoken) 
				.setSubject(username) // Essa parte é o usuário
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) // o tempo de expiração = tempo atual + tempos de expiração
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) // Essa parte eu falo qual o algoritmo que vai gerar a assinatura e depois o segredo do token (sempre temos que pegar os bytes)
				.compact(); // por fim é necessário so fazer a assinatura
	}
	
}
