package br.com.leonardo.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leonardo.cursomc.domain.Categoria;
import br.com.leonardo.cursomc.repositories.CategoriaRepository;

@Service // A defini como serviço
public class CategoriaService {
	
	@Autowired // Essa anotação faz o atributo abaixo se auto iniciar automaticamente pelo Spring
	private CategoriaRepository repo; //Aqui temos qeu declarar uma depencia do CategoriaRepository
	
	public Categoria buscar(Integer id) {
		Categoria obj = repo.findOne(id); // Esse objeto criado ja utiliza os metodos criados na Classe JpaRepository que é implementada na CategoriaRepository
		return obj;
	}

}
