package com.patriciocontreras.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="local_pagos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalPago implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotEmpty
	private String nombre;
	
	@Column(nullable = false)
	@NotEmpty
	@Size(min = 12, max = 90)
	private String direccion;
	
	@Column(nullable = false)
	@NotEmpty
	@NotNull(message = "no puede estar vacio el nombre de la ciudad")
	private String ciudad;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
