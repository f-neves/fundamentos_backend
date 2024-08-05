package br.ufg.inf.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufg.inf.api.exception.BadRequestException;
import br.ufg.inf.api.exception.ResourceNotFoundException;
import br.ufg.inf.api.model.Tag;
import br.ufg.inf.api.service.TagService;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> listarTags() {
        try {
            List<Tag> tags = tagService.listarTags();
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionarTag(@RequestBody Tag tag) {
        try {
            if (tag == null) {
                throw new BadRequestException("Tag não pode ser nula");
            }
            Tag novoTag = tagService.salvarTag(tag);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoTag);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar tag: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterTag(@PathVariable Long id) {
        try {
            Tag tag = tagService.obterTag(id);
            if (tag == null) {
                throw new ResourceNotFoundException("Tag não encontrada com o id: " + id);
            }
            return ResponseEntity.ok(tag);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter tag: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarTag(@PathVariable Long id, @RequestBody Tag tag) {
        try {
            if (tag == null) {
                throw new BadRequestException("Tag não pode ser nula");
            }
            if (!id.equals(tag.getId())) {
                throw new BadRequestException("ID na URL não corresponde ao ID no corpo da requisição");
            }
            Tag tagExistente = tagService.obterTag(id);
            if (tagExistente == null) {
                throw new ResourceNotFoundException("Tag não encontrada com o id: " + id);
            }
            Tag tagAtualizado = tagService.salvarTag(tag);
            return ResponseEntity.ok(tagAtualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar tag: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarTag(@PathVariable Long id) {
        try {
            Tag tagExistente = tagService.obterTag(id);
            if (tagExistente == null) {
                throw new ResourceNotFoundException("Tag não encontrada com o id: " + id);
            }
            tagService.deletarTag(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Não é possível excluir esta tag, pois ela está associada a um ou mais produtos.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar tag: " + e.getMessage());
        }
    }

}