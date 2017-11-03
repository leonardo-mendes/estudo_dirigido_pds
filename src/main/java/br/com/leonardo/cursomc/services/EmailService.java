package br.com.leonardo.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.leonardo.cursomc.domain.Pedido;

// Iremos criar essa interface para facilitar o os metodos que serão implementados para o envio do email
public interface EmailService {
	
	void sendOrderConfirmationEmail (Pedido p);
	
	void sendEmail (SimpleMailMessage msg);

}
