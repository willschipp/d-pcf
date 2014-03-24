package io.pivotal.poc.du.loyalty.s4.domain;

import javax.sql.DataSource;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class DefaultDataSourceConfiguration {

	@Bean
	public DataSource dataSource() {
		PGPoolingDataSource source = new PGPoolingDataSource();
		//set
		source.setServerName("localhost");//TODO remove hard coding
		source.setDatabaseName("wschipp");
		source.setUser("wschipp");
		source.setPassword("");
		source.setMaxConnections(10);
		//return
		return source;
	}	
	
}
