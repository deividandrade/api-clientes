package com.springboot.api_clientes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api_clientes.modelos.Estado;

public interface EstadoRepositorio extends JpaRepository<Estado, Long> {

}
