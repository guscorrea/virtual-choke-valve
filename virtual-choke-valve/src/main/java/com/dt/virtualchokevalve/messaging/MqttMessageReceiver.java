package com.dt.virtualchokevalve.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import com.dt.virtualchokevalve.model.MqttRequest;
import com.dt.virtualchokevalve.model.enums.MeasurementType;
import com.dt.virtualchokevalve.persistence.TemperatureRepository;
import com.dt.virtualchokevalve.persistence.entity.Temperature;
import com.dt.virtualchokevalve.utils.TopicDataExtractor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MqttMessageReceiver implements MessageHandler {

	private final ObjectMapper objectMapper;

	private final TopicDataExtractor topicDataExtractor;

	private final TemperatureRepository temperatureRepository;

	@Autowired
	public MqttMessageReceiver(ObjectMapper objectMapper, TopicDataExtractor topicDataExtractor, TemperatureRepository temperatureRepository) {
		this.objectMapper = objectMapper;
		this.topicDataExtractor = topicDataExtractor;
		this.temperatureRepository = temperatureRepository;
	}

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		try {
			String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
			MqttRequest mqttRequest = objectMapper.readValue(message.getPayload().toString(), MqttRequest.class);
			mqttRequest.setMqttTopicData(topicDataExtractor.getData(topic));
			System.out.println("Message consumed from " + topic + " with LocalDateTime: " + mqttRequest.getTimeStamp() + " and value: "
					+ mqttRequest.getValue());

			if (MeasurementType.temperature.equals(mqttRequest.getMqttTopicData().getMeasurementType())) {
				Temperature temperature = new Temperature(mqttRequest.getMqttTopicData().getComponentId(), mqttRequest.getTimeStamp(),
						mqttRequest.getValue());
				temperatureRepository.save(temperature);
			}
			else {
				//save pressure
			}
		}
		catch (JsonProcessingException e) {
			System.out.println("Error deserializing Mqtt Request " + message.getPayload());
		}
	}

}