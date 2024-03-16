package com.patriciocontreras.entity;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
	
	@Column(name = "metodo_online",nullable=false,columnDefinition = "boolean default false")
	private boolean metodoOnline;
	
/*	@Column(name = "fecha_gasto")
	@NotNull(message = "no puede estar vacia la fecha")
	@Temporal(TemporalType.DATE)
	private Date fechaPago;*/
	
	
	@OneToOne(cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "gasto_id",referencedColumnName = "id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","hadler"})
	private Gasto gasto;
	
	@Column(nullable = false,name = "tipo_pago")
	@Enumerated(value = EnumType.STRING)
    private TipoPagos tipoPago; 
	
	@NotNull(message = "El local de pago no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "local_pago_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","hadler"})
	private LocalPago localPago;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
