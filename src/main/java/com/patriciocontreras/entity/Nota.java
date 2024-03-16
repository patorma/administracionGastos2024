package com.patriciocontreras.entity;

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
@Table(name = "notas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Nota implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotEmpty
	private String titulo;
	
	@Column(nullable = false)
	@NotEmpty
	@Size(min = 30, max =300)
	private String contenido;
	
	@Column(name = "fecha_nota")
	@NotNull(message = "no puede estar vacia la fecha de la nota")
	@Temporal(TemporalType.DATE)
	private Date fechaNota;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
