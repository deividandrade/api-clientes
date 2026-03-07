package com.springboot.api_clientes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api_clientes.modelos.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

}
