package com.patriciocontreras.model;



import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_gastos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoGasto implements Serializable{



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String tipo;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
