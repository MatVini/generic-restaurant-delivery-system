package com.br.matvcirino.genericRestaurantDeliverySystem.controller;

public class JsonControlePedido {
	
	private Long clienteId;
	private String especificacoes;
	
	JsonControlePedido() {}
	
	public JsonControlePedido(Long clienteId, String especificacoes) {
		this.clienteId = clienteId;
		this.especificacoes = especificacoes;
	}

	public Long getClienteId() {
		return clienteId;
	}
	
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public String getEspecificacoes() {
		return especificacoes;
	}

	public void setEspecificacoes(String especificacoes) {
		this.especificacoes = especificacoes;
	}
}
