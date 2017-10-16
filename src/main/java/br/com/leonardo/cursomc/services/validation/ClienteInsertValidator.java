package br.com.leonardo.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.leonardo.cursomc.domain.Cliente;
import br.com.leonardo.cursomc.domain.enums.TipoPessoa;
import br.com.leonardo.cursomc.dto.ClienteNewDTO;
import br.com.leonardo.cursomc.repositories.ClienteRepository;
import br.com.leonardo.cursomc.resources.exception.FieldMessage;
import br.com.leonardo.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteInsert ann) {
		
	}
	
	@Override // Esse metodo que é construido para nos realizarmos qualquer validação personalizada, com isso a notação @valid vai utilizar essa regra.
	public boolean isValid(ClienteNewDTO obj, ConstraintValidatorContext context) {
		
		// FieldMessage nos ja criamos e ela trata os erros que vamos simular abaixo.
		List<FieldMessage> list = new ArrayList<>();
		
		// Os dois IF's abaixo servem somente para validar o CPF/CNPJ e se ocorrer erro iremos adicionar na Lista.
		if(obj.getTipopessoa().equals(TipoPessoa.PESSOAFISICA.getId()) && !BR.isValidCPF(obj.getCpf())) {
			list.add(new FieldMessage("CPFouCNPJ", "CPF é inválido."));
		}
		
		if(obj.getTipopessoa().equals(TipoPessoa.PESSOAJURIDICA.getId()) && !BR.isValidCNPJ(obj.getCpf())) {
			list.add(new FieldMessage("CPFouCNPJ", "CNPJ é inválido."));
		}
		
		// Validação para saber se o email ja existe cadastro no banco gerando uma mensagem masi clara para o Usuário.
		Cliente aux = repo.findByEmail(obj.getEmail());
		if(aux != null) {
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
