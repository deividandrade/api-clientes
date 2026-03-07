package com.springboot.api_clientes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api_clientes.modelos.Fornecedor;

public interface FornecedorRepositorio extends JpaRepository<Fornecedor, Long> {

}
