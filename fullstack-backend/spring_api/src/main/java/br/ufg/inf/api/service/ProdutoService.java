package br.ufg.inf.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufg.inf.api.model.Produto;
import br.ufg.inf.api.repository.ProdutoRepository;

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
