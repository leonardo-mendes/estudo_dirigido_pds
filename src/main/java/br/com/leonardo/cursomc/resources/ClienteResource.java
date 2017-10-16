package br.com.leonardo.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import br.com.leonardo.cursomc.domain.Cliente;
import br.com.leonardo.cursomc.dto.ClienteDTO;
import br.com.leonardo.cursomc.dto.ClienteNewDTO;
import br.com.leonardo.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes") // Esse endpoint ficou definido que vai ser o algumacoisa/categoria
public class ClienteResource{
	
	@Autowired // Ja vimos a sua função no ClienteService
	private ClienteService service;
	

	@RequestMapping(value="/{id}", method=RequestMethod.GET) //Esse segundo endpoint ficou definido que vai ser o algumacoisa/categoria/id para o get
	public ResponseEntity<Cliente> find(@PathVariable Integer id) { //Para que o Spring entenda que o id da URL vai ser o do parametro usamos a anotação @PathVariable
	//	Utilizamos o ResponseEntity<?> pois ele ja tem alguns encapsulamentos importante para o Spring e utilizamos o ? porue não sabemos se vai retornar o resultado
		
		Cliente obj = service.find(id); // Aqui so estamos chamando uma função que ja declaramos no Service da classe
		
		return ResponseEntity.ok().body(obj);
		// Aqui estamos falando que o retorno vai ser .ok() e vai trazer o body(Com o objeto dentro);
	}
	
	
	// Inserir
	@RequestMapping(method=RequestMethod.POST)
	// Para que a validacao do DTO funcione temos que utilizar o @valid
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO){ // O RequestBody faz o JSON ser convertido em um objeto JAVA
		// O codigo Http quando estamos inserindo um objeto no banco ele tem um codigo especifico que é 201
		// Sempre devemos  devolver o novo ID do objeto para a URI (endereço do novo objeto)
		
		Cliente obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		
		// Essa expressão abaixo é meio que um padrão do Java para devolver para o URI o valor do novo id
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		// Esse método abaixo ja faz toda a construção do build da URI
		return ResponseEntity.created(uri).build();
	}

	// Atualizar
	@RequestMapping(value="/{id}", method=RequestMethod.PUT) // Esse metodo vai ser uma mistura dos dois metodos acima
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		
		Cliente obj = service.fromDTO(objDTO);
		obj.setId(id); // Com isso garantimos que o Objeto passado vai ser o atualizado.
		obj = service.update(obj);
		return ResponseEntity.noContent().build(); // Não precisa de retorno
	}
	
	// Deletar
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE) // Lembrando que em um DELETE sempre ira retornar o codigo 204 da Http
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		
		// OBS IMPORTANTE: Quando vamos implementar o Delete, temos que verificar se existe algum objeto linkado a ele no DB
		// Com isso temos poucas alternativas: ou continuamos o delete e apagamos todos objetos ou abortamos o delete
		// No caso do Exercicio vamos abortar o DELETE, senão tratarmos retorna a Exception DataIntegrityViolationException()
		
		// Nese metodo não precisamos utilizar um try e catch, pois vamos tratar no na Exeception ResourceExceptionHandler
		service.delete(id); // Metodo ja implemetado automaticamente.
		
		return ResponseEntity.noContent().build(); // Não precisa de retornoo build da URI
	}
	
	// Buscar todas Clientes
	@RequestMapping(method=RequestMethod.GET) // Esse endpoint não precisa de ID ja que chamaremos todas categorias
	public ResponseEntity<List<ClienteDTO>> findAll() { 
		
		List<Cliente> list = service.findAll();
		
		// Dessa maneira abaixo vamos percorrer a lista completa (stream) efetuando a operacao pra cada elemento da lista (map) e para cada objeto da lista
		// eu estou criando o alias obj que ira realizar uma funcao anonima (->) que ira criar um ClienteDTO com o obj como parametro
		// apos isso temos que converter o stream para lista novamente entao utilizamos o collect(Collectors.toList())
		List <ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
		// Aqui estamos falando que o retorno vai ser 	.ok() e vai trazer o body(Com a lista dentro);
	}
	
	// Buscar todas Clientes com quatidades por paginas
	@RequestMapping(value="/page", method=RequestMethod.GET) // Esse endpoint não precisa de ID ja que chamaremos todas categorias
	public ResponseEntity<Page> findPage(
			// Essa expressao @RequestParam(value="page", defaultValue="0") define a estrutura como um parametro e o torna um parametro opcional, pois se nao for preencido trata 0
			@RequestParam(value="page", defaultValue="0") Integer page, 
			// Nesse parametro abaixo que definimos como quantos objetos por pagina iremos retornar
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			@RequestParam(value="linesPerPage", defaultValue="ASC")String direction) 
	{ 
		
		Page<Cliente> list = service.findPages(page, linesPerPage, orderBy, direction);
		
		// Dessa maneira abaixo vamos percorrer a lista completa (stream) efetuando a operacao pra cada elemento da lista (map) e para cada objeto da lista
		// eu estou criando o alias obj que ira realizar uma funcao anonima (->) que ira criar um ClienteDTO com o obj como parametro
		// apos isso temos que converter o stream para lista novamente entao utilizamos o collect(Collectors.toList())
		Page <ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
		// Aqui estamos falando que o retorno vai ser 	.ok() e vai trazer o body(Com a lista dentro);
	}
	
}
