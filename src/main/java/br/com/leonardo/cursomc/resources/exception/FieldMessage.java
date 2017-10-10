package br.com.leonardo.cursomc.resources.exception;

import java.io.Serializable;

// Essa classe existe para tratarmos as mensagens de erro quando ocorrer algum erro de campo no @VALID
public class FieldMessage implements Serializable{ // Utilizamos o Serializable para conseguir utilizar a classe em trafego de dados.
	private static final long serialVersionUID = 1L; // Sempre é criado quando implementamos a Serializable
	
	private String fieldName;
	private String message;
		
	public FieldMessage() {
	}

	public FieldMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	
	
}
