package io.pivotal.poc.du.messaging.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Profile("junit")
@Configuration
@EnableTransactionManagement
public class JunitConfiguration {

	@Bean
	public ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("vm://localhost");
		return connectionFactory;
	}
	
	@Bean(name="inbound.queue")
	public Queue queue() {
		ActiveMQQueue queue = new ActiveMQQueue("inbound.queue");
		return queue;
	}
	
}
