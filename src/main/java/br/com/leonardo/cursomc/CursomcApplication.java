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

	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception { // Metodo implementado para fazer o save de dados no banco ao iniciar
			
	}
}
