package br.com.leonardo.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.leonardo.cursomc.domain.enums.Perfil;
import br.com.leonardo.cursomc.domain.enums.TipoPessoa;

@Entity
public class Cliente implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	@Column(unique=true) // Isso garante que não teremos valores igual, ou seja dois emails iguais, porem quando usamos assim é lançado uma exceção muito estranha para o Usuario, por isso devemos tratar-la
	private String email;
	private String cpf;
	private Integer tipopessoa; // O tipo de pessoa vai ser armazenado como um inteiro mas vai se expor como Tipo Pessoa
	
	@OneToMany (mappedBy="cliente", cascade=CascadeType.ALL) // Essa notação cascade fala que toda ação que eu fizer no cliente pode influenciar o Endereço, isso ocorre pela associção 1 para muitos
	private List<Endereco> enderecos = new ArrayList<>();
	
	@ElementCollection // Situacao pontual para Collections
	@CollectionTable(name="TELEFONE")
	private Set<String> telefones = new HashSet<>();
	//Como telefone é uma classe fraca <<weak>> nos nem precisamos criar uma classe especifica para a mesma
	//Iremos criar uma coleção: isso é vai ser um array de telefones que não irão se repetir
	//O que garante a não repetição é o private Set
	
	@ElementCollection(fetch=FetchType.EAGER) // fetch=FetchType.EAGER utilizamos para garantir que ele sempre trará os perfis do Cliente
	@CollectionTable(name="Perfis")
	private Set<Integer> perfis = new HashSet<>();	
	
	@JsonIgnore
	@OneToMany (mappedBy="cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	@JsonIgnore // Para não mostrar a senha no JSON
	private String senha;

	public Cliente() {
		addPerfil(Perfil.CLIENTE); // Por regra de negócio todo cliente cadastrado terá o perfil de CLiente
	}
	
	public Cliente(Integer id, String nome, String email, String cpf, TipoPessoa tipopessoa, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		// Foi necessário fazer a validação de Ternalio para garantir o funcionamento do ClienteDTO
		this.tipopessoa = (tipopessoa == null) ? null: tipopessoa.getId(); // Como definimos como enum iremos chamar so o ID
		this.senha = senha;
		addPerfil(Perfil.CLIENTE); // Por regra de negócio todo cliente cadastrado terá o perfil de CLiente
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public TipoPessoa getTipopessoa() {
		return TipoPessoa.toEnum(tipopessoa); // Aqui utilizamos o metodo estatico
	}

	public void setTipopessoa(TipoPessoa tipopessoa) {
		this.tipopessoa = tipopessoa.getId();
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha=senha;
	}
	
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getId());
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	

}
