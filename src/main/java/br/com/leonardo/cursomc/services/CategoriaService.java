package br.com.leonardo.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leonardo.cursomc.domain.Categoria;
import br.com.leonardo.cursomc.repositories.CategoriaRepository;
import br.com.leonardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service // A defini como serviço
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
	
	public Categoria update(Categoria obj) {
		find(obj.getId()); // Utilizamos o primeiro metodo implementado para garantir que teremos um objeto com o ID
		// Lembrando que esse metodo fiind() ja tem um tratamento de Exception
		return repo.save(obj);
	}

}
