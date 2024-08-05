package br.ufg.inf.backend.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufg.inf.backend.spring.model.Tag;
import br.ufg.inf.backend.spring.repository.TagRepository;

@Service
public class TagService {
	@Autowired
	private TagRepository tagRepository;

	public Tag obterTag(Long id) {
		return tagRepository.findById(id).orElse(null);
	}
	
	public List<Tag> listarTags() {
		return tagRepository.findAll();
	}
	public Tag salvarTag(Tag tag) {
		return tagRepository.save(tag);
	}

	public void deletarTag(Long id) {
		tagRepository.deleteById(id);
	}

}
