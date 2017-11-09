package br.com.leonardo.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.leonardo.cursomc.domain.Cidade;
import br.com.leonardo.cursomc.domain.Cliente;
import br.com.leonardo.cursomc.domain.Endereco;
import br.com.leonardo.cursomc.domain.enums.Perfil;
import br.com.leonardo.cursomc.domain.enums.TipoPessoa;
import br.com.leonardo.cursomc.dto.ClienteDTO;
import br.com.leonardo.cursomc.dto.ClienteNewDTO;
import br.com.leonardo.cursomc.repositories.CidadeRepository;
import br.com.leonardo.cursomc.repositories.ClienteRepository;
import br.com.leonardo.cursomc.repositories.EnderecoRepository;
import br.com.leonardo.cursomc.security.UserSS;
import br.com.leonardo.cursomc.services.exceptions.AuthorizationException;
import br.com.leonardo.cursomc.services.exceptions.DataIntegrityException;
import br.com.leonardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service // A defini como serviço
public class ClienteService {
	
	@Autowired // Essa anotação faz o atributo abaixo se auto iniciar automaticamente pelo Spring
	private ClienteRepository repo; //Aqui temos qeu declarar uma depencia do ClienteRepository
	
	@Autowired
	private BCryptPasswordEncoder passwordenc;
	
	private CidadeRepository cidaderepo;
	private EnderecoRepository enderecorepo;
	
	
	
	public Cliente find(Integer id) {
		// Vamos buscar o usuário logado
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Cliente obj = repo.findOne(id); // Esse objeto criado ja utiliza os metodos criados na Classe JpaRepository que é implementada na ClienteRepository
		// Esse metodo findOne quando não encontra o objeto retorna nulo
		
		if(obj==null) { // Quando criamos essa excessao o REST vai ter qeu capturar ela
			throw new ObjectNotFoundException("Objeto não encontrado: Id - "+id);			
		}
		
		return obj;
	}
	
	// Esse metodo foi criado posterior ao update pois ele que realizara o update dos dados no dB
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null); // Com isso garantimos que o objeto novo sempre vai ter o ID nulo.
		obj = repo.save(obj); 
		enderecorepo.save(obj.getEnderecos());
		return repo.save(obj); 
	}
	
	// Esse metodo é um pouco diferente de categoria pois  na classe cliente tem dados qu enão temos na clienteDTO, pois não é interessante la
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData (newObj, obj);
		return obj;
	}
	
	public void delete(Integer id) {
		find(id); // Utilizamos o primeiro metodo implementado para garantir que teremos um objeto com o ID
		// Lembrando que esse metodo find() ja tem um tratamento de Exception
		
		try {
			repo.delete(id);
		}
		catch(DataIntegrityViolationException e) { // Como falamos na Classe ClienteResource temos que lançar essa exceção
			throw new DataIntegrityException("Não é possivel excluir pois existe entidades relacionadas."); // Usamos uma Exception que criamos para tratar com msg personalizada.
		}
	}

	// Metodo que retorna todas as categorias
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	// Classe Page ja e implmentada no SpringData, esse metodo vai chamar uma quatidade de categorias por pagina
	public Page<Cliente> findPages(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repo.findAll(pageRequest);
		
	}
	
	// Como alteramos o metodo POST do Resource para trazer um objeto DTO por causa das validacoes temos que fazer essa conversao abaixo
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}
	
	// Como alteramos o metodo POST do Resource para trazer um objeto DTO por causa das validacoes temos que fazer essa conversao abaixo
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli1 = new Cliente(null, objDTO.getNome(),objDTO.getEmail(), objDTO.getCpf(), TipoPessoa.toEnum(objDTO.getTipopessoa()), passwordenc.encode(objDTO.getSenha()));
		Cidade cid = cidaderepo.findOne(objDTO.getCidadeId());
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getBairro(), objDTO.getCep(), objDTO.getComplemento(), cli1, cid);
		cli1.getEnderecos().add(end);
		cli1.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2()!=null) {
			cli1.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3()!=null) {
			cli1.getTelefones().add(objDTO.getTelefone3());
		}
		return cli1;
	}
	

}
