package com.cenfotec.examen.controllers;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cenfotec.examen.entities.Factura;
import com.cenfotec.examen.entities.Paquete;
import com.cenfotec.examen.entities.Prealerta;
import com.cenfotec.examen.entities.Vuelo;
import com.cenfotec.examen.repositories.PaqueteRepository;
import com.cenfotec.examen.repositories.VueloRepository;

@RestController
@RequestMapping("/paquete")
public class PaqueteController {
	@Autowired
	PaqueteRepository service;
	@Autowired 
	VueloRepository vueloService;
	@PostMapping()
	public Paquete registrarPaquete(@RequestBody Paquete paquete) {
		paquete.setPrealerta(false);
		Paquete paqueteGuardado = service.save(paquete);
		return paqueteGuardado;

	}	
	@PutMapping("/prealerta")
	public String prealertarPaquete(@RequestBody Prealerta prealerta) {
		Optional<Paquete> paquete =  service.findOneByCourierId(prealerta.getIdCourier());
		if(paquete.isPresent()) {
			Paquete paquetePrealertado =  paquete.get();
			paquetePrealertado.setPrealerta(true);
			paquetePrealertado.setFactura(prealerta.getFactura());
			service.save(paquetePrealertado);
			agregarPaqueteVuelo(paquetePrealertado);
			return "paquete prealertado exitosamente";
		}
		return "Lo sentimos no encontramos paquete para prealertar";
	}
	@PutMapping("/factura/{idCourier}")
	public String facturarPaquete(@PathVariable long idCourier, @RequestBody Factura factura) {
		Optional<Paquete> paquete =  service.findOneByCourierId(idCourier);
		if(paquete.isPresent()) {
			Paquete paquetePrealertado =  paquete.get();
			paquetePrealertado.setFactura(factura);
			service.save(paquetePrealertado);
			agregarPaqueteVuelo(paquetePrealertado);
			return "paquete facturado exitosamente";
		}
		return "Lo sentimos no encontramos paquete para facturar";
	}
	
	private String getFechaVuelo (Date fecha) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance(); 
		c.setTime(fecha); 
		c.add(Calendar.DATE, 1);
		return  dateFormat.format(c.getTime());
	}
	private void agregarPaqueteVuelo ( Paquete paqueteGuardado) {
		String fechaVuelo =  getFechaVuelo(paqueteGuardado.getFecha());
		Vuelo vuelo = new Vuelo();
		Optional<Vuelo> optVuelo =  vueloService.findById(fechaVuelo);
		if(optVuelo.isPresent()) {
			vuelo =  optVuelo.get();
			vuelo.getListaPaquetes().add(paqueteGuardado);
		} else {
			vuelo.setFechaVuelo(fechaVuelo);
			vuelo.getListaPaquetes().add(paqueteGuardado);
		}
		vueloService.save(vuelo);
		
	}
}
