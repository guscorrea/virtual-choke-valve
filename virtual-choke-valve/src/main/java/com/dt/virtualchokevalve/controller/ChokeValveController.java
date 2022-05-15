package com.dt.virtualchokevalve.controller;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dt.virtualchokevalve.config.MqttConfig;

@RestController
public class ChokeValveController {

	@Resource
	MqttConfig mqttConfig;

	@PostMapping("/add-topic/{name}")
	public ResponseEntity<Void> addTopic(@PathVariable("name") String name) {
		System.out.println("Adding new topic: " + name);
		mqttConfig.adapter.addTopic(name, 2);
		return ResponseEntity.ok().build();
	}

}
