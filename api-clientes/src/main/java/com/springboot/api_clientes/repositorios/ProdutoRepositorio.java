package com.springboot.api_clientes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api_clientes.modelos.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

}
