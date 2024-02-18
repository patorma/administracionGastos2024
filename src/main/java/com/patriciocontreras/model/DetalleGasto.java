package com.patriciocontreras.model;


import java.io.Serializable;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
	
	@Transient
	private int subTotal;

	
	@PrePersist
	private void calcularSubtotal() {
		this.subTotal = cantidad *  precioUnitario;
	}
	
	private static final long serialVersionUID = 1L;
	

}
