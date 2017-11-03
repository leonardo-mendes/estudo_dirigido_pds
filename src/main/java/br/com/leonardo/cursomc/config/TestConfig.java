package br.com.leonardo.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.leonardo.cursomc.services.DBService;
import br.com.leonardo.cursomc.services.EmailService;
import br.com.leonardo.cursomc.services.MockEmailService;

@Configuration
@Profile("test") // Com essa notação eu falo que todos os bins presentes aqui estão ativos quando no meu application.proprieis estiver com o test active
public class TestConfig {
	
	@Autowired
	private DBService dbtest;
	
	@Bean // Assim nos iremos start todo o servico do banco
	public Boolean instantiateDataBase() throws ParseException {
		dbtest.instantiateTestDataBase();		
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
