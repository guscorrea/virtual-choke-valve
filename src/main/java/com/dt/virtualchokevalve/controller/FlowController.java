package com.dt.virtualchokevalve.controller;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dt.virtualchokevalve.persistence.FlowRepository;
import com.dt.virtualchokevalve.persistence.entity.Flow;

@RestController
public class FlowController {

	private final FlowRepository flowRepository;

	@Autowired
	public FlowController(FlowRepository flowRepository) {
		this.flowRepository = flowRepository;
	}

	@GetMapping("/flow")
	public ResponseEntity<List<Flow>> listFlow() {
		return new ResponseEntity<>(flowRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/flow/{id}")
	public ResponseEntity<List<Flow>> getFlow(@PathVariable("id") String id, @RequestParam(required = false) String startDateTime,
			@RequestParam(required = false) String endDateTime) {
		if (areDateFiltersInformed(startDateTime, endDateTime)) {
			List<Flow> flowList = flowRepository.findAllByChokeValveIdAndDateTime(UUID.fromString(id), startDateTime,
					endDateTime);
			return new ResponseEntity<>(flowList, HttpStatus.OK);
		}
		return new ResponseEntity<>(flowRepository.findAllByChokeValveId(UUID.fromString(id)), HttpStatus.OK);
	}

	private boolean areDateFiltersInformed(String startDateTime, String endDateTime) {
		return StringUtils.isNotEmpty(startDateTime) && StringUtils.isNotEmpty(endDateTime);
	}

}
