package br.com.leonardo.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.leonardo.cursomc.domain.Categoria;
import br.com.leonardo.cursomc.dto.CategoriaDTO;
import br.com.leonardo.cursomc.repositories.CategoriaRepository;
import br.com.leonardo.cursomc.services.exceptions.DataIntegrityException;
import br.com.leonardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service // Essa notação define a classe como um Serviço
public class CategoriaService {
	
	@Autowired // Essa anotação faz o atributo abaixo se auto iniciar automaticamente pelo Spring
	private CategoriaRepository repo; //Aqui temos qeu declarar uma depencia do CategoriaRepository
	
	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id); // Esse objeto criado ja utiliza os metodos criados na Classe JpaRepository que é implementada na CategoriaRepository
		// Esse metodo findOne quando não encontra o objeto retorna nulo
		
		if(obj==null) { // Quando criamos essa excessao o REST vai ter qeu capturar ela
			throw new ObjectNotFoundException("Objeto não encontrado: Id - "+id);			
		}
		
		return obj;
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null); // Com isso garantimos que o objeto novo sempre vai ter o ID nulo.
		return repo.save(obj); 
	}
	
	// O método acima e abaixo são bem parecidos, pois o metodo save() faz o update quando acha o ID e Insert quando não acha
	
	// Esse metodo foi criado posterior ao update pois ele que realizara o update dos dados no dB
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
	
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData (newObj, obj);
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id); // Utilizamos o primeiro metodo implementado para garantir que teremos um objeto com o ID
		// Lembrando que esse metodo find() ja tem um tratamento de Exception
		
		try {
			repo.delete(id);
		}
		catch(DataIntegrityViolationException e) { // Como falamos na Classe CategoriaResource temos que lançar essa exceção
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos."); // Usamos uma Exception que criamos para tratar com msg personalizada.
		}
	}

	// Metodo que retorna todas as categorias
	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	// Classe Page ja e implmentada no SpringData, esse metodo vai chamar uma quatidade de categorias por pagina
	public Page<Categoria> findPages(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repo.findAll(pageRequest);
		
	}
	
	// Como alteramos o metodo POST do Resource para trazer um objeto DTO por causa das validacoes temos que fazer essa conversao abaixo
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}

}
