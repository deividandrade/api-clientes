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

    // Lista temporária de itens para a entrada
    private List<itemEntrada> listaItemEntrada = new ArrayList<>();


    // Página de cadastro
    @GetMapping("/cadastroEntrada")
    public ModelAndView cadastrar(Entrada entrada, itemEntrada itemEntrada) {
        ModelAndView mv = new ModelAndView("administrativo/entradas/cadastro");

        mv.addObject("entrada", entrada == null ? new Entrada() : entrada);
        mv.addObject("itemEntrada", itemEntrada == null ? new itemEntrada() : itemEntrada);
        mv.addObject("listaItemEntrada", this.listaItemEntrada);
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaFornecedores", fornecedorRepositorio.findAll());
        mv.addObject("listaProdutos", produtoRepositorio.findAll());

        return mv;
    }

    // Adicionar item à lista
    @PostMapping("/adicionarItem")
    public ModelAndView adicionarItem(Entrada entrada, itemEntrada itemEntrada) {
        // Adiciona o item à lista temporária
        if (itemEntrada != null) {
            listaItemEntrada.add(itemEntrada);
        }
        return cadastrar(entrada, new itemEntrada());
    }

    // Salvar a entrada com todos os itens
    @PostMapping("/salvarEntrada")
    public ModelAndView salvarEntrada(Entrada entrada, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(entrada, new itemEntrada());
        }

        // Salva a entrada
        Entrada entradaSalva = entradaRepositorio.saveAndFlush(entrada);

        // Salva todos os itens vinculando à entrada
        for (itemEntrada item : listaItemEntrada) {
            item.setEntrada(entradaSalva);
            itemEntradaRepositorio.save(item);
        }

        // Limpa a lista para o próximo cadastro
        listaItemEntrada.clear();

        return cadastrar(new Entrada(), new itemEntrada());
    }

    // Lista todas as entradas
    @GetMapping("/listarEntrada")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/entradas/lista");
        mv.addObject("listarEntradas", entradaRepositorio.findAll());
        return mv;
    }
}