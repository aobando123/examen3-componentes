package com.cenfotec.examen.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cenfotec.examen.utils.FechaHelper;
import com.cenfotec.examen.entities.Vuelo;
import com.cenfotec.examen.repositories.VueloRepository;

@RestController
@RequestMapping("/vuelo")
public class VueloController {
	@Autowired
	VueloRepository vueloService;
	
	@RequestMapping("/getProximoVuelo") 
	public Vuelo getPaquetesProximoVuelo() {
		String fechaVuelo = FechaHelper.getFechaVuelo(new Date());
		
		return vueloService.findById(fechaVuelo).get();
	}
}
