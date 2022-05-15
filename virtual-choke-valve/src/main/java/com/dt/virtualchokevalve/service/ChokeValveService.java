package com.dt.virtualchokevalve.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.virtualchokevalve.model.ChokeValveRequest;
import com.dt.virtualchokevalve.persistence.ChokeValveRepository;
import com.dt.virtualchokevalve.persistence.entity.ChokeValve;

@Service
public class ChokeValveService {

	private ChokeValveRepository chokeValveRepository;

	@Autowired
	public ChokeValveService(ChokeValveRepository chokeValveRepository) {
		this.chokeValveRepository = chokeValveRepository;
	}

	public ChokeValve saveChokeValve(ChokeValveRequest chokeValveRequest) {
		System.out.println("Creating a choke valve with name " + chokeValveRequest.getName());
		ChokeValve chokeValve = new ChokeValve(UUID.randomUUID(), chokeValveRequest.getName());
		return chokeValveRepository.save(chokeValve);
	}

	public List<ChokeValve> getAllChokeValves() {
		return chokeValveRepository.findAll();
	}

	public void deleteChokeValve(String id) {
		System.out.println("Deleting choke valve with id " + id);
		chokeValveRepository.delete(UUID.fromString(id));
	}

}
