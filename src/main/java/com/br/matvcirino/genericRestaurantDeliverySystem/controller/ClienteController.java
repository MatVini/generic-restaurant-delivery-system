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

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private RepositorioCliente repositorio;
	
	/*ClienteController(RepositorioCliente repositorio) {
		this.repositorio = repositorio;
	}*/
	
	@GetMapping
	List<Cliente> listarTodos() {
		return repositorio.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Cliente novoCliente(@RequestBody Cliente novoCliente) {
		return repositorio.save(novoCliente);
	}
	
	@GetMapping("/{id}")
	Cliente listarUm(@PathVariable Long id) {
		return repositorio.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	Cliente atualizarCliente(@PathVariable Long id, @RequestBody Cliente novoCliente) {
		return repositorio.findById(id).map(cliente -> {
					cliente.setNome(novoCliente.getNome());
					cliente.setTelefone(novoCliente.getTelefone());
					cliente.setEndereco(novoCliente.getEndereco());
					return repositorio.save(cliente);
				}).orElseThrow(() -> new ClienteNotFoundException(id));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deletarCliente(@PathVariable Long id) {
		repositorio.findById(id).map(cliente -> {
			repositorio.deleteById(cliente.getId());
			return Void.TYPE;
		}).orElseThrow(() -> new ClienteNotFoundException(id));
	}
}
