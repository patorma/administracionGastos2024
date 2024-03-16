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

import com.patriciocontreras.entity.LocalPago;
import com.patriciocontreras.service.ILocalPagoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "http://localhost:4200" })
public class LocalPagoController {
	
	@Autowired
	private ILocalPagoService localPagoService;
	
	@GetMapping("/locales")
	public List<LocalPago> listar() throws Exception{
		return localPagoService.listar();
	}
	
	@GetMapping("/locales/page/{page}")
	public Page<LocalPago> index(@PathVariable Integer page) throws Exception{
		Pageable pageable = PageRequest.of(page, 4);
		return localPagoService.findAll(pageable);
	}
	
	@GetMapping("/local/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) throws Exception{
		
		LocalPago local = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			local = localPagoService.listarPorId(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(local == null) {
			response.put("mensaje", "El local con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<LocalPago>(local, HttpStatus.OK);
	}
	
	@PostMapping("/local")
	public ResponseEntity<?> create(@Valid @RequestBody LocalPago local,BindingResult result) throws Exception{
		
		LocalPago localNew = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			localNew =localPagoService.registrar(local);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El local ha sido creado con éxito! ");
		response.put("local",localNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/local/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody LocalPago local,BindingResult result,@PathVariable Long id) throws Exception{
		
		LocalPago localActual = localPagoService.listarPorId(id);
		
		LocalPago localUpdated = null;
		
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
		
		if(localActual == null) {
			response.put("mensaje", "Error: no se pudo editar el local con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			localActual.setNombre(local.getNombre());
			localActual.setDireccion(local.getDireccion());
			localActual.setCiudad(local.getCiudad());
			
			localUpdated = localPagoService.registrar(localActual);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el local en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El local ha sido actualizado con éxito!");
		response.put("local", localUpdated);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/local/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
		LocalPago local = localPagoService.listarPorId(id);
		Map<String, Object> response = new HashMap<>();
		
		try {
			if(local == null) {
				response.put("mensaje", "No se encontró el local con el ID: " + id);
		        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			localPagoService.eliminar(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el local de la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El local fue eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

}
