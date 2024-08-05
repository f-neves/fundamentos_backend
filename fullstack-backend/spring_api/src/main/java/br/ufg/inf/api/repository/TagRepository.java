package br.ufg.inf.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufg.inf.api.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
