package com.patriciocontreras.DTO;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.patriciocontreras.entity.Gasto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleGastoDTO {
	
	private int precio;
	private int cantidad;
	private int subtotal;
	

	
	public Map<String, Object> toMap(){
		 Map<String, Object> map = new HashMap<>();
	        map.put("precio", precio);
	        map.put("cantidad", cantidad);
	        map.put("subtotal", subtotal);
	        return map;
	}
	

	

}
