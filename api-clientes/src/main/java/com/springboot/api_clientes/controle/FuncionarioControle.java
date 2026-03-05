package com.springboot.api_clientes.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.api_clientes.modelos.Funcionario;
import com.springboot.api_clientes.repositorios.FuncionarioRepositorio;
import com.springboot.api_clientes.repositorios.CidadeRepositorio;

@Controller
public class FuncionarioControle {

	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;

	@Autowired
	private CidadeRepositorio cidadeRepositorio;

	@GetMapping("/cadastroFuncionario")
	public ModelAndView cadastrar(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
		mv.addObject("funcionario", funcionario);
		mv.addObject("listaCidades", cidadeRepositorio.findAll());
		return mv;
	}

	@GetMapping("/listarFuncionario")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/funcionarios/lista");
		mv.addObject("listarFuncionarios", funcionarioRepositorio.findAll());
		return mv;
	}

	@GetMapping("/editarFuncionario/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);

		return cadastrar(funcionario.get());

	}

	@GetMapping("/removerFuncionario/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
		funcionarioRepositorio.delete(funcionario.get());

		return listar();

	}

	@PostMapping("/salvarFuncionario")
	public ModelAndView salvar(Funcionario funcionario, BindingResult result) {
		if (result.hasErrors()) {
			return cadastrar(funcionario);
		}
		funcionarioRepositorio.saveAndFlush(funcionario);
		return cadastrar(new Funcionario());
	}

}
