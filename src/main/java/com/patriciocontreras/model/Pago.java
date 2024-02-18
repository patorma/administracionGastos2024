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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pago implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "metodso_online",nullable=false)
	private boolean metodoOnline;
	
	@Column(name = "fecha_gasto")
	@NotNull(message = "no puede estar vacia la fecha")
	@Temporal(TemporalType.DATE)
	private Date fechaPago;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
