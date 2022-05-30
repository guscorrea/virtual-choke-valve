package com.dt.virtualchokevalve.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "choke_valve")
public class ChokeValve {

	@PartitionKey
	private UUID chokeValveId;

	@Column
	private String name;

	@Column
	private String valveInfo;

	@Column
	private LocalDateTime creationDateTime;

}
