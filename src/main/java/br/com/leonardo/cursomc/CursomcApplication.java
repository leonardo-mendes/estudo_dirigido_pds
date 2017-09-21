package br.com.leonardo.cursomc;

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
import br.com.leonardo.cursomc.domain.Produto;
import br.com.leonardo.cursomc.domain.enums.TipoPessoa;
import br.com.leonardo.cursomc.repositories.CategoriaRepository;
import br.com.leonardo.cursomc.repositories.CidadeRepository;
import br.com.leonardo.cursomc.repositories.ClienteRepository;
import br.com.leonardo.cursomc.repositories.EnderecoRepository;
import br.com.leonardo.cursomc.repositories.EstadoRepository;
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
		
	}
}
