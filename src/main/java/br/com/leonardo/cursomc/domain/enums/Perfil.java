package br.com.leonardo.cursomc.domain.enums;

public enum Perfil {
	
	ADMIN (1, "ROLE_ADMIN"), // ROLE_ADMIN é uma exigencia do Spring Security
	CLIENTE (2, "ROLE_CLIENTE");
	
	private Integer id;
	private String descricao;
	
	
	private Perfil(Integer id, String descricao) { //Enums sempre é privte
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
	public static Perfil toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Perfil x: Perfil.values()) { //Irar busca em todos os valores da classe o cod
			if(cod.equals(x.getId())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: "+cod); // excessao ja existente 
	}

}
