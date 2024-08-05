package br.ufg.inf.backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufg.inf.backend.spring.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}