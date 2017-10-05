package br.com.leonardo.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.leonardo.cursomc.domain.Categoria;
import br.com.leonardo.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias") // Esse endpoint ficou definido que vai ser o algumacoisa/categoria
public class CategoriaResource{
	
	@Autowired // Ja vimos a sua função no CategoriaService
	private CategoriaService service;
	

	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) //Esse segundo endpoint ficou definido que vai ser o algumacoisa/categoria/id para o get
	public ResponseEntity<?> find(@PathVariable Integer id) { //Para que o Spring entenda que o id da URL vai ser o do parametro usamos a anotação @PathVariable
	//	Utilizamos o ResponseEntity<?> pois ele ja tem alguns encapsulamentos importante para o Spring e utilizamos o ? porue não sabemos se vai retornar o resultado
		
		Categoria obj = service.buscar(id); // Aqui so estamos chamando uma função que ja declaramos no Service da classe
		
		return ResponseEntity.ok().body(obj);
		// Aqui estamos falando que o retorno vai ser .ok() e vai trazer o body(Com o objeto dentro);
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){ // O RequestBody faz o JSON ser convertido em um objeto JAVA
		// O codigo Http quando estamos inserindo um objeto no banco ele tem um codigo especifico que é 201
		// Sempre devemos  devolver o novo ID do objeto para a URI (endereço do novo objeto).
		obj = service.insert(obj);
		
		// Essa expressão abaixo é meio que um padrão do Java para devolver para o URI o valor do novo id
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		// Esse método abaixo ja faz toda a construção do build da URI
		return ResponseEntity.created(uri).build();
	}
	
}
