package com.br.matvcirino.genericRestaurantDeliverySystem.exceptions;

@SuppressWarnings("serial")
public class ClienteNotFoundException extends RuntimeException {
	
	public ClienteNotFoundException(Long id) {
		super("Não foi possível encontrar cliente " + id);
	}
}
