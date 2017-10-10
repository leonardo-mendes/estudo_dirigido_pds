package br.com.leonardo.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

// Essa classe que ira tratar os erros gerando a mensagem mais clara para o usuario

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Integer status, String msg, Long timestamp) {
		super(status, msg, timestamp);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}
	
	
	/* Nesse caso não iremos precisar de um Set pois vamos inserir o erro um a um
	public void setList(List<FieldMessage> list) {
		this.list = list;
	}*/
	
	// Esse metodo então vai ter todos dados do StandarError mais os erros do FieldMessage
	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
	
	
}
