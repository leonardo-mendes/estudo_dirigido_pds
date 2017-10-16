package br.com.leonardo.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.leonardo.cursomc.domain.Produto;
import br.com.leonardo.cursomc.dto.ProdutoDTO;
import br.com.leonardo.cursomc.resources.utils.URL;
import br.com.leonardo.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos") // Esse endpoint ficou definido que vai ser o algumacoisa/categoria
public class ProdutoResource{
	
	@Autowired // Ja vimos a sua função no ProdutoService
	private ProdutoService service;
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) //Esse segundo endpoint ficou definido que vai ser o algumacoisa/categoria/id para o get
	public ResponseEntity<Produto> find(@PathVariable Integer id) { //Para que o Spring entenda que o id da URL vai ser o do parametro usamos a anotação @PathVariable
	//	Utilizamos o ResponseEntity<?> pois ele ja tem alguns encapsulamentos importante para o Spring e utilizamos o ? porue não sabemos se vai retornar o resultado
		
		Produto obj = service.find(id); // Aqui so estamos chamando uma função que ja declaramos no Service da classe
		
		return ResponseEntity.ok().body(obj);
		// Aqui estamos falando que o retorno vai ser .ok() e vai trazer o body(Com o objeto dentro);
	}
	
		
	// Buscar todas Produtos com quatidades por paginas
	@RequestMapping(method=RequestMethod.GET) // Esse endpoint não precisa de ID ja que chamaremos todas produtos
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			@RequestParam(value="linesPerPage", defaultValue="ASC")String direction) 
	{ 
		
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		
		// Na requisição as categorias irão vir em 1,2,3,4
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		
		// Dessa maneira abaixo vamos percorrer a lista completa (stream) efetuando a operacao pra cada elemento da lista (map) e para cada objeto da lista
		// eu estou criando o alias obj que ira realizar uma funcao anonima (->) que ira criar um CategoriaDTO com o obj como parametro
		// apos isso temos que converter o stream para lista novamente entao utilizamos o collect(Collectors.toList())
		Page <ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
		// Aqui estamos falando que o retorno vai ser 	.ok() e vai trazer o body(Com a lista dentro);
	}
	
}
