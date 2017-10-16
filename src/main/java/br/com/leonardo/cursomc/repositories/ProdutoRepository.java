package br.com.leonardo.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.leonardo.cursomc.domain.Categoria;
import br.com.leonardo.cursomc.domain.Produto;

@Repository //Essa notação faz a classe entender que ela ira trabalhar com umas classe de serviço, ou seja ira acessar o DB
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{ //O repository sempre sera uma interface que ira herdar da classe JpaRepository<Classe que trabalharemos, Tipo da Chave Primaria>

	// A anotação @Query deixa nos utilizarmos o Select abaixo preenchendo o metodo sem precisar implmentar passo a passo
	// os parametros :nomeparamentro são preenchidos através dos atributos do metodo com a anotação @Param("nome do campo na Query")
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj  INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias")List<Categoria> categorias, Pageable pageRequest);
	
	
	// O metodo acima fizemos com para praticar o JPQL mas poderiamos utilizar o metodo abaixo utilizando o padrão SpringDATA:
	// Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
	
}
