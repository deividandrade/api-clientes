package com.springboot.api_clientes.controle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.api_clientes.modelos.Venda;
import com.springboot.api_clientes.modelos.ItemVenda;
import com.springboot.api_clientes.modelos.Produto;
import com.springboot.api_clientes.repositorios.VendaRepositorio;
import com.springboot.api_clientes.repositorios.ItemVendaRepositorio;
import com.springboot.api_clientes.repositorios.ClienteRepositorio;
import com.springboot.api_clientes.repositorios.FuncionarioRepositorio;
import com.springboot.api_clientes.repositorios.ProdutoRepositorio;

@Controller
@RequestMapping("/venda")  // Prefixo para evitar conflitos
public class VendaControle {

    @Autowired
    private VendaRepositorio vendaRepositorio;

    @Autowired
    private ItemVendaRepositorio itemVendaRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    // Lista temporária de itens antes de salvar a venda
    private List<ItemVenda> listaItemVenda = new ArrayList<>();

    // ABRIR TELA DE CADASTRO
    @GetMapping("/cadastro")
    public ModelAndView cadastrar(Venda venda, ItemVenda itemVenda) {
        ModelAndView mv = new ModelAndView("administrativo/vendas/cadastro");

        if (venda == null) {
            venda = new Venda();
        }
        if (itemVenda == null) {
            itemVenda = new ItemVenda();
        }

        mv.addObject("venda", venda);
        mv.addObject("itemVenda", itemVenda);
        mv.addObject("listaItemVenda", listaItemVenda);

        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaClientes", clienteRepositorio.findAll());
        mv.addObject("listaProdutos", produtoRepositorio.findAll());

        return mv;
    }

    // ADICIONAR ITEM NA LISTA TEMPORÁRIA
    @PostMapping("/adicionarItem")
    public ModelAndView adicionarItem(Venda venda, ItemVenda itemVenda) {
        if (itemVenda.getProduto() != null && itemVenda.getQuantidade() != null && itemVenda.getValor() != null) {
            Produto produto = produtoRepositorio.findById(itemVenda.getProduto().getId()).orElse(null);
            itemVenda.setProduto(produto);
            listaItemVenda.add(itemVenda);
        }
        calcularTotais(venda);
        return cadastrar(venda, new ItemVenda());
    }

    // SALVAR VENDA E ITENS
    @PostMapping("/salvarVenda")
    public ModelAndView salvarVenda(Venda venda, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(venda, new ItemVenda());
        }
        calcularTotais(venda);
        Venda vendaSalva = vendaRepositorio.saveAndFlush(venda);

        for (ItemVenda item : listaItemVenda) {
            item.setVenda(vendaSalva);
            itemVendaRepositorio.save(item);
        }
        listaItemVenda.clear();
        return cadastrar(new Venda(), new ItemVenda());
    }

    // CALCULAR TOTAIS
    private void calcularTotais(Venda venda) {
        Double quantidadeTotal = 0.0;
        Double valorTotal = 0.0;

        for (ItemVenda item : listaItemVenda) {
            if (item.getQuantidade() != null && item.getValor() != null) {
                quantidadeTotal += item.getQuantidade();
                valorTotal += item.getQuantidade() * item.getValor();
            }
        }
        venda.setQuantidadeTotal(quantidadeTotal);
        venda.setValorTotal(valorTotal);
    }

    // LISTAR TODAS AS VENDAS
    @GetMapping("/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/vendas/lista");
        mv.addObject("listarVendas", vendaRepositorio.findAll());
        return mv;
    }

    // ABRIR TELA DE EDIÇÃO DE VENDA
    @GetMapping("/editar/{id}")
    public ModelAndView editarVenda(@PathVariable("id") Long id) {
        Venda venda = vendaRepositorio.findById(id).orElse(null);

        ModelAndView mv = new ModelAndView("administrativo/vendas/cadastro");
        mv.addObject("venda", venda);
        mv.addObject("itemVenda", new ItemVenda());

        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaClientes", clienteRepositorio.findAll());
        mv.addObject("listaProdutos", produtoRepositorio.findAll());

        return mv;
    }

    // REMOVER VENDA E ITENS ASSOCIADOS
    @GetMapping("/remover/{id}")
    public ModelAndView removerVenda(@PathVariable("id") Long id) {
        Venda venda = vendaRepositorio.findById(id).orElse(null);

        if (venda != null) {
            List<ItemVenda> itens = itemVendaRepositorio.findByVenda(venda);
            itemVendaRepositorio.deleteAll(itens);
            vendaRepositorio.delete(venda);
        }

        return listar();
    }
}