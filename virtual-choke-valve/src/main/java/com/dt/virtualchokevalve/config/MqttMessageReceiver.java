package com.dt.virtualchokevalve.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import com.dt.virtualchokevalve.model.MqttRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MqttMessageReceiver implements MessageHandler {

	private final ObjectMapper objectMapper;

	@Autowired
	public MqttMessageReceiver(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		try {
			String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
			MqttRequest mqttRequest = objectMapper.readValue(message.getPayload().toString(), MqttRequest.class);
			System.out.println("Message consumed from " + topic + " with LocalDateTime: " + mqttRequest.getTimeStamp() + " and value: "
					+ mqttRequest.getValue());
		}
		catch (JsonProcessingException e) {
			System.out.println("Error deserializing Mqtt Request " + message.getPayload());
		}
	}

}