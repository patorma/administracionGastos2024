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
public class DetalleGastoDTO {
	
	    private int precio;
	    private int cantidad;
	    private int subtotal; // Cambiar a Integer
	    private String producto;

	    public Map<String, Object> toMap(){
	        Map<String, Object> map = new HashMap<>();
	        map.put("precio", precio);
	        map.put("cantidad", cantidad);
	        map.put("subtotal", subtotal);
	        map.put("producto", producto);
	        return map;
	    }
	

	

}
