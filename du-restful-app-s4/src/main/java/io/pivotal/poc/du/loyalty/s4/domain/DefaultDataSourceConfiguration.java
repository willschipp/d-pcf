package io.pivotal.poc.du.loyalty.s4.domain;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class DefaultDataSourceConfiguration {

	@Bean
	public DataSource dataSource() {
		return JdbcConnectionPool.create("jdbc:h2:mem:a","sa","");
	}	
	
}
