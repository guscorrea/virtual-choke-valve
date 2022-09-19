package com.dt.virtualchokevalve.model;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChokeValveRequest {

	@NotBlank
	@Schema(description = "The name of the virtual choke valve resource", required = true)
	String name;

	@Schema(description = "Additional information for choke valve resource")
	String valveInfo;

}
