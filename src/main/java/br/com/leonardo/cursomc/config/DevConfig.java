package br.com.leonardo.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.leonardo.cursomc.services.DBService;
import br.com.leonardo.cursomc.services.EmailService;
import br.com.leonardo.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev") // Com essa notação eu falo que todos os bins presentes aqui estão ativos quando no meu application.proprieis estiver com o test active
public class DevConfig {
	
	@Autowired
	private DBService dbtest;
	
	@Value("${spring.jpa.hibernate.ddl-auto}") // Essa notação eu consigo  validar se no application.proprie esta marcado para criar o banco novamente, somente assim poderemos iniciar o servico de preencer o banco
	private String strategy;
	
	@Bean // Assim nos iremos start todo o servico do banco
	public Boolean instantiateDataBase() throws ParseException {
		
		if(!"create".equalsIgnoreCase(strategy)) {
			return false;
		}
		
		dbtest.instantiateTestDataBase();		
		return true;
	}
	
	// Esse Bean serve para avisar a aplicação que quando ela necessitar de um EmailService ela vai retornar um SmtpEmailService
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
