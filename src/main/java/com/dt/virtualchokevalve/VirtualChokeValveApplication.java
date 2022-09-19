package com.dt.virtualchokevalve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Virtual Choke Valve",
		description = "The virtual-choke-valve microservice represents the digital version of a device known as Choke Valve, commonly used"
				+ " in oil and gas production facilities. This type of control valve controls the flow of fluids as they are being produced by the"
				+ " well and can act as pressure regulators in the reservoir and flowlines.",
		version = "1.0.0"), servers = { @Server(url = "http://localhost:8081", description = "Local Docker deployment URL") })
public class VirtualChokeValveApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualChokeValveApplication.class, args);
	}

}
