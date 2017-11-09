package br.com.leonardo.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.leonardo.cursomc.domain.Cliente;
import br.com.leonardo.cursomc.domain.ItemPedido;
import br.com.leonardo.cursomc.domain.PagamentoBoleto;
import br.com.leonardo.cursomc.domain.Pedido;
import br.com.leonardo.cursomc.domain.enums.EstadoPagamento;
import br.com.leonardo.cursomc.repositories.ClienteRepository;
import br.com.leonardo.cursomc.repositories.ItemPedidoRepository;
import br.com.leonardo.cursomc.repositories.PagamentoRepository;
import br.com.leonardo.cursomc.repositories.PedidoRepository;
import br.com.leonardo.cursomc.repositories.ProdutoRepository;
import br.com.leonardo.cursomc.security.UserSS;
import br.com.leonardo.cursomc.services.exceptions.AuthorizationException;
import br.com.leonardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service // A defini como serviço
public class PedidoService {
	
	@Autowired // Essa anotação faz o atributo abaixo se auto iniciar automaticamente pelo Spring
	private PedidoRepository repo; //Aqui temos qeu declarar uma depencia do PedidoRepository
	
	@Autowired // Essa anotação faz o atributo abaixo se auto iniciar automaticamente pelo Spring
	private BoletoService boletoService; //Aqui temos qeu declarar uma depencia do PedidoRepository
	
	@Autowired // Essa anotação faz o atributo abaixo se auto iniciar automaticamente pelo Spring
	private PagamentoRepository pagamentorepo; //Aqui temos qeu declarar uma depencia do PedidoRepository
	
	@Autowired // Essa anotação faz o atributo abaixo se auto iniciar automaticamente pelo Spring
	private ProdutoRepository produtorepo; //Aqui temos qeu declarar uma depencia do PedidoRepository
	
	@Autowired // Essa anotação faz o atributo abaixo se auto iniciar automaticamente pelo Spring
	private ItemPedidoRepository itempedidorepo; //Aqui temos qeu declarar uma depencia do PedidoRepository
	
	@Autowired // Essa anotação faz o atributo abaixo se auto iniciar automaticamente pelo Spring
	private ClienteRepository clienterepo; //Aqui temos qeu declarar uma depencia do PedidoRepository
	
	@Autowired // Essa anotação faz o atributo abaixo se auto iniciar automaticamente pelo Spring
	private EmailService emailservice; //Aqui temos qeu declarar uma depencia do PedidoRepository

	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id); // Esse objeto criado ja utiliza os metodos criados na Classe JpaRepository que é implementada na PedidoRepository
		// Esse metodo findOne quando não encontra o objeto retorna nulo
		
		if(obj==null) { // Quando criamos essa excessao o REST vai ter qeu capturar ela
			throw new ObjectNotFoundException("Objeto não encontrado: Id - "+id);			
		}
		
		return obj;
	}

	// Inserindo um Pedido
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date()); //Cria uma nova data do sistema no momento
		obj.setCliente(clienterepo.findOne(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto pagto = (PagamentoBoleto) obj.getPagamento();
			boletoService.preencherPagamentoBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentorepo.save(obj.getPagamento());
		
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtorepo.findOne(ip.getProduto().getId()));
			ip.setPreco(produtorepo.findOne(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		
		itempedidorepo.save(obj.getItens());
		
		// Aqui enviamos o email
		emailservice.sendOrderConfirmationEmail(obj);		
		
		return obj;
	}
	
	public Page<Pedido> findPages(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSS user = UserService.authenticated(); // Vou capturar o Id do cliente verifcando se ele esta autenticado.
		if(user == null) {
			throw new AuthorizationException("Acesso Negado");
		}
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienterepo.findOne(user.getId());
		return repo.findByCliente(cliente, pageRequest);		
	}
	
}
