package com.springboot.api_clientes.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api_clientes.modelos.Entrada;
import com.springboot.api_clientes.modelos.itemEntrada;

public interface itemEntradaRepositorio extends JpaRepository<itemEntrada, Long> {

	List<itemEntrada> findByEntrada(Entrada entrada);

}
