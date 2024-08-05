package br.ufg.inf.backend.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import br.ufg.inf.backend.spring.model.Tag;
import br.ufg.inf.backend.spring.service.TagService;

@Controller
public class TagController {
	@Autowired
	private TagService tagService;

	@GetMapping("/tags")
	public String listarTags(Model model, @RequestParam(required = false) String sucesso, @RequestParam(required = false) String erro) {
		model.addAttribute("tags", tagService.listarTags());
		model.addAttribute("sucesso", sucesso);
		model.addAttribute("erro", erro);
		return "tags";
	}

	@GetMapping("/tags/adicionar")
	public String mostrarFormularioAdicionarTag() {
		return "adicionar-tag";
	}

	@PostMapping("/tags")
	public String adicionarTag(@RequestParam String nome, RedirectAttributes redirectAttributes) {
		Tag tag = new Tag();
		tag.setNome(nome);
		tagService.salvarTag(tag);
		redirectAttributes.addAttribute("sucesso", "Tag salva com sucesso!");
		return "redirect:/tags";
	}

	@GetMapping("/tags/editar")
	public String mostrarFormularioEditarTag(@RequestParam("id") Long id, Model model) {
		Tag tag = tagService.obterTag(id);
		model.addAttribute("tag", tag);
		return "editar-tag";
	}
	@PostMapping("/tags/editar")
	public String editarTag(@RequestParam("id") Long id, @RequestParam("nome") String nome, RedirectAttributes redirectAttributes) {
		Tag tag = tagService.obterTag(id);
		tag.setNome(nome);
		tagService.salvarTag(tag);
		redirectAttributes.addAttribute("sucesso", "Tag atualizado com sucesso!");
		return "redirect:/tags";
	}
	@PostMapping("/tags/deletar")
	public String deletarTag(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			tagService.deletarTag(id);
			redirectAttributes.addAttribute("sucesso", "Tag deletado com sucesso!");
		} catch (Exception e) {
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				redirectAttributes.addAttribute("erro", "Não é possível deletar esta tag pois ela está sendo usada em outros registros.");
			} else {
				redirectAttributes.addAttribute("erro", "Ocorreu um erro ao deletar a tag.");
			}
		}
		return "redirect:/tags";
	}
}