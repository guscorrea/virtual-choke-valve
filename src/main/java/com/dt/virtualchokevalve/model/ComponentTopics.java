package com.dt.virtualchokevalve.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.dt.virtualchokevalve.model.enums.MeasurementType;
import lombok.Data;

@Data
public class ComponentTopics {

	private static final String CHOKE_TOPIC = "choke.";

	private static final String PRESSURE = "." + MeasurementType.pressure;

	private static final String TEMPERATURE = "." + MeasurementType.temperature;

	private static final String FLOW = "." + MeasurementType.flow;

	private final String componentId;

	private final String baseTopicName;

	private final String pressureTopicName;

	private final String temperatureTopicName;

	private final String flowTopicName;

	@Autowired
	public ComponentTopics(String componentId) {
		this.componentId = componentId;
		this.baseTopicName = CHOKE_TOPIC + componentId;
		this.pressureTopicName = CHOKE_TOPIC + componentId + PRESSURE;
		this.temperatureTopicName = CHOKE_TOPIC + componentId + TEMPERATURE;
		this.flowTopicName = CHOKE_TOPIC + componentId + FLOW;
	}

}
