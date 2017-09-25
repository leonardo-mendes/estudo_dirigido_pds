package br.com.leonardo.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.leonardo.cursomc.domain.Categoria;
import br.com.leonardo.cursomc.domain.Cidade;
import br.com.leonardo.cursomc.domain.Cliente;
import br.com.leonardo.cursomc.domain.Endereco;
import br.com.leonardo.cursomc.domain.Estado;
import br.com.leonardo.cursomc.domain.ItemPedido;
import br.com.leonardo.cursomc.domain.Pagamento;
import br.com.leonardo.cursomc.domain.PagamentoBoleto;
import br.com.leonardo.cursomc.domain.PagamentoCartao;
import br.com.leonardo.cursomc.domain.Pedido;
import br.com.leonardo.cursomc.domain.Produto;
import br.com.leonardo.cursomc.domain.enums.EstadoPagamento;
import br.com.leonardo.cursomc.domain.enums.TipoPessoa;
import br.com.leonardo.cursomc.repositories.CategoriaRepository;
import br.com.leonardo.cursomc.repositories.CidadeRepository;
import br.com.leonardo.cursomc.repositories.ClienteRepository;
import br.com.leonardo.cursomc.repositories.EnderecoRepository;
import br.com.leonardo.cursomc.repositories.EstadoRepository;
import br.com.leonardo.cursomc.repositories.ItemPedidoRepository;
import br.com.leonardo.cursomc.repositories.PagamentoRepository;
import br.com.leonardo.cursomc.repositories.PedidoRepository;
import br.com.leonardo.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication  implements CommandLineRunner{ // A gente implementa essa interface para quando a aplicação iniciar ela ja instanciar um metodo para fazer o primeiro insert no banco para teste.
	
	@Autowired // Ja utilizamos anterior
	private CategoriaRepository categoriarepository; 
	@Autowired
	private ProdutoRepository produtorepository;
	@Autowired
	private CidadeRepository cidaderepository;
	@Autowired
	private EstadoRepository estadorepository;
	@Autowired
	private ClienteRepository clienterepository;
	@Autowired
	private EnderecoRepository enderecorepository;
	@Autowired
	private PedidoRepository pedidorepository;
	@Autowired
	private PagamentoRepository pagamentorepository;
	@Autowired
	private ItemPedidoRepository itempedidorepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception { // Metodo implementado para fazer o save de dados no banco ao iniciar
		Categoria cat1 = new Categoria(null,"Informatica");
		Categoria cat2 = new Categoria(null,"Escritorio");
		
		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat1.getProdutos().addAll(Arrays.asList(p2));	
		
		p1.getCategorias().addAll(Arrays.asList(cat1));	
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));	
		p3.getCategorias().addAll(Arrays.asList(cat1));	
		
		categoriarepository.save(Arrays.asList(cat1,cat2)); // Como criamos o objeto do repository aqui vamos utilizar um de seus metodos ja herdados da Jpa
		// Dentro do save utilizamos o Arrays.asList(cat1,cat2) que é um macete para salvar masi de um objeto ja no dB
		
		produtorepository.save(Arrays.asList(p1,p2,p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlandia",est1);
		Cidade c2 = new Cidade(null,"Sao Paulo",est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));		
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadorepository.save(Arrays.asList(est1,est2));
		cidaderepository.save(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@hotmail.com", "1234567", TipoPessoa.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("123456789","987654321"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Jardim", "123465789", "Apto03", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Rosas", "500", "Jardim 2", "987654321", "Fundos", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienterepository.save(Arrays.asList(cli1));
		enderecorepository.save(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm"); // Classe que ira formatar a data
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 14:45"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoCartao(null, EstadoPagamento.PAGO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("10/11/2017 14:45"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidorepository.save(Arrays.asList(ped1,ped2));
		pagamentorepository.save(Arrays.asList(pagto1,pagto2));
		
		ItemPedido ip1 = new ItemPedido(p1, ped1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(p3, ped1, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(p2, ped2, 100.00, 2, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itempedidorepository.save(Arrays.asList(ip1,ip2,ip3));
	
	}
}
