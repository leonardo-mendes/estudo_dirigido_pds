package br.com.leonardo.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leonardo.cursomc.domain.Pedido;
import br.com.leonardo.cursomc.repositories.PedidoRepository;
import br.com.leonardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service // A defini como serviço
public class PedidoService {
	
	@Autowired // Essa anotação faz o atributo abaixo se auto iniciar automaticamente pelo Spring
	private PedidoRepository repo; //Aqui temos qeu declarar uma depencia do PedidoRepository
	
	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id); // Esse objeto criado ja utiliza os metodos criados na Classe JpaRepository que é implementada na PedidoRepository
		// Esse metodo findOne quando não encontra o objeto retorna nulo
		
		if(obj==null) { // Quando criamos essa excessao o REST vai ter qeu capturar ela
			throw new ObjectNotFoundException("Objeto não encontrado: Id - "+id);			
		}
		
		return obj;
	}

}
