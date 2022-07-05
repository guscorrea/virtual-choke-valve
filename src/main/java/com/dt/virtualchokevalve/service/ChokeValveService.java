package com.dt.virtualchokevalve.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.virtualchokevalve.config.MqttConfig;
import com.dt.virtualchokevalve.exception.ChokeValveNotFoundException;
import com.dt.virtualchokevalve.model.ChokeValveRequest;
import com.dt.virtualchokevalve.model.ComponentTopics;
import com.dt.virtualchokevalve.persistence.ChokeValveRepository;
import com.dt.virtualchokevalve.persistence.entity.ChokeValve;

@Service
public class ChokeValveService {

	@Resource
	private MqttConfig mqttConfig;

	private final ChokeValveRepository chokeValveRepository;

	@Autowired
	public ChokeValveService(ChokeValveRepository chokeValveRepository) {
		this.chokeValveRepository = chokeValveRepository;
	}

	public List<ChokeValve> getAllChokeValves() {
		return chokeValveRepository.findAll();
	}

	public ChokeValve getChokeValve(UUID id) {
		ChokeValve chokeValve = chokeValveRepository.find(id);
		if (Objects.isNull(chokeValve)) {
			throw new ChokeValveNotFoundException("Choke Valve with id " + id.toString() + " not found in the database.");
		}
		return chokeValve;
	}

	public ChokeValve saveChokeValve(ChokeValveRequest chokeValveRequest) {
		System.out.println("Creating a choke valve with name " + chokeValveRequest.getName());
		ChokeValve chokeValve = new ChokeValve(UUID.randomUUID(), chokeValveRequest.getName(), chokeValveRequest.getValveInfo(), LocalDateTime.now());
		addTopics(chokeValve);
		return chokeValveRepository.save(chokeValve);
	}

	public ChokeValve updateChokeValve(UUID id, ChokeValveRequest chokeValveRequest) {
		ChokeValve chokeValve = getChokeValve(id);
		System.out.println("Updating choke valve with id " + id);
		chokeValve.setName(chokeValveRequest.getName());
		chokeValve.setValveInfo(StringUtils.defaultIfEmpty(chokeValveRequest.getValveInfo(), chokeValve.getValveInfo()));
		return chokeValveRepository.save(chokeValve);
	}

	public void deleteChokeValve(UUID id) {
		System.out.println("Deleting choke valve with id " + id);
		chokeValveRepository.delete(id);
		removeDefaultTopics(id);
	}

	private void addTopics(ChokeValve chokeValve) {
		ComponentTopics newComponentTopics = new ComponentTopics(chokeValve.getChokeValveId().toString());

		String temperatureTopic = newComponentTopics.getTemperatureTopicName();
		System.out.println("Adding new topic: " + temperatureTopic);
		mqttConfig.adapter.addTopic(temperatureTopic, 2);

		String pressureTopic = newComponentTopics.getPressureTopicName();
		System.out.println("Adding new topic: " + pressureTopic);
		mqttConfig.adapter.addTopic(pressureTopic, 2);

		String flowTopic = newComponentTopics.getFlowTopicName();
		System.out.println("Adding new topic: " + flowTopic);
		mqttConfig.adapter.addTopic(flowTopic, 2);

		String customTopic = newComponentTopics.getCustomTopicName();
		System.out.println("Adding new topic: " + customTopic);
		mqttConfig.adapter.addTopic(customTopic, 2);

	}

	private void removeDefaultTopics(UUID id) {
		ComponentTopics componentTopics = new ComponentTopics(id.toString());
		mqttConfig.adapter.removeTopic(componentTopics.getTemperatureTopicName(),
				componentTopics.getPressureTopicName(),
				componentTopics.getFlowTopicName(),
				componentTopics.getCustomTopicName());
	}

}
