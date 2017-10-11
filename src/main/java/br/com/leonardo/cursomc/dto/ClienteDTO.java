package br.com.leonardo.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.leonardo.cursomc.domain.Cliente;

public class ClienteDTO implements Serializable{ // Utilizamos o Serializable para conseguir utilizar a classe em trafego de dados.
	private static final long serialVersionUID = 1L; // Sempre é criado quando implementamos a Serializable
	
	private Integer id;
	
	@NotEmpty(message="Preencimento Obrigatorio") // Valida se esta vazio
	@Length(min=5, max=120, message="O tamanho deve ser de 5 a 120 caracteres") // Validacao do tamanho do campo
	private String nome;
	
	@NotEmpty (message="Preencimento Obrigatorio") // Valida se esta vazio
	@Email (message="Email inválido")
	private String email;
	
	public ClienteDTO() {
	}
	
	public ClienteDTO(Cliente obj) {
		this.id=obj.getId();
		this.nome=obj.getNome();
		this.email=obj.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
