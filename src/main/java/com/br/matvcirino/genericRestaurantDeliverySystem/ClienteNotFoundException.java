package com.br.matvcirino.genericRestaurantDeliverySystem;

@SuppressWarnings("serial")
public class ClienteNotFoundException extends RuntimeException {
	
	public ClienteNotFoundException(Long id) {
		super("Não foi possível encontrar cliente " + id);
	}
}
