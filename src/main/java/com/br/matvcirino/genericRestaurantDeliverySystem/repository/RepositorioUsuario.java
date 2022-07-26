package com.br.matvcirino.genericRestaurantDeliverySystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.matvcirino.genericRestaurantDeliverySystem.entity.Usuario;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByUsername(String username);
}
