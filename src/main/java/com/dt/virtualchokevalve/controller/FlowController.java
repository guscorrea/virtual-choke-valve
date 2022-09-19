package com.dt.virtualchokevalve.controller;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dt.virtualchokevalve.persistence.FlowRepository;
import com.dt.virtualchokevalve.persistence.entity.Flow;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Flow")
public class FlowController {

	private final FlowRepository flowRepository;

	@Autowired
	public FlowController(FlowRepository flowRepository) {
		this.flowRepository = flowRepository;
	}

	@GetMapping("/v1/flow")
	@Operation(summary = "Retrieves all flow resources.", description = "Retrieves all flow resources in a list.", responses = {
			@ApiResponse(responseCode = "200", description = "The list of flow resources was retrieved.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Flow.class))) }) })
	public ResponseEntity<List<Flow>> listFlow() {
		return new ResponseEntity<>(flowRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/v1/flow/{id}")
	@Operation(summary = "Retrieves all flow resources by UUID.", description = "Retrieves all flow resources in a list, filtered by UUID. "
			+ "Start date and time can also be used as optional filtering parameters.",
			responses = { @ApiResponse(responseCode = "200", description = "The list of flow resources was retrieved.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Flow.class))) }),
					@ApiResponse(responseCode = "400", description = "The request failed validation.", content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(type = "string", example = "Invalid UUID string")) }) })
	public ResponseEntity<List<Flow>> getFlow(@PathVariable("id") String id, @RequestParam(required = false) String startDateTime,
			@RequestParam(required = false) String endDateTime) {
		if (areDateFiltersInformed(startDateTime, endDateTime)) {
			List<Flow> flowList = flowRepository.findAllByChokeValveIdAndDateTime(UUID.fromString(id), startDateTime, endDateTime);
			return new ResponseEntity<>(flowList, HttpStatus.OK);
		}
		return new ResponseEntity<>(flowRepository.findAllByChokeValveId(UUID.fromString(id)), HttpStatus.OK);
	}

	private boolean areDateFiltersInformed(String startDateTime, String endDateTime) {
		return StringUtils.isNotEmpty(startDateTime) && StringUtils.isNotEmpty(endDateTime);
	}

}
