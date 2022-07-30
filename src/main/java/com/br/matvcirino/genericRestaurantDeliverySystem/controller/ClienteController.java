package com.br.matvcirino.genericRestaurantDeliverySystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.matvcirino.genericRestaurantDeliverySystem.entity.Cliente;
import com.br.matvcirino.genericRestaurantDeliverySystem.exceptions.ClienteNotFoundException;
import com.br.matvcirino.genericRestaurantDeliverySystem.repository.RepositorioCliente;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "API para controle de clientes")
public class ClienteController {

	@Autowired
	private RepositorioCliente repositorio;

	@Operation(summary = "Lista todos os clientes registrados", description = "", tags = {"Clientes"})
	@GetMapping
	List<Cliente> listarTodos() {
		return repositorio.findAll();
	}

	@Operation(summary = "Registra um cliente", description = "Requer nome, telefone e endereço do cliente.", tags = {"Clientes"})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Cliente novoCliente(@RequestBody Cliente novoCliente) {
		return repositorio.save(novoCliente);
	}

	@Operation(summary = "Mostra um cliente, buscando por seu ID", description = "", tags = {"Clientes"})
	@GetMapping("/{id}")
	Cliente listarUm(@PathVariable Long id) {
		return repositorio.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
	}

	@Operation(summary = "Atualiza dados de um cliente", description = "Requer nome, telefone e endereço.", tags = {"Clientes"})
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	Cliente atualizarCliente(@PathVariable Long id, @RequestBody Cliente novoCliente) {
		return repositorio.findById(id).map(cliente -> {
			cliente.setNome(novoCliente.getNome());
			cliente.setTelefone(novoCliente.getTelefone());
			cliente.setEndereco(novoCliente.getEndereco());
			return repositorio.save(cliente);
		}).orElseThrow(() -> new ClienteNotFoundException(id));
	}

	@Operation(summary = "Remove registro de um cliente", description = "", tags = {"Clientes"})
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deletarCliente(@PathVariable Long id) {
		repositorio.findById(id).map(cliente -> {
			repositorio.deleteById(cliente.getId());
			return Void.TYPE;
		}).orElseThrow(() -> new ClienteNotFoundException(id));
	}
}
