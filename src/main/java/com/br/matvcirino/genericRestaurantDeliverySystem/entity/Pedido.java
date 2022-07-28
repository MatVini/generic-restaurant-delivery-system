package com.br.matvcirino.genericRestaurantDeliverySystem.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Cliente_ID")
	@JsonBackReference
	private Cliente cliente;
	
	private String especificacoes;

	Pedido() {}
	
	public Pedido(Cliente cliente, String especificacoes) {
		this.cliente = cliente;
		this.especificacoes = especificacoes;
	}
	
	public Long getId() {
		return id;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getEspecificacoes() {
		return especificacoes;
	}

	public void setEspecificacoes(String especificacoes) {
		this.especificacoes = especificacoes;
	}
}
