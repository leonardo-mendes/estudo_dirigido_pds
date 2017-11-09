package br.com.leonardo.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.leonardo.cursomc.domain.Pedido;
import br.com.leonardo.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos") // Esse endpoint ficou definido que vai ser o algumacoisa/categoria
public class PedidoResource{
	
	@Autowired // Ja vimos a sua função no PedidoService
	private PedidoService service;
	

	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) //Esse segundo endpoint ficou definido que vai ser o algumacoisa/categoria/id para o get
	public ResponseEntity<Pedido> find(@PathVariable Integer id) { //Para que o Spring entenda que o id da URL vai ser o do parametro usamos a anotação @PathVariable
	//	Utilizamos o ResponseEntity<?> pois ele ja tem alguns encapsulamentos importante para o Spring e utilizamos o ? porue não sabemos se vai retornar o resultado
		
		Pedido obj = service.find(id); // Aqui so estamos chamando uma função que ja declaramos no Service da classe
		
		return ResponseEntity.ok().body(obj);
		// Aqui estamos falando que o retorno vai ser .ok() e vai trazer o body(Com o objeto dentro);
	}
	
	
	// Inserir
	@RequestMapping(method=RequestMethod.POST)
	// Para que a validacao do DTO funcione temos que utilizar o @valid
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){ // O RequestBody faz o JSON ser convertido em um objeto JAVA
		// O codigo Http quando estamos inserindo um objeto no banco ele tem um codigo especifico que é 201
		// Sempre devemos  devolver o novo ID do objeto para a URI (endereço do novo objeto)

		obj = service.insert(obj);
		
		// Essa expressão abaixo é meio que um padrão do Java para devolver para o URI o valor do novo id
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		// Esse método abaixo ja faz toda a construção do build da URI
		return ResponseEntity.created(uri).build();
	}
	
	
	// Buscar todos os pedidos que pertence ao cliente
	@RequestMapping(method=RequestMethod.GET) // Esse endpoint não precisa de ID ja que chamaremos todas categorias
	public ResponseEntity<Page<Pedido>> findPage(
			// Essa expressao @RequestParam(value="page", defaultValue="0") define a estrutura como um parametro e o torna um parametro opcional, pois se nao for preencido trata 0
			@RequestParam(value="page", defaultValue="0") Integer page, 
			// Nesse parametro abaixo que definimos como quantos objetos por pagina iremos retornar
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="instante")String orderBy, 
			@RequestParam(value="linesPerPage", defaultValue="DESC")String direction) 
	{ 
		
		Page<Pedido> list = service.findPages(page, linesPerPage, orderBy, direction);
		
		return ResponseEntity.ok().body(list);
	}
	
}
