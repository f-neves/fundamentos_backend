package br.ufg.inf.backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufg.inf.backend.spring.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}