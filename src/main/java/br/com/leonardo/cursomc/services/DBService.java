package br.com.leonardo.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
import br.com.leonardo.cursomc.domain.enums.Perfil;
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

@Service
public class DBService { // Essa classe é criada somente para instanciar o servico para testar o banco
	
	
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
	@Autowired
	private BCryptPasswordEncoder passwordenc;

	public void instantiateTestDataBase() throws ParseException {
		Categoria cat1 = new Categoria(null,"Informatica");
		Categoria cat2 = new Categoria(null,"Escritorio");
		Categoria cat3 = new Categoria(null,"Casa Mesa e Banho");
		Categoria cat4 = new Categoria(null,"Eletronicos");
		Categoria cat5 = new Categoria(null,"Jardinagem");
		Categoria cat6 = new Categoria(null,"Decoracao");
		Categoria cat7 = new Categoria(null,"Tabacaria");
		Categoria cat8 = new Categoria(null,"Perfumaria");
		
		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);
		Produto p4 = new Produto(null,"Mesa de Escritorio", 300.00);
		Produto p5 = new Produto(null,"Toalha", 50.00);
		Produto p6 = new Produto(null,"Colcha", 200.00);
		Produto p7 = new Produto(null,"TV True Color", 1500.00);
		Produto p8 = new Produto(null,"Roçadeira", 800.00);
		Produto p9 = new Produto(null,"Abajour", 100.00);
		Produto p10 = new Produto(null,"Pendente", 180.00);
		Produto p11 = new Produto(null,"Shampoo", 90.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
	
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		categoriarepository.save(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7,cat8)); // Como criamos o objeto do repository aqui vamos utilizar um de seus metodos ja herdados da Jpa
		// Dentro do save utilizamos o Arrays.asList(cat1,cat2) que é um macete para salvar masi de um objeto ja no dB
		
		produtorepository.save(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlandia",est1);
		Cidade c2 = new Cidade(null,"Sao Paulo",est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));		
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadorepository.save(Arrays.asList(est1,est2));
		cidaderepository.save(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "leonardo@webmendes.com", "53756673200", TipoPessoa.PESSOAFISICA, passwordenc.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("123456789","987654321"));
		
		Cliente cli2 = new Cliente(null, "Leonardo Mendes", "leonardocm92@hotmail.com", "67728188224", TipoPessoa.PESSOAFISICA, passwordenc.encode("456"));
		cli2.addPerfil(Perfil.ADMIN);
		cli2.getTelefones().addAll(Arrays.asList("123456789","987654321"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Jardim", "123465789", "Apto03", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Rosas", "500", "Jardim 2", "987654321", "Fundos", cli1, c2);
		Endereco e3 = new Endereco(null, "Rua Orquideas", "550", "Jardim 3", "987654321", "Fundos", cli2, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		
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
