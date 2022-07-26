package com.br.matvcirino.genericRestaurantDeliverySystem.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Entrega {

	private @Id Long idPedido;
	private String status; // reformar para Enum
	
	Entrega() {}
	
	Entrega (String status) {
		this.status = status;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
