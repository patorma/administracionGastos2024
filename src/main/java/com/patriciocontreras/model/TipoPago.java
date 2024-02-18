package com.patriciocontreras.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_pagos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoPago implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotEmpty
	@Size(min = 10,max = 90)
	private String nombre;
	
	@Column(nullable = false)
	@NotEmpty
	@Size(min = 30, max =300)
	private String descripcion;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
