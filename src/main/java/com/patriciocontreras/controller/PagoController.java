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

import com.patriciocontreras.entity.Gasto;
import com.patriciocontreras.entity.LocalPago;
import com.patriciocontreras.entity.Pago;
import com.patriciocontreras.service.IPagoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "http://localhost:4200" })
public class PagoController {

	@Autowired
	private IPagoService pagoService;

	@GetMapping("/pagos")
	public List<Pago> listar() throws Exception {
		return pagoService.listar();
	}

	@GetMapping("/pagos/page/{page}")
	public Page<Pago> index(@PathVariable Integer page) throws Exception {
		Pageable pageable = PageRequest.of(page, 4);
		return pagoService.findAll(pageable);
	}

	@GetMapping("/pago/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) throws Exception {

		Pago pago = null;
		Map<String, Object> response = new HashMap<>();

		try {
			pago = pagoService.listarPorId(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (pago == null) {
			response.put("mensaje", "El pago con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Pago>(pago, HttpStatus.OK);
	}

	@PostMapping("/pago")
	public ResponseEntity<?> create(@Valid @RequestBody Pago pago, BindingResult result) throws Exception {
		Pago pagoNew = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			pagoNew = pagoService.registrar(pago);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El pago ha sido creado con éxito! ");
		response.put("pago", pagoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/pago/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Pago pago,BindingResult result,@PathVariable Long id) throws Exception{
		
		//se obtiene el pago  que se quiere modificar
		Pago pagoActual = pagoService.listarPorId(id);
		
		// Corresponde al pago  ya actualizado
		Pago pagoUpdated =null;
		
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
		
		if(pagoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el pago del gasto con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			//modificamos los datos del pago del gasto actual con los datos del pago del gasto que te envian
			pagoActual.setTipoPago(pago.getTipoPago());
			pagoActual.setLocalPago(pago.getLocalPago());
			pagoActual.setGasto(pago.getGasto());
			pagoActual.setMetodoOnline(pago.isMetodoOnline());
		
			
			pagoUpdated = pagoService.registrar(pagoActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el pago del gasto en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El pago del gasto ha sido actualizado con éxito!");
		response.put("pago",pagoUpdated );
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}    
	
	@DeleteMapping("/pago/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
		
		Pago pago = pagoService.listarPorId(id);
		Map<String, Object> response = new HashMap<>();
		
		try {
			if(pago == null) {
				  response.put("mensaje", "No se encontró el pago con ese ID: " + id);
		            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			pagoService.eliminar(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el pago del gasto de la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El pago del gasto fue eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/pagos/gastos")
	public List<Gasto> listarGastos(){
	  return  pagoService.findAllGastos();
	}
	
	@GetMapping("/pagos/locales")
	public List<LocalPago> listarLocales(){
		return pagoService.findAllLocales();
	}
}
