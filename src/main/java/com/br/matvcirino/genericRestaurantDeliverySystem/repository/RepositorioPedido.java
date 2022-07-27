package com.br.matvcirino.genericRestaurantDeliverySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.matvcirino.genericRestaurantDeliverySystem.entity.Pedido;

@Repository
public interface RepositorioPedido extends JpaRepository<Pedido, Long> {

}
