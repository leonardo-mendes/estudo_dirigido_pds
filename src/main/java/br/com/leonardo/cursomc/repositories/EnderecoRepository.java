package br.com.leonardo.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.leonardo.cursomc.domain.Endereco;

@Repository //Essa notação faz a classe entender que ela ira trabalhar com umas classe de serviço, ou seja ira acessar o DB
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{ //O repository sempre sera uma interface que ira herdar da classe JpaRepository<Classe que trabalharemos, Tipo da Chave Primaria>


}
