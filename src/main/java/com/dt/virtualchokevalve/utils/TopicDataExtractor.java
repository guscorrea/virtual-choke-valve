package com.dt.virtualchokevalve.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.dt.virtualchokevalve.model.MqttTopicData;
import com.dt.virtualchokevalve.model.enums.MeasurementType;

@Component
public class TopicDataExtractor {

	public MqttTopicData getData(String topic) {
		//TODO add validations for each field
		String[] stringData = topic.split("\\.");
		UUID componentId = UUID.fromString(stringData[1]);
		MeasurementType measurementType = MeasurementType.valueOf(stringData[2]);
		return new MqttTopicData(componentId, measurementType);

	}

}
