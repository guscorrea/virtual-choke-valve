package com.dt.virtualchokevalve.config;

import static com.datastax.driver.core.schemabuilder.SchemaBuilder.createKeyspace;
import static com.datastax.driver.mapping.NamingConventions.LOWER_CAMEL_CASE;
import static com.datastax.driver.mapping.NamingConventions.LOWER_SNAKE_CASE;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.split;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.DefaultNamingStrategy;
import com.datastax.driver.mapping.DefaultPropertyMapper;
import com.datastax.driver.mapping.MappingConfiguration;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.PropertyMapper;

@Configuration
public class ScyllaConfig {

	@Bean
	public Cluster cluster() {
		return Cluster.builder().addContactPointsWithPorts(
				new InetSocketAddress("127.0.0.1", 9042),
				new InetSocketAddress("127.0.0.1", 9043),
				new InetSocketAddress("127.0.0.1", 9044))
				.build();
	}

	@Bean
	public Session session(Cluster cluster, @Value("${scylla.keyspace}") String keyspace) throws IOException {
		final Session session = cluster.connect();
		setupKeyspace(session, keyspace);
		return session;
	}

	@Bean
	public MappingManager mappingManager(Session session) {
		final PropertyMapper propertyMapper = new DefaultPropertyMapper().setNamingStrategy(
				new DefaultNamingStrategy(LOWER_CAMEL_CASE, LOWER_SNAKE_CASE));
		final MappingConfiguration configuration = MappingConfiguration.builder().withPropertyMapper(propertyMapper).build();
		return new MappingManager(session, configuration);
	}

	private void setupKeyspace(Session session, String keyspace) throws IOException {
		final Map<String, Object> replication = new HashMap<>();
		replication.put("class", "NetworkTopologyStrategy");
		replication.put("DC1", 3);
		session.execute(createKeyspace(keyspace).ifNotExists().with().replication(replication));
		session.execute("USE " + keyspace);
		String[] statements = split(IOUtils.toString(getClass().getResourceAsStream("/cql/setup.cql")), ";");
		Arrays.stream(statements).map(statement -> normalizeSpace(statement) + ";").forEach(session::execute);
	}

}
