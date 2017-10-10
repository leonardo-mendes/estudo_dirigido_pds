package br.com.leonardo.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.leonardo.cursomc.domain.Categoria;

// Observacao: A camada DTO vai ser responsavel por fazer validacoes que estao presentes em notacoes, ou seja
// sao validacoes mais simples, pois quando e uma validacao mais robusta deve ser feito no package service
// e a validacao do objeto DTO sera feito no REST.

public class CategoriaDTO implements Serializable{ // Utilizamos o Serializable para conseguir utilizar a classe em trafego de dados.
	private static final long serialVersionUID = 1L; // Sempre é criado quando implementamos a Serializable
	
	// Essa classe é criada para somente para ser um objeto que define os dados que trafegaremos quando necessitarmos utilizar o Categoria

	// Dessa maneira so iremos utilizar essas informações no trafego de dados da categoria
	private Integer id;
	
	@NotEmpty(message="Preencimento Obrigatorio") // Valida se esta vazio
	@Length(min=5, max=80, message="O tamanho deve ser de 5 a 80 caracteres") // Validacao do tamanho do campo
	private String nome;
	
	
	public CategoriaDTO() {
	}
	
	// Fazemos isso para o categoria DTO utilizar os dados do Categoria
	public CategoriaDTO(Categoria obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();				
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
	
	
}
