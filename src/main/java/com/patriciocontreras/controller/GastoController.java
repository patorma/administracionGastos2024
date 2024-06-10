package com.patriciocontreras.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patriciocontreras.DTO.DetalleGastoDTO;

import com.patriciocontreras.entity.Gasto;
import com.patriciocontreras.service.IGastoService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200" })
public class GastoController {

	@Autowired
	private IGastoService gastoService;
	
	
	@GetMapping("/gastos")
	public List<Gasto> listar() throws Exception{
		return gastoService.listar();
	}
	
	@GetMapping("/gastos/page/{page}")
	public Page<Gasto> index(@PathVariable Integer page) throws Exception{
		Pageable pageable = PageRequest.of(page, 4);
		return gastoService.findAll(pageable);
	}
	
	@GetMapping("/gasto/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) throws Exception{
		
		Gasto gasto =null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			gasto = gastoService.listarPorId(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(gasto == null) {
			response.put("mensaje", "El gasto con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Gasto>(gasto,HttpStatus.OK);
		
	}
	
	@PostMapping("/gasto")
	public ResponseEntity<?> create(@Valid @RequestBody Gasto gasto,BindingResult result) throws Exception{
		// es el nuevo gasto creado
		//se inicializa
		Gasto gastoNew = null;
		Map<String, Object> response =new HashMap<>();
		
		// se valida si contiene errores el objeto 
		if(result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					     .stream()
					     .map(err -> "El campo '"+ err.getField() + "' "+err.getDefaultMessage())
					     .collect(toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			gastoNew =gastoService.registrar(gasto);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El gasto ha sido creado con éxito! ");
		response.put("gasto", gastoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/gasto/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Gasto gasto,BindingResult result,@PathVariable Long id) throws Exception{
		
		//se obtiene el gasto  que se quiere modificar
		Gasto gastoActual = gastoService.listarPorId(id);
		
		//gasto ya actualizado
		Gasto gastoUpdated =null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			// se debe obtener los mensajes de error de cada campo 
			// y convertir estos en una lista de errores de tipo string
			
			// se debe convertir esta lista de fielderrors en String
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+ err.getField() + "' "+err.getDefaultMessage())// muy parecido  al operador map en angular (rxjs), mismo concepto!
					.collect(toList());// ahora podemos convertir de regreso el stream  aun tipo List
			response.put("errors", errors);
			// se responde con un responseentity con listados de error
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
			
			// en lo anterior se recibe un field errors y lo convertimos a string
		}
		
		if(gastoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el gasto con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			//modificamos los datos del gasto actual con los datos del gasto que te envian
			gastoActual.setDescripcion(gasto.getDescripcion());
			gastoActual.setFechaGasto(gasto.getFechaGasto());
			gastoActual.setTipoGasto(gasto.getTipoGasto());
			
			gastoUpdated = gastoService.registrar(gastoActual);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el gasto en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El gasto ha sido actualizado con éxito!");
		response.put("gasto",gastoUpdated);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/gasto/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) throws Exception{
		
		Gasto gasto = gastoService.listarPorId(id);
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			if (gasto == null) {
				  response.put("mensaje", "No se encontró el gasto con el ID: " + id);
		            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			gastoService.eliminar(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "No se pudo eliminar el gasto, revisar si hay pagos,tipo de gasto o detalle asociados!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El gasto fue eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	/*@GetMapping("/gastos/tipoGastos")
	public List<TipoGasto> listarTipos(){
		return gastoService.findAllTipos();
	}*/
	
	@GetMapping("/gastos/detalle/{id}")
	public  ResponseEntity<?> obtenerDetallesGastos(@PathVariable Long id){
		List<Object[]> detalles =  gastoService.obtenerDetallesGastos(id);
		Map<String, Object> response = new HashMap<>();
		
		if(detalles.isEmpty()) {
			 response.put("mensaje", "No se encontró el gasto con el ID: " + id +" " +"para mostrar el detalle");
	          return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
        List<Map<String, Object>> detallesJSON = new ArrayList<>();
        for (Object[] detalle : detalles) {
           int precio = (int) detalle[0];
            int cantidad = (int) detalle[1];
            int subtotal = (int) detalle[2]; 
            String producto = (String) detalle[3];
            Long id1 = (Long) detalle[4];
           
            DetalleGastoDTO detalleGastoDTO = new DetalleGastoDTO(precio, cantidad, subtotal, producto.toString(),id1); // Convertir subtotal a String
            detallesJSON.add(detalleGastoDTO.toMap());
        }
   
        return new ResponseEntity<List<Map<String, Object>>>(detallesJSON,HttpStatus.OK);
    }

		
	
	
	@GetMapping("/gasto/subtotal/{id}")
	public ResponseEntity<?> subTotal(@PathVariable Long id ) {
		
		
		Integer subtotal = gastoService.subTotalGastosDetalle(id);
		Map<String, Object> response = new HashMap<>();
		
		if(subtotal == null) {
			response.put("mensaje", "No se encontró el gasto con el ID: " + id +" " +"para mostrar el subtotal");
	          return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
	     return new ResponseEntity<Integer>(subtotal,HttpStatus.OK);
		
	}
	
	@GetMapping("/gasto/total")
	public ResponseEntity<?> total(){
		
		Integer total = gastoService.totalGastos();
		Map<String, Object> response = new HashMap<>();
		
		if(total == null) {
			response.put("mensaje", "No hay aún total de todos los gastos del sistema");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new  ResponseEntity<Integer>(total,HttpStatus.OK);
	}
}
