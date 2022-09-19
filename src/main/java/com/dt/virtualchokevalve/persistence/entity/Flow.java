package com.dt.virtualchokevalve.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
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
@Table(name = "flow")
@Schema(description = "Flow resource")
public class Flow {

	@PartitionKey
	@Schema(description = "The virtual choke valve unique identifier", example = "ccf9e52b-e2e4-45d8-8884-0721d3246a53")
	private UUID chokeValveId;

	@ClusteringColumn
	@Schema(description = "Data generation timestamp")
	private LocalDateTime timestamp;

	@Column
	@Schema(description = "Measured percentage value", example = "85")
	private String percentage;

}
