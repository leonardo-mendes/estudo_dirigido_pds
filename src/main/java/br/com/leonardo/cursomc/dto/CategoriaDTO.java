package br.com.leonardo.cursomc.dto;

import java.io.Serializable;

import br.com.leonardo.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable{ // Utilizamos o Serializable para conseguir utilizar a classe em trafego de dados.
	private static final long serialVersionUID = 1L; // Sempre é criado quando implementamos a Serializable
	
	// Essa classe é criada para somente para ser um objeto que define os dados que trafegaremos quando necessitarmos utilizar o Categoria

	// Dessa maneira so iremos utilizar essas informações no trafego de dados da categoria
	private Integer id;
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
