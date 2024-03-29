package com.dt.virtualchokevalve.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.dt.virtualchokevalve.messaging.MqttMessageReceiver;

@Configuration
public class MqttConfig {

	private final MqttMessageReceiver mqttMessageReceiver;

	@Value("${mqtt.url}")
	private String url;

	@Value("${mqtt.username}")
	private String username;

	@Value("${mqtt.password}")
	private String password;

	@Value("${mqtt.clientId}")
	private String clientId;

	@Value("${mqtt.qos}")
	private int qos;

	public MqttPahoMessageDrivenChannelAdapter adapter;

	public MqttConfig(MqttMessageReceiver mqttMessageReceiver) {
		this.mqttMessageReceiver = mqttMessageReceiver;
	}

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { url });
		options.setUserName(username);
		options.setPassword(password.toCharArray());
		options.setCleanSession(false);
		options.setMaxInflight(2000);
		factory.setConnectionOptions(options);
		return factory;
	}

	@Bean
	public MessageChannel chokeValveInputChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageProducer inbound() {
		adapter = new MqttPahoMessageDrivenChannelAdapter(clientId, mqttClientFactory(),
				"choke.test.temperature", "choke.test.pressure");
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter(qos, false));
		adapter.setQos(qos);
		adapter.setOutputChannel(chokeValveInputChannel());
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "chokeValveInputChannel")
	public MessageHandler mqttMessageHandler() {
		return this.mqttMessageReceiver;
	}

}
