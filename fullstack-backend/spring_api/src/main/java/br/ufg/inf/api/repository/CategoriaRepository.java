package br.ufg.inf.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufg.inf.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}