package com.dt.virtualchokevalve.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "choke_valve")
@Schema(description = "Choke Valve resource")
public class ChokeValve {

	@PartitionKey
	@Schema(description = "The virtual choke valve unique identifier", example = "ccf9e52b-e2e4-45d8-8884-0721d3246a53")
	private UUID chokeValveId;

	@Column
	@Schema(description = "The name of the virtual choke valve resource", example = "Choke valve #1")
	private String name;

	@Column
	@Schema(description = "Additional information for choke valve resource", example = "Additional info")
	private String valveInfo;

	@Column
	@Schema(description = "Resource creation date and time")
	private LocalDateTime creationDateTime;

}
