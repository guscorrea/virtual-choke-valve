package com.dt.virtualchokevalve.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MqttRequest {

	private LocalDateTime timeStamp;

	private String value;

}
