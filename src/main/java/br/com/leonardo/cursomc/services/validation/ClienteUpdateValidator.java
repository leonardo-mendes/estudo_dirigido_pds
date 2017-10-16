package br.com.leonardo.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.leonardo.cursomc.domain.Cliente;
import br.com.leonardo.cursomc.dto.ClienteDTO;
import br.com.leonardo.cursomc.repositories.ClienteRepository;
import br.com.leonardo.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	// Como no Cliente DTO nos não utilizamos o ID precisamos pegar da URI (link) o Id do usuario
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteUpdate ann) {
		
	}
	
	@Override // Esse metodo que é construido para nos realizarmos qualquer validação personalizada, com isso a notação @valid vai utilizar essa regra.
	public boolean isValid(ClienteDTO obj, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		// FieldMessage nos ja criamos e ela trata os erros que vamos simular abaixo.
		List<FieldMessage> list = new ArrayList<>();
		
		// Validação para saber se o email ja existe cadastro no banco gerando uma mensagem masi clara para o Usuário, mas diferente do INSERT se for o mesmo usuario tem que deixar.
		Cliente aux = repo.findByEmail(obj.getEmail());
		if(aux != null && !obj.getId().equals(uriId)) {
			list.add(new FieldMessage("Email", "Email já existente."));
		}
		
		// Esse for abaixo não precisamos aprender, pois é modelo para o JPA o que ele faz? Ele pega cada posição da lista criada acima e adciona colocando a msg do erro e o tipo do erro que é tratado ResourceExceptionHandler
		for(FieldMessage e: list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getMessage()).addConstraintViolation();
		}
		return list.isEmpty();
		
	}
	
	
	
}
