package br.ufg.inf.backend.spring.service;

import br.ufg.inf.backend.spring.model.Categoria;
import br.ufg.inf.backend.spring.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria obterCategoria(Long id) {
		return categoriaRepository.findById(id).orElse(null);
	}
	
	public List<Categoria> listarCategorias() {
		return categoriaRepository.findAll();
	}
	public Categoria salvarCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public void deletarCategoria(Long id) {
		categoriaRepository.deleteById(id);
	}
}
