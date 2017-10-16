package br.com.leonardo.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.leonardo.cursomc.services.validation.ClienteInsert;

// Essa classe é criada para fazer um controle, pois na inha regra de negocio o Cliente so pode ser cadastrado se:
// - Tiver um Endereço (Lembrando que por associação o Endereço exigi uma Cidade)
// - Tiver Um Telefone (Por opção o Cliente pode ter até 3 telefones)

@ClienteInsert
public class ClienteNewDTO implements Serializable{

	private static final long serialVersionUID = 1L;
		
	@NotEmpty(message="Preencimento Obrigatorio") // Valida se esta vazio
	@Length(min=5, max=80, message="O tamanho deve ser de 5 a 80 caracteres") // Validacao do tamanho do campo
	private String nome;
	
	@NotEmpty (message="Preencimento Obrigatorio") // Valida se esta vazio
	@Email (message="Email inválido")
	private String email;
	
	@NotEmpty(message="Preencimento Obrigatorio") // Valida se esta vazio
	private String cpf;
	
	private Integer tipopessoa;
	
	@NotEmpty(message="Preencimento Obrigatorio") // Valida se esta vazio
	private String logradouro;
	
	@NotEmpty(message="Preencimento Obrigatorio") // Valida se esta vazio
	private String numero;
	
	private String bairro;
	
	@NotEmpty(message="Preencimento Obrigatorio") // Valida se esta vazio
	private String cep;
	
	
	private String complemento;
	
	@NotEmpty(message="Preencimento Obrigatorio") // Valida se esta vazio
	private String telefone1;
	
	private String telefone2;
	
	private String telefone3;
	
	private Integer cidadeId;
	
	public ClienteNewDTO() {
		
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

	public Integer getTipopessoa() {
		return tipopessoa;
	}

	public void setTipopessoa(Integer tipopessoa) {
		this.tipopessoa = tipopessoa;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}
	
	

}
