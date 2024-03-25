package com.patriciocontreras.entity;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalle_gastos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleGasto implements Serializable{
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@Column(nullable = false)
	@NotEmpty
	private String producto;
	
	@Column(nullable = false)
	@NotNull(message = "no puede estar vacio la cantidad")
	private int cantidad;
	
	@Column(name = "precio_unitario",nullable = false)
	@NotNull(message = "no puede estar vacio la cantidad")
	private int precioUnitario;
	
	/*
	@Transient
	private int subTotal;


	@PrePersist
	private void calcularSubtotal() {
		this.subTotal = cantidad *  precioUnitario;
	}
	*/
	@NotNull(message = "El gasto no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gasto_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","hadler"})
	private Gasto gasto;
	
	
	private static final long serialVersionUID = 1L;
	

}
