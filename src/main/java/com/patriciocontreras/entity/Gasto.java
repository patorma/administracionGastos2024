package com.patriciocontreras.entity;

import java.io.Serializable;
import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

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
	
	
	@Column(nullable = false)
	@NotEmpty
	@Size(min = 30,max = 300)
	private String descripcion;
	
	
	@Column(name = "fecha_gasto")
	@NotNull(message = "no puede estar vacia la fecha")
	@Temporal(TemporalType.DATE)
	private Date fechaGasto; 
	
	/*@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "tipo_gasto_id",referencedColumnName = "id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","hadler"})*/
	@Column(name="tipo_gasto",nullable = false)
	@Enumerated(value = EnumType.STRING)
	private TiposGastos tipoGasto;
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
