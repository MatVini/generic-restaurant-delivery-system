package com.br.matvcirino.genericRestaurantDeliverySystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.br.matvcirino.genericRestaurantDeliverySystem.StatusDeEntrega;

@Entity
public class Entrega {

	@Id
	private Long idPedido;
	
	@Enumerated(EnumType.STRING) @Column(length = 12)
	private StatusDeEntrega status;
	
	Entrega() {}
	
	Entrega (StatusDeEntrega status) {
		this.status = status;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public StatusDeEntrega getStatus() {
		return status;
	}

	public void setStatus(StatusDeEntrega status) {
		this.status = status;
	}
}
