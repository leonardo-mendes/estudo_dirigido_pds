package br.com.leonardo.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.leonardo.cursomc.domain.Categoria;
import br.com.leonardo.cursomc.domain.Produto;
import br.com.leonardo.cursomc.repositories.CategoriaRepository;
import br.com.leonardo.cursomc.repositories.ProdutoRepository;
import br.com.leonardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service // A defini como serviço
public class ProdutoService {
	
	@Autowired // Essa anotação faz o atributo abaixo se auto iniciar automaticamente pelo Spring
	private ProdutoRepository repo; //Aqui temos qeu declarar uma depencia do ProdutoRepository
	
	@Autowired 
	private CategoriaRepository categoriarepo;
	
	public Produto find(Integer id) {
		Produto obj = repo.findOne(id); // Esse objeto criado ja utiliza os metodos criados na Classe JpaRepository que é implementada na ProdutoRepository
		// Esse metodo findOne quando não encontra o objeto retorna nulo
		
		if(obj==null) { // Quando criamos essa excessao o REST vai ter qeu capturar ela
			throw new ObjectNotFoundException("Objeto não encontrado: Id - "+id);			
		}
		
		return obj;
	}
	
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		// Vai trazer todas as categorias que estão presentes na LIST de ID
		List<Categoria> categorias = categoriarepo.findAll(ids);		
		
		return repo.search(nome, categorias, pageRequest);
		
	}
	
	

}
