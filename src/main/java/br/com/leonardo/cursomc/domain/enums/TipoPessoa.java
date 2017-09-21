package br.com.leonardo.cursomc.domain.enums;

public enum TipoPessoa {
	
	PESSOAFISICA (1, "Pessoa Fisica"), // (Valor do ID, "Descrição")
	PESSOAJURIDICA (2, "Pessoa Juridica");
	
	private Integer id;
	private String descricao;
	
	
	private TipoPessoa(Integer id, String descricao) { //Enums sempre é privte
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
	public static TipoPessoa toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(TipoPessoa x: TipoPessoa.values()) { //Irar busca em todos os valores da classe o cod
			if(cod.equals(x.getId())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: "+cod); // excessao ja existente 
	}
	

}
