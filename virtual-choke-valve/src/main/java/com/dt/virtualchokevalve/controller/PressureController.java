package com.dt.virtualchokevalve.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dt.virtualchokevalve.persistence.PressureRepository;
import com.dt.virtualchokevalve.persistence.entity.Pressure;

@RestController
public class PressureController {

	private final PressureRepository pressureRepository;

	@Autowired
	public PressureController(PressureRepository pressureRepository) {
		this.pressureRepository = pressureRepository;
	}

	@GetMapping("/pressure")
	public ResponseEntity<List<Pressure>> listPressure() {
		return new ResponseEntity<>(pressureRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/pressure/{id}")
	public ResponseEntity<List<Pressure>> getPressure(@PathVariable("id") String id) {
		return new ResponseEntity<List<Pressure>>(pressureRepository.findAllByChokeValveId(UUID.fromString(id)), HttpStatus.OK);
	}

}