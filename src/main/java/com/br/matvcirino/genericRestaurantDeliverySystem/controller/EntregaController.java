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

import com.br.matvcirino.genericRestaurantDeliverySystem.entity.Entrega;
import com.br.matvcirino.genericRestaurantDeliverySystem.entity.StatusDeEntrega;
import com.br.matvcirino.genericRestaurantDeliverySystem.exceptions.PedidoNotFoundException;
import com.br.matvcirino.genericRestaurantDeliverySystem.repository.RepositorioEntrega;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/entregas")
@Tag(name = "Entregas", description = "API para controle de entregas")
public class EntregaController {

	@Autowired
	private RepositorioEntrega repositorio;
	
	@Operation(summary = "Lista todas as entregas registradas", description = "", tags = {"Entregas"})
	@GetMapping
	List<Entrega> listarTodos() {
		return repositorio.findAll();
	}
	
	@Operation(summary = "Registra uma entrega", description = "Só requer o ID do pedido, o status é automaticamente setado como 'NÃO_ENVIADO'.", tags = {"Entregas"})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Entrega novaEntrega (@RequestBody Long id) {
		Entrega entrega = new Entrega(StatusDeEntrega.NÃO_ENVIADO);
		entrega.setIdPedido(id);
		return repositorio.save(entrega);
	}
	
	@Operation(summary = "Mostra uma entrega, buscando pelo ID do pedido", description = "", tags = {"Entregas"})
	@GetMapping("/{id}")
	Entrega listarUm (@PathVariable Long id) {
		return repositorio.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
	}
	
	@Operation(summary = "Atualiza dados de uma entrega", description = "Aceita os valores 'NÃO_ENVIADO', 'A_CAMINHO' e 'ENTREGE'.", tags = {"Entregas"})
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	Entrega atualizarEntrega(@PathVariable Long id, @RequestBody StatusDeEntrega status) {
		return repositorio.findById(id).map(entrega -> {
			entrega.setStatus(status);
			return repositorio.save(entrega);
		}).orElseThrow(() -> new PedidoNotFoundException(id));
	}
	
	@Operation(summary = "Remove registro de uma entrega", description = "", tags = {"Entregas"})
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deletarEntrega(@PathVariable Long id) {
		repositorio.findById(id).map(entrega -> {
			repositorio.deleteById(id);
			return Void.TYPE;
		}).orElseThrow(() -> new PedidoNotFoundException(id));
	}
}
