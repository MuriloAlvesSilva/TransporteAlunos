package br.com.transporte.TransporteAlunos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.transporte.TransporteAlunos.models.Aluno;
import br.com.transporte.TransporteAlunos.models.Habilidade;
import br.com.transporte.TransporteAlunos.repositorys.AlunosRepository;

@Controller
public class HabilidadeController {
	
	@Autowired
	private AlunosRepository repository;
	
	@GetMapping("/habilidade/cadastrar/{id}")
	public String cadastrar(@PathVariable String id, Model model) {
		Aluno aluno = repository.obterAlunoPor(id);
		model.addAttribute("aluno", aluno);
		model.addAttribute("habilidade", new Habilidade());
		
		return "habilidade/cadastrar";
	}
	
	@PostMapping("/habilidade/salvar/{id}")
	public String salvar(@PathVariable String id, @ModelAttribute Habilidade habilidade) {
		Aluno aluno = repository.obterAlunoPor(id);
		repository.salvar(aluno.adicionar(aluno, habilidade));
		
		
		return "redirect:/aluno/listar";
	}
}
