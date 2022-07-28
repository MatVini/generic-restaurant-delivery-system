package com.br.matvcirino.genericRestaurantDeliverySystem;

public class JsonControle {
	
	private Long clienteId;
	private String especificacoes;
	
	JsonControle() {}
	
	public JsonControle(Long clienteId, String especificacoes) {
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
