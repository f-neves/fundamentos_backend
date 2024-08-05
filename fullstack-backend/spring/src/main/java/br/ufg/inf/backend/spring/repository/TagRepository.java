package br.ufg.inf.backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufg.inf.backend.spring.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
