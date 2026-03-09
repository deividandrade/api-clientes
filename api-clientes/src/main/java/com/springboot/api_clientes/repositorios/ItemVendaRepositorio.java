package com.springboot.api_clientes.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.api_clientes.modelos.ItemVenda;
import com.springboot.api_clientes.modelos.Venda;

public interface ItemVendaRepositorio extends JpaRepository<ItemVenda, Long> {

    List<ItemVenda> findByVenda(Venda venda);

}