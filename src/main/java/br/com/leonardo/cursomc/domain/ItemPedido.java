package br.com.leonardo.cursomc.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore // Para não utilizamos como se fosse um chave simples, devido a isso não utilizamos @JsonReference e JsonBackReferenc
	@EmbeddedId // Mostra que é um ID composto
	private ItemPedidoPk id = new ItemPedidoPk(); // Esse id é um atributo composto
	// Com isso as associacos dos pedidos foram feitas na classe ItemPedidoPk
	
	private Double desconto;
	private Integer qtd;
	private Double preco;
	
	public ItemPedido() {
		super();
	}
	
	// No construtor da classe não faz sentido utilizamos o ItemPedidoPk, temos que utilizar as classs que fazem a associação
	// Então temos que utilizar um produto e um pedido como abaixo, setando pelos set.
	public ItemPedido(Produto produto, Pedido pedido, Double desconto, Integer qtd, Double preco) {
		super();
		this.id.setPedido(pedido);
		this.id.setProduto(produto);
		this.desconto = desconto;
		this.qtd = qtd;
		this.preco = preco;
	}
	
	
	public Double getSubtotal() {
		return (preco-desconto) * qtd;
	}	
	
	// Criamos o get do Pedido e Produto para ter acesso direto as classes fora da classe ItemPedidoPk. porem no Json emos que ignorar pois tudo que começa com get o JPA entende que tem que serializar.
	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	public Produto getProduto() {
		return id.getProduto();
	}
	
	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}
	
	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}

	public ItemPedidoPk getId() {
		return id;
	}

	public void setId(ItemPedidoPk id) {
		this.id = id;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
