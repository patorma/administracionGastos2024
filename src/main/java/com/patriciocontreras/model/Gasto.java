package com.patriciocontreras.model;

import java.io.Serializable;
import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gastos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gasto implements Serializable{



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre_gasto")
	@NotEmpty
	@NotNull(message = "no puede estar vacio el nombre del gasto")
	@Size(min = 10,max = 60)
	private String nombreGasto;
	
	@Column(nullable = false)
	@NotEmpty
	@Size(min = 30,max = 300)
	private String descripcion;
	
	@Column(name="valor_total",nullable = false)
	@NotEmpty
	private int valorTotal;
	
	@Column(name = "fecha_gasto")
	@NotNull(message = "no puede estar vacia la fecha")
	@Temporal(TemporalType.DATE)
	private Date fechaGasto; 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
