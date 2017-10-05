package br.com.leonardo.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity //Notação que faz essa classe ser criada no dB através do JPA Obs: Lembrar de criar a dependencia do mesmo no pom.xml
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id //Essa notação indica que o campo abaixo "id" vai ser uma chave primária
	@GeneratedValue(strategy=GenerationType.IDENTITY) //aqui definimos como vai funcionar a chave primária desta tabela
	private Integer id;
	private String nome;
	private Double preco;
	
	@JsonIgnore // Essa referencia fala que como ja colocamos a refrencia na outra classe essa so vai dizer que pertence a outra
	@ManyToMany // Essa notação fala sobre a associação que ele ira utilizar com a classe da lista abaixo
	@JoinTable(name="PRODUTO_CATEGORIA", // Vai ser o nome da Tabela
		joinColumns = @JoinColumn (name="produto_id"), // Aqui chamamos o join do produto com o nome da coluna no banco e abaixo a parte do categoria, ja que é uma relação * para *
		inverseJoinColumns = @JoinColumn (name="categoria_id")
    )
	private List<Categoria> categorias = new ArrayList<>();
	
	@JsonIgnore // Para a gente não é interessate saber qual produto esa no item de pedido e sim qual é o produto do item
	@OneToMany(mappedBy="id.produto") // A relação das classes primeiramene se da pelo id depois a classe associativa
	private Set<ItemPedido> itens = new HashSet<>(); // Utilizamos o Set para garantir que não vai ter item repetido
	
	public Produto(Integer id, String nome, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	public Produto() {

	}
	
	@JsonIgnore
	// Se não ignorarmos vamos ter um referencia ciclica
	public List<Pedido> getPedidos(){
		List<Pedido> lista  = new ArrayList<>();
		for (ItemPedido x : itens) {
			lista.add(x.getPedido());
		}
		return lista;
	}

		
	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	
	
	
}
