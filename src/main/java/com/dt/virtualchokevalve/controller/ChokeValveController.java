package com.dt.virtualchokevalve.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Choke Valve")
public class ChokeValveController {

	private final ChokeValveService chokeValveService;

	@Autowired
	public ChokeValveController(ChokeValveService chokeValveService) {
		this.chokeValveService = chokeValveService;
	}

	@GetMapping("/v1/choke-valve")
	@Operation(summary = "Retrieves all choke valves.", description = "Retrieves all choke valve resources in a list.", responses = {
			@ApiResponse(responseCode = "200", description = "The list of choke valves was retrieved.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							array = @ArraySchema(schema = @Schema(implementation = ChokeValve.class))) }) })
	public ResponseEntity<List<ChokeValve>> listChokeValve() {
		List<ChokeValve> chokeValves = chokeValveService.getAllChokeValves();
		return new ResponseEntity<>(chokeValves, HttpStatus.OK);
	}

	@GetMapping("/v1/choke-valve/{id}")
	@Operation(summary = "Retrieves a choke valve.", description = "Retrieves a choke valve resource with a given UUID.", responses = {
			@ApiResponse(responseCode = "200", description = "The choke valve was retrieved.",
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ChokeValve.class)) }),
			@ApiResponse(responseCode = "400", description = "The request failed validation.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "string", example = "Invalid UUID string")) }),
			@ApiResponse(responseCode = "404", description = "The choke valve was not found in the DB.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "string",
							example = "Choke Valve with id c5f2f64c-b02d-4635-8a34-c3d4cc2d955b not found in the database.")) }) })
	public ResponseEntity<ChokeValve> getChokeValve(@PathVariable("id") UUID id) {
		ChokeValve chokeValve = chokeValveService.getChokeValve(id);
		return new ResponseEntity<>(chokeValve, HttpStatus.OK);
	}

	@PostMapping("/v1/choke-valve")
	@Operation(summary = "Creates a choke valve resource",
			description = "Sends a post request, validates input data, and saves the generated resource into the Scylla database.", responses = {
			@ApiResponse(responseCode = "200", description = "Choke Valve resource was created",
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ChokeValve.class)) }),
			@ApiResponse(responseCode = "400", description = "The request failed validation.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = "string", example = "Field name: must not be null")) }),
			@ApiResponse(responseCode = "500", description = "Unexpected error occurred",
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	public ResponseEntity<ChokeValve> createChokeValve(@RequestBody @Valid ChokeValveRequest chokeValveRequest) {
		ChokeValve chokeValve = chokeValveService.saveChokeValve(chokeValveRequest);
		return new ResponseEntity<>(chokeValve, HttpStatus.OK);
	}

	@PutMapping("/v1/choke-valve/{id}")
	@Operation(summary = "Updates a choke valve resource",
			description = "Sends a put request, validates input data, checks if the current resource exists, and saves the updated resource into the "
					+ "Scylla database.", responses = {
			@ApiResponse(responseCode = "200", description = "Choke Valve resource was updated",
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ChokeValve.class)) }),
			@ApiResponse(responseCode = "400", description = "The request failed validation.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = "string", example = "Field name: must not be null")) }),
			@ApiResponse(responseCode = "404", description = "The choke valve was not found in the DB.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "string",
							example = "Choke Valve with id c5f2f64c-b02d-4635-8a34-c3d4cc2d955b not found in the database.")) }),
			@ApiResponse(responseCode = "500", description = "Unexpected error occurred",
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	public ResponseEntity<ChokeValve> updateChokeValve(@PathVariable("id") UUID id, @RequestBody @Valid ChokeValveRequest chokeValveRequest) {
		ChokeValve updatedChokeValve = chokeValveService.updateChokeValve(id, chokeValveRequest);
		return new ResponseEntity<>(updatedChokeValve, HttpStatus.OK);
	}

	@DeleteMapping("/v1/choke-valve/{id}")
	@Operation(summary = "Deletes a choke valve resource", description = "Deletes a choke valve resource with given UUID.",
			responses = { @ApiResponse(responseCode = "204", description = "The choke valve was deleted.") })
	public ResponseEntity<Void> deleteChokeValve(@PathVariable("id") UUID id) {
		chokeValveService.deleteChokeValve(id);
		return ResponseEntity.noContent().build();
	}

}
