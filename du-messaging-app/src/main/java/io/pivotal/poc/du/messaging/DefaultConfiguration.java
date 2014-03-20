package io.pivotal.poc.du.messaging;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

@Configuration
@Profile("default")
public class DefaultConfiguration {

	@Bean
	public DataSource dataSource() {
		JndiDataSourceLookup lookup = new JndiDataSourceLookup();
		return lookup.getDataSource("jdbc/du-messageing-app");
	}
	
	@Bean
	public ConnectionFactory connectionFactory() {
		return new RMQConnectionFactory();
	}

	@Bean(name="inbound.queue")
	public Queue queue() {
		return new RMQDestination("inbound.queue",false,false);
	}
	
}
