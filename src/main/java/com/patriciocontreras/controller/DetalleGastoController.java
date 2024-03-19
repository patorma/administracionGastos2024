package com.patriciocontreras.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.patriciocontreras.entity.DetalleGasto;
import com.patriciocontreras.entity.Gasto;
import com.patriciocontreras.service.IDetalleGastoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200" })
public class DetalleGastoController {
	
	@Autowired
	private IDetalleGastoService detalleGastoService;
	
	@GetMapping("/detallesGastos")
	public List<DetalleGasto> listar() throws Exception{
		return detalleGastoService.listar();
	}
	
	@GetMapping("/detallesGastos/page/{page}")
	public Page<DetalleGasto> index(@PathVariable Integer page) throws Exception{
		Pageable pageable = PageRequest.of(page, 4);
		return detalleGastoService.findAll(pageable);
	}
	
	@GetMapping("detalleGasto/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) throws Exception {
		DetalleGasto detalleGasto = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			detalleGasto = detalleGastoService.listarPorId(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(detalleGasto == null) {
			response.put("mensaje", "El detalle del gasto con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<DetalleGasto>(detalleGasto,HttpStatus.OK);
	}
	
	@PostMapping("/detalleGasto")
	public ResponseEntity<?> create(@Valid @RequestBody DetalleGasto detalleGasto,BindingResult result) throws Exception{
		// es el nuevo detalle de gasto creado
				//se inicializa
	  DetalleGasto detalleGastoNew = null;
	  Map<String, Object> response =new HashMap<>();
	  
		// se valida si contiene errores el objeto 
		if(result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					     .stream()
					     .map(err -> "El campo '"+ err.getField() + "' "+err.getDefaultMessage())
					     .collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			detalleGastoNew = detalleGastoService.registrar(detalleGasto);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El detalle del gasto ha sido creado con éxito! ");
		response.put("detalle",detalleGastoNew );
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/detalleGasto/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody DetalleGasto detalleGasto,BindingResult result,@PathVariable Long id) throws Exception{
		
		//se obtiene el detalle del gasto  que se quiere modificar
		DetalleGasto detalleGastoActual = detalleGastoService.listarPorId(id); 
		
		// Detalle del gasto ya actualizado
		DetalleGasto detalleGastoUpdated = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			// se debe obtener los mensajes de error de cada campo 
			// y convertir estos en una lista de errores de tipo string
			
			// se debe convertir esta lista de fielderrors en String
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+ err.getField() + "' "+err.getDefaultMessage())// muy parecido  al operador map en angular (rxjs), mismo concepto!
					.collect(Collectors.toList());// ahora podemos convertir de regreso el stream  aun tipo List
			response.put("errors", errors);
			// se responde con un responseentity con listados de error
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
			
			// en lo anterior se recibe un field errors y lo convertimos a string
		}
		
		if(detalleGastoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el detalle del gasto con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			//modificamos los datos del detalle del gasto actual con los datos del detalle del gasto que te envian
			detalleGastoActual.setProducto(detalleGasto.getProducto());
			detalleGastoActual.setCantidad(detalleGasto.getCantidad());
			detalleGastoActual.setPrecioUnitario(detalleGasto.getPrecioUnitario());
			detalleGastoActual.setGasto(detalleGasto.getGasto());
			
			detalleGastoUpdated = detalleGastoService.registrar(detalleGastoActual);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el detalle del gasto en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El detalle del gasto ha sido actualizado con éxito!");
		response.put("detalle",detalleGastoUpdated);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/detalleGasto/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
		DetalleGasto detalle = detalleGastoService.listarPorId(id);
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			if (detalle == null) {
				  response.put("mensaje", "No se encontró el detalle del gasto con el ID: " + id);
		          return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			detalleGastoService.eliminar(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el detalle del gasto de la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El detalle del gasto fue eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/detallesGastos/gastos")
	public List<Gasto> listarGastos(){
		return detalleGastoService.findAllGastos();
	}

}
