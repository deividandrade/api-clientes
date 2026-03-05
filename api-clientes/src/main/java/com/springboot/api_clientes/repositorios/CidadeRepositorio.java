package com.springboot.api_clientes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api_clientes.modelos.Cidade;

public interface CidadeRepositorio extends JpaRepository<Cidade, Long> {

}
