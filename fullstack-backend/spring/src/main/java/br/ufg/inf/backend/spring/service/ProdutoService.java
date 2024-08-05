package br.ufg.inf.backend.spring.service;

import br.ufg.inf.backend.spring.model.Produto;
import br.ufg.inf.backend.spring.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public Produto obterProduto(Long id) {
		return produtoRepository.findById(id).orElse(null);
	}
	
	public List<Produto> listarProdutos() {
		return produtoRepository.findAll();
	}
	public Produto salvarProduto(Produto produto) {
		return produtoRepository.save(produto);
	}

	public void deletarProduto(Long id) {
		produtoRepository.deleteById(id);
	}
}
