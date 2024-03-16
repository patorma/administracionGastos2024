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

import com.patriciocontreras.entity.TipoGasto;
import com.patriciocontreras.service.ITipoGastoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200" })
public class TipoGastoController {

	@Autowired
	private ITipoGastoService tipoGastoService;
	
	@GetMapping("/tiposGastos")
	public List<TipoGasto> listar() throws Exception{
		return tipoGastoService.listar();
	}
	
	@GetMapping("/tiposGastos/page/{page}")
	public Page<TipoGasto> index(@PathVariable Integer page) throws Exception{
		Pageable pageable = PageRequest.of(page, 4);
		return tipoGastoService.findAll(pageable);
	}
	
	@GetMapping("/tipoGasto/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) throws Exception{
		
		TipoGasto tipoGasto =null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			tipoGasto = tipoGastoService.listarPorId(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(tipoGasto  == null) {
			response.put("mensaje", "El tipo de gasto con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<TipoGasto>(tipoGasto,HttpStatus.OK);
		
		
		
	}
	
	@PostMapping("/tipoGasto")
	public ResponseEntity<?> create(@Valid @RequestBody TipoGasto tipoGasto,BindingResult result) throws Exception{
		// es el nuevo tipo de gasto creado
		TipoGasto tipoGastoNew = null;
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
			tipoGastoNew = tipoGastoService.registrar(tipoGasto);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El tipo de gasto ha sido creado con éxito! ");
		response.put("tipo gasto",tipoGastoNew);
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/tipoGasto/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody TipoGasto tipoGasto,BindingResult result,@PathVariable Long id) throws Exception{
		
		//se obtiene el tipo de gasto que se quiere modificar
		TipoGasto tipoGastoActual =tipoGastoService.listarPorId(id);
		
		//tipo de gasto ya actualizado
		TipoGasto tipoGastoUpdated =null;
		
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
		
		if(tipoGastoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el tipo de gasto con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			//modificamos los datos del tipo de gasto actual con los datos del tipó de gasto que te envian
			tipoGastoActual.setTipo(tipoGasto.getTipo());
			tipoGastoUpdated = tipoGastoService.registrar(tipoGastoActual);
			
			} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el tipo de gasto en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El tipo de gasto ha sido actualizado con éxito!");
		response.put("tipo de gasto",tipoGastoUpdated);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/tipoGasto/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) throws Exception{
		TipoGasto tipo =tipoGastoService.listarPorId(id);
		Map<String, Object> response = new HashMap<>();
	
		try {
			
			  // Verificar si la entidad con el ID especificado existe
	        if (tipo== null) {
	            response.put("mensaje", "No se encontró el tipo de gasto con el ID: " + id);
	            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	        }
			
			
			tipoGastoService.eliminar(id);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el tipo de gasto de la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El tioo de gasto fue eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
}
