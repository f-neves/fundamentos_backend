package br.ufg.inf.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufg.inf.api.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}