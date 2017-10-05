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
		
	// Buscar
	@RequestMapping(value="/{id}", method=RequestMethod.GET) //Esse segundo endpoint ficou definido que vai ser o algumacoisa/categoria/id para o get
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { //Para que o Spring entenda que o id da URL vai ser o do parametro usamos a anotação @PathVariable
	//	Utilizamos o ResponseEntity<?> pois ele ja tem alguns encapsulamentos importante para o Spring e utilizamos o ? porue não sabemos se vai retornar o resultado
		
		Categoria obj = service.find(id); // Aqui so estamos chamando uma função que ja declaramos no Service da classe
		
		return ResponseEntity.ok().body(obj);
		// Aqui estamos falando que o retorno vai ser .ok() e vai trazer o body(Com o objeto dentro);
	}
	
	// Inserir
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
	
	// Atualizar
	@RequestMapping(value="/{id}", method=RequestMethod.PUT) // Esse metodo vai ser uma mistura dos dois metodos acima
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
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
	
}
