package br.ufg.inf.backend.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufg.inf.backend.spring.model.Categoria;
import br.ufg.inf.backend.spring.model.Produto;
import br.ufg.inf.backend.spring.model.Tag;
import br.ufg.inf.backend.spring.service.CategoriaService;
import br.ufg.inf.backend.spring.service.ProdutoService;
import br.ufg.inf.backend.spring.service.TagService;

@Controller
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private TagService tagService;

    @GetMapping("/produtos")
    public String listarProdutos(Model model, @RequestParam(required = false) String sucesso, @RequestParam(required = false) String erro) {
        model.addAttribute("produtos", produtoService.listarProdutos());
        model.addAttribute("sucesso", sucesso);
        model.addAttribute("erro", erro);
        return "produtos";
    }
    
    @GetMapping("/produtos/adicionar")
    public String mostrarFormularioAdicionarProduto() {
        return "adicionar-produto";
    }
    
    @PostMapping("/produtos")
    public String adicionarProduto(@RequestParam String nome, @RequestParam double preco, @RequestParam Long categoriaId, @RequestParam List<Long> tagsId, RedirectAttributes redirectAttributes) {
    	Categoria categoria = categoriaService.obterCategoria(categoriaId);
    	List<Tag> tags =new ArrayList<Tag>();
        for (Long tagId : tagsId) {
            tags.add(tagService.obterTag(tagId));
        }    	
        if (categoria == null) {
            redirectAttributes.addAttribute("erro", "Categoria inválida!");
            return "redirect:/produtos";
        }
        if (tags.isEmpty()) {
            redirectAttributes.addAttribute("erro", "Tags inválidas!");
            return "redirect:/produtos";
        }
    	Produto produto = new Produto(null,nome,preco,categoria,tags);
        produtoService.salvarProduto(produto);
        redirectAttributes.addAttribute("sucesso", "Produto adicionado com sucesso!");
        return "redirect:/produtos";
    }

    @GetMapping("/produtos/editar")
    public String mostrarFormularioEditarProduto(@RequestParam("id") Long id, Model model) {
        Produto produto = produtoService.obterProduto(id);
        Categoria categoria = produto.getCategoria();
        List<Tag> tags = produto.getTags();
        model.addAttribute("produto", produto);

        return "editar-produto";
    }
    @PostMapping("/produtos/editar")
    public String editarProduto(@RequestParam("id") Long id, @RequestParam("nome") String nome,
                                @RequestParam("preco") double preco, @RequestParam("categoriaId") Long categoriaId, @RequestParam List<Long> tagsId, RedirectAttributes redirectAttributes) {
        Produto produto = produtoService.obterProduto(id);
        Categoria categoria= categoriaService.obterCategoria(categoriaId);
        List<Tag> tags =new ArrayList<Tag>();
        for (Long tagId : tagsId) {
            tags.add(tagService.obterTag(tagId));
        }
        
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setCategoria(categoria);
        produto.setTags(tags);
        produtoService.salvarProduto(produto);
        redirectAttributes.addAttribute("sucesso", "Produto atualizado com sucesso!");
        return "redirect:/produtos";
    }
    @PostMapping("/produtos/deletar")
    public String deletarProduto(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) { 
        try {
			produtoService.deletarProduto(id);
			redirectAttributes.addAttribute("sucesso", "Produto deletado com sucesso!");
		} catch (Exception e) {
            redirectAttributes.addAttribute("erro", "Ocorreu um erro ao deletar o produto.");
		}
        return "redirect:/produtos";
    }
}