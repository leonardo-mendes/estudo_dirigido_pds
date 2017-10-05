package br.com.leonardo.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.leonardo.cursomc.domain.Cliente;
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
	
}
