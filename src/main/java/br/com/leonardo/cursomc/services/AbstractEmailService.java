package br.com.leonardo.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.leonardo.cursomc.domain.Pedido;


// Essa classe vai servir para desenvolver os metodos que envia o email.
public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}") // Aqui vamos recuperar o valor do application.proprieties
	private String sender;
	
	@Value("${default.recipient}") // Aqui vamos recuperar o valor do application.proprieties
	private String recipient;
	
	@Override
	public void sendOrderConfirmationEmail (Pedido obj) {
		SimpleMailMessage msg = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(msg);
	}

	// Vai ser protected para não ter muito acesso de outros packages
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(this.sender); // Remetente
		msg.setFrom(this.recipient); // Destinatário
		msg.setSubject("Pedido Confirmado! Código: "+obj.getId()); // Assunto
		msg.setSentDate(new Date(System.currentTimeMillis())); // Data
		msg.setText(obj.toString()); // Corpo do Email
		return msg;
	}
	
}
