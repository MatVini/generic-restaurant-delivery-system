package com.br.matvcirino.genericRestaurantDeliverySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.matvcirino.genericRestaurantDeliverySystem.entity.Entrega;

@Repository
public interface RepositorioEntrega extends JpaRepository<Entrega, Long> {

}
