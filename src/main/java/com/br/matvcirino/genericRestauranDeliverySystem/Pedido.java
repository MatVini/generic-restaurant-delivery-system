package com.br.matvcirino.genericRestauranDeliverySystem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pedido {

	private @Id @GeneratedValue Long id;
	private Long idCliente;
	private String especificacoes;

	Pedido() {}
	
	Pedido(Long idCliente, String especificacoes) {
		this.idCliente = idCliente;
		this.especificacoes = especificacoes;
	}
	
	public Long getId() {
		return id;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getEspecificacoes() {
		return especificacoes;
	}

	public void setEspecificacoes(String especificacoes) {
		this.especificacoes = especificacoes;
	}
}
