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

import com.patriciocontreras.entity.Nota;
import com.patriciocontreras.service.INotaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "http://localhost:4200" })
public class NotaController {
	
	@Autowired
	private INotaService notaService;
	
	@GetMapping("/notas")
    public List<Nota> listar() throws Exception{
		return notaService.listar();
	}
	
	@GetMapping("/notas/page/{page}")
	public Page<Nota> index(@PathVariable Integer page) throws Exception{
		Pageable pageable = PageRequest.of(page, 4);
		return notaService.findAll(pageable);
	} 
	
	@GetMapping("/nota/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) throws Exception{
		
		Nota nota = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			nota = notaService.listarPorId(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(nota == null) {
			response.put("mensaje", "La nota con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Nota>(nota,HttpStatus.OK);
		
		
	}
	
	@PostMapping("/nota")
	public ResponseEntity<?> create(@Valid @RequestBody Nota nota,BindingResult result) throws Exception{
		
		Nota notaNew = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			notaNew =notaService.registrar(nota);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El nota ha sido creado con éxito! ");
		response.put("nota",notaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/nota/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Nota nota,BindingResult result,@PathVariable Long id) throws Exception{
		//se obtiene la nota que se quiere modificar
		Nota notaActual = notaService.listarPorId(id);
		
		// Corresponde a la nota ya actualizada
		Nota notaUpdated = null;
		
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
		
		//si no encuentra la nota a modificar
		if(notaActual == null) {
			
			response.put("mensaje", "Error: no se pudo editar la nota con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			notaActual.setTitulo(nota.getTitulo());
			notaActual.setContenido(nota.getContenido());
			notaActual.setFechaNota(nota.getFechaNota());
			
			notaUpdated = notaService.registrar(notaActual);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la nota en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La nota ha sido actualizada con éxito!");
		response.put("nota", notaUpdated);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/nota/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) throws Exception{
		
		Nota nota = notaService.listarPorId(id);
		Map<String, Object> response = new HashMap<>();
		
		try {
			if(nota == null) {
				  response.put("mensaje", "No se encontró la nota con el ID: " + id);
		          return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			notaService.eliminar(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la nota de la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La nota fue eliminada con éxito!");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
}
