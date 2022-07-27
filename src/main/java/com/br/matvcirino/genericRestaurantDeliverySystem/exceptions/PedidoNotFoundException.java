package com.br.matvcirino.genericRestaurantDeliverySystem.exceptions;

@SuppressWarnings("serial")
public class PedidoNotFoundException extends RuntimeException {
	
	public PedidoNotFoundException(Long id) {
		super("Não foi possível encontrar pedido " + id);
	}
}
