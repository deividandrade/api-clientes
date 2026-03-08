package com.springboot.api_clientes.controle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.api_clientes.modelos.Entrada;
import com.springboot.api_clientes.modelos.itemEntrada;
import com.springboot.api_clientes.modelos.Produto;
import com.springboot.api_clientes.repositorios.EntradaRepositorio;
import com.springboot.api_clientes.repositorios.FornecedorRepositorio;
import com.springboot.api_clientes.repositorios.FuncionarioRepositorio;
import com.springboot.api_clientes.repositorios.ProdutoRepositorio;
import com.springboot.api_clientes.repositorios.itemEntradaRepositorio;

@Controller
public class EntradaControle {

	@Autowired
	private EntradaRepositorio entradaRepositorio;

	@Autowired
	private itemEntradaRepositorio itemEntradaRepositorio;

	@Autowired
	private ProdutoRepositorio produtoRepositorio;

	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;

	@Autowired
	private FornecedorRepositorio fornecedorRepositorio;

	// lista temporária de itens
	private List<itemEntrada> listaItemEntrada = new ArrayList<>();

	// =============================
	// ABRIR TELA DE CADASTRO
	// =============================
	@GetMapping("/cadastroEntrada")
	public ModelAndView cadastrar(Entrada entrada, itemEntrada itemEntrada) {

		ModelAndView mv = new ModelAndView("administrativo/entradas/cadastro");

		if (entrada == null) {
			entrada = new Entrada();
		}

		if (itemEntrada == null) {
			itemEntrada = new itemEntrada();
		}

		mv.addObject("entrada", entrada);
		mv.addObject("itemEntrada", itemEntrada);
		mv.addObject("listaItemEntrada", listaItemEntrada);

		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		mv.addObject("listaFornecedores", fornecedorRepositorio.findAll());
		mv.addObject("listaProdutos", produtoRepositorio.findAll());

		return mv;
	}

	// =============================
	// ADICIONAR ITEM
	// =============================
	@PostMapping("/adicionarItem")
	public ModelAndView adicionarItem(Entrada entrada, itemEntrada itemEntrada) {

		if (itemEntrada.getProduto() != null && itemEntrada.getQuantidade() != null
				&& itemEntrada.getValorCusto() != null) {

			Produto produto = produtoRepositorio.findById(itemEntrada.getProduto().getId()).orElse(null);

			itemEntrada.setProduto(produto);

			listaItemEntrada.add(itemEntrada);
		}

		calcularTotais(entrada);

		return cadastrar(entrada, new itemEntrada());
	}

	// =============================
	// SALVAR ENTRADA
	// =============================
	@PostMapping("/salvarEntrada")
	public ModelAndView salvarEntrada(Entrada entrada, BindingResult result) {

		if (result.hasErrors()) {
			return cadastrar(entrada, new itemEntrada());
		}

		calcularTotais(entrada);

		Entrada entradaSalva = entradaRepositorio.saveAndFlush(entrada);

		for (itemEntrada item : listaItemEntrada) {

			item.setEntrada(entradaSalva);

			itemEntradaRepositorio.save(item);
		}

		listaItemEntrada.clear();

		return cadastrar(new Entrada(), new itemEntrada());
	}

	// =============================
	// CALCULAR TOTAIS
	// =============================
	private void calcularTotais(Entrada entrada) {

		Double quantidadeTotal = 0.0;
		Double valorTotal = 0.0;

		for (itemEntrada item : listaItemEntrada) {

			if (item.getQuantidade() != null && item.getValorCusto() != null) {

				quantidadeTotal += item.getQuantidade();
				valorTotal += item.getQuantidade() * item.getValorCusto();
			}
		}

		entrada.setQuantidadeTotal(quantidadeTotal);
		entrada.setValorTotal(valorTotal);
	}

	// =============================
	// LISTAR ENTRADAS
	// =============================
	@GetMapping("/listarEntrada")
	public ModelAndView listar() {

		ModelAndView mv = new ModelAndView("administrativo/entradas/lista");

		mv.addObject("listarEntradas", entradaRepositorio.findAll());

		return mv;
	}

}