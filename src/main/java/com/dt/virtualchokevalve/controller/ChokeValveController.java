package com.dt.virtualchokevalve.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dt.virtualchokevalve.model.ChokeValveRequest;
import com.dt.virtualchokevalve.persistence.entity.ChokeValve;
import com.dt.virtualchokevalve.service.ChokeValveService;

@RestController
public class ChokeValveController {

	private final ChokeValveService chokeValveService;

	@Autowired
	public ChokeValveController(ChokeValveService chokeValveService) {
		this.chokeValveService = chokeValveService;
	}

	@GetMapping("/choke-valve")
	public ResponseEntity<List<ChokeValve>> listChokeValve() {
		List<ChokeValve> chokeValves = chokeValveService.getAllChokeValves();
		return new ResponseEntity<>(chokeValves, HttpStatus.OK);
	}

	@GetMapping("/choke-valve/{id}")
	public ResponseEntity<ChokeValve> getChokeValve(@PathVariable("id") UUID id) {
		ChokeValve chokeValve = chokeValveService.getChokeValve(id);
		return new ResponseEntity<>(chokeValve, HttpStatus.OK);
	}

	@PostMapping("/choke-valve")
	public ResponseEntity<ChokeValve> createChokeValve(@RequestBody @Valid ChokeValveRequest chokeValveRequest) {
		ChokeValve chokeValve = chokeValveService.saveChokeValve(chokeValveRequest);
		return new ResponseEntity<>(chokeValve, HttpStatus.OK);
	}

	@PutMapping("/choke-valve/{id}")
	public ResponseEntity<ChokeValve> updateChokeValve(@PathVariable("id") UUID id, @RequestBody @Valid ChokeValveRequest chokeValveRequest) {
		ChokeValve updatedChokeValve = chokeValveService.updateChokeValve(id, chokeValveRequest);
		return new ResponseEntity<>(updatedChokeValve, HttpStatus.OK);
	}

	@DeleteMapping("/choke-valve/{id}")
	public ResponseEntity<Void> createChokeValve(@PathVariable("id") UUID id) {
		chokeValveService.deleteChokeValve(id);
		return ResponseEntity.noContent().build();
	}

}
