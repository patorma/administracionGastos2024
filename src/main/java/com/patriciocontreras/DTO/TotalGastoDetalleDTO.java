package com.patriciocontreras.DTO;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TotalGastoDetalleDTO {

	private int totalGasto;
	

	public Map<String, Object> toMapTotal(){
		 Map<String, Object> map = new HashMap<>();
		 map.put("totalDetalleGasto", totalGasto);
		 return map;
	}
	
}
