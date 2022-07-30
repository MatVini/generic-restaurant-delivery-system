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
import com.br.matvcirino.genericRestaurantDeliverySystem.entity.Pedido;
import com.br.matvcirino.genericRestaurantDeliverySystem.exceptions.PedidoNotFoundException;
import com.br.matvcirino.genericRestaurantDeliverySystem.repository.RepositorioCliente;
import com.br.matvcirino.genericRestaurantDeliverySystem.repository.RepositorioPedido;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "API para controle de pedidos")
public class PedidoController {

	@Autowired
	private RepositorioPedido repositorio;
	
	@Autowired
	private RepositorioCliente repCliente;
	
	@Operation(summary = "Lista todos os pedidos", description = "Não mostra o ID do cliente, esta informação fica nas APIs de cliente.", tags = {"Pedidos"})
	@GetMapping
	List<Pedido> listarTodos() {
		return repositorio.findAll();
	}
	
	@Operation(summary = "Registra um cliente", description = "Requer ID do cliente que fez o pedido, e as especificações do pedido.", tags = {"Pedidos"})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Pedido novoPedido(@RequestBody JsonControlePedido json) {
		Cliente cliente = repCliente.getReferenceById(json.getClienteId());
		String especificacoes = json.getEspecificacoes();
		Pedido novoPedido = new Pedido(cliente, especificacoes);
		return repositorio.save(novoPedido);
	}
	
	@Operation(summary = "Mostra um pedido, buscando por seu ID", description = "", tags = {"Pedidos"})
	@GetMapping("/{id}")
	Pedido listarUm(@PathVariable Long id) {
		return repositorio.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
	}
	
	@Operation(summary = "Atualiza dados de um pedido", description = "Requer ID do cliente referente ao pedido (não precisa ser o mesmo cliente), e as especificações do pedido.", tags = {"Pedidos"})
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	Pedido atualizarPedido(@PathVariable Long id, @RequestBody JsonControlePedido json) {
		return repositorio.findById(id).map(pedido -> {
			Cliente cliente = repCliente.getReferenceById(json.getClienteId());
			String especificacoes = json.getEspecificacoes();
			pedido.setCliente(cliente);
			pedido.setEspecificacoes(especificacoes);
			return repositorio.save(pedido);
		}).orElseThrow(() -> new PedidoNotFoundException(id));
	}
	
	@Operation(summary = "Remove registro de um pedido", description = "", tags = {"Pedidos"})
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deletarPedido(@PathVariable Long id) {
		repositorio.findById(id).map(pedido -> {
			repositorio.deleteById(pedido.getId());
			return Void.TYPE;
		}).orElseThrow(() -> new PedidoNotFoundException(id));
	}
}
