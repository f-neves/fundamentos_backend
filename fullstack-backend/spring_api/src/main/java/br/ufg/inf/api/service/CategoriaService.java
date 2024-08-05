package br.ufg.inf.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufg.inf.api.model.Categoria;
import br.ufg.inf.api.repository.CategoriaRepository;

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
