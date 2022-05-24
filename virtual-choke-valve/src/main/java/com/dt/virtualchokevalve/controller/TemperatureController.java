package com.dt.virtualchokevalve.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dt.virtualchokevalve.persistence.TemperatureRepository;
import com.dt.virtualchokevalve.persistence.entity.Temperature;

@RestController
public class TemperatureController {

	private final TemperatureRepository temperatureRepository;

	@Autowired
	public TemperatureController(TemperatureRepository temperatureRepository) {
		this.temperatureRepository = temperatureRepository;
	}

	@GetMapping("/temperature")
	public ResponseEntity<List<Temperature>> listTemperature() {
		return new ResponseEntity<>(temperatureRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/temperature/{id}")
	public ResponseEntity<List<Temperature>> getTemperature(@PathVariable("id") String id) {
		return new ResponseEntity<List<Temperature>>(temperatureRepository.findAllByChokeValveId(UUID.fromString(id)), HttpStatus.OK);
	}

}
