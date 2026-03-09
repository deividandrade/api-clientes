package com.springboot.api_clientes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.api_clientes.modelos.Venda;

public interface VendaRepositorio extends JpaRepository<Venda, Long> {

}