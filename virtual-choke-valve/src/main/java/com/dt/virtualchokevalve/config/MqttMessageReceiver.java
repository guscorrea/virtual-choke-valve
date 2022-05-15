package com.dt.virtualchokevalve.config;

import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class MqttMessageReceiver implements MessageHandler {

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
		System.out.println("Message consumed from " + topic + " - " + message.getPayload());
	}
}