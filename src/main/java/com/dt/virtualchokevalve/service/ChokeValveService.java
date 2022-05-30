package com.dt.virtualchokevalve.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.virtualchokevalve.config.MqttConfig;
import com.dt.virtualchokevalve.model.ChokeValveRequest;
import com.dt.virtualchokevalve.model.enums.MeasurementType;
import com.dt.virtualchokevalve.persistence.ChokeValveRepository;
import com.dt.virtualchokevalve.persistence.entity.ChokeValve;

@Service
public class ChokeValveService {

	@Resource
	private MqttConfig mqttConfig;

	private ChokeValveRepository chokeValveRepository;

	@Autowired
	public ChokeValveService(ChokeValveRepository chokeValveRepository) {
		this.chokeValveRepository = chokeValveRepository;
	}

	public ChokeValve saveChokeValve(ChokeValveRequest chokeValveRequest) {
		System.out.println("Creating a choke valve with name " + chokeValveRequest.getName());
		ChokeValve chokeValve = new ChokeValve(UUID.randomUUID(), chokeValveRequest.getName(), chokeValveRequest.getValveInfo(), LocalDateTime.now());
		addTopics(chokeValve);
		return chokeValveRepository.save(chokeValve);
	}

	public List<ChokeValve> getAllChokeValves() {
		return chokeValveRepository.findAll();
	}

	public void deleteChokeValve(String id) {
		System.out.println("Deleting choke valve with id " + id);
		chokeValveRepository.delete(UUID.fromString(id));
	}

	private void addTopics(ChokeValve chokeValve) {
		String baseTopicName = "choke." + chokeValve.getChokeValveId().toString();
		String temperatureTopic = baseTopicName + "." + MeasurementType.temperature;
		String pressureTopic = baseTopicName + "." + MeasurementType.pressure;

		System.out.println("Adding new topic: " + temperatureTopic);
		mqttConfig.adapter.addTopic(temperatureTopic, 2);

		System.out.println("Adding new topic: " + pressureTopic);
		mqttConfig.adapter.addTopic(pressureTopic, 2);

	}

}
