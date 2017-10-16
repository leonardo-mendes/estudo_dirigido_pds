package br.com.leonardo.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.leonardo.cursomc.domain.Cliente;

@Repository //Essa notação faz a classe entender que ela ira trabalhar com umas classe de serviço, ou seja ira acessar o DB
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{ //O repository sempre sera uma interface que ira herdar da classe JpaRepository<Classe que trabalharemos, Tipo da Chave Primaria>

	// Uma coisa legal que o SPringData faz é conseguirmos criar metodos com pouco programação como abaixo:
	@Transactional(readOnly=true)
	public Cliente findByEmail(String email); // Se utilizarmos o findBy + nome do atributo na entidade conseguimos, ele ira retornar o que precisamos como Boolean

}
