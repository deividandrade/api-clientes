package com.springboot.api_clientes.modelos;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "itens_venda")
public class ItemVenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double quantidade;

    private Double valor;

    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @PrePersist
    @PreUpdate
    public void calcularSubtotal() {
        if (quantidade != null && valor != null) {
            subtotal = quantidade * valor;
        }
    }

    // Getters e Setters
    public Long getId() { return id; }

    public Double getQuantidade() { return quantidade; }
    public void setQuantidade(Double quantidade) { this.quantidade = quantidade; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public Double getSubtotal() { return subtotal; }

    public Venda getVenda() { return venda; }
    public void setVenda(Venda venda) { this.venda = venda; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
}