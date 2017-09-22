package br.com.leonardo.cursomc.domain.enums;

public enum EstadoPagamento {
	
	PENDENTE (1, "Pendente"), // (Valor do ID, "Descrição")
	PAGO (2, "Pago"),
	CANCELADO (3, "Cancelado");
	
	private Integer id;
	private String descricao;
	
	
	private EstadoPagamento(Integer id, String descricao) { //Enums sempre é privte
		this.id = id;
		this.descricao = descricao;
	}

	// Enums sempre teremos so get pois nunca iremos alterar o valor
	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	// Tem que ser static porque sempre iremos procurar valores e não instanciamos valores novos
	public static EstadoPagamento toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(EstadoPagamento x: EstadoPagamento.values()) { //Irar busca em todos os valores da classe o cod
			if(cod.equals(x.getId())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: "+cod); // excessao ja existente 
	}

}
