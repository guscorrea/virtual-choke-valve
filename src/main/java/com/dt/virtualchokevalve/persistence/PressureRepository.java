package com.dt.virtualchokevalve.persistence;

import static com.datastax.driver.core.DataType.text;
import static com.datastax.driver.core.DataType.timestamp;
import static com.datastax.driver.core.DataType.uuid;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.gte;
import static com.datastax.driver.core.querybuilder.QueryBuilder.lte;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.dt.virtualchokevalve.persistence.entity.Pressure;

@Repository
public class PressureRepository {

	private Mapper<Pressure> mapper;

	private Session session;

	private static final String TABLE = "pressure";

	public PressureRepository(MappingManager mappingManager) {
		createTable(mappingManager.getSession());
		this.mapper = mappingManager.mapper(Pressure.class);
		this.session = mappingManager.getSession();
	}

	private void createTable(Session session) {
		session.execute(
				SchemaBuilder.createTable(TABLE)
						.ifNotExists()
						.addPartitionKey("choke_valve_id", uuid())
						.addClusteringColumn("timestamp", timestamp())
						.addColumn("value", text()));
	}

	public List<Pressure> findAll() {
		final ResultSet result = session.execute(select().all().from(TABLE));
		return mapper.map(result).all();
	}

	public List<Pressure> findAllByChokeValveId(UUID chokeValveId) {
		final ResultSet result = session.execute(select().all().from(TABLE).where(eq("choke_valve_id", chokeValveId)));
		return mapper.map(result).all();
	}

	public List<Pressure> findAllByChokeValveIdAndDateTime(UUID chokeValveId, String startDateTime, String endDateTime) {
		final ResultSet result = session.execute(
				select().all().from(TABLE).where(eq("choke_valve_id", chokeValveId))
						.and(gte("timestamp", LocalDateTime.parse(startDateTime)))
						.and(lte("timestamp", LocalDateTime.parse(endDateTime))));
		return mapper.map(result).all();
	}

	public Pressure save(Pressure pressure) {
		mapper.save(pressure);
		return pressure;
	}

}
