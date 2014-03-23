package io.pivotal.poc.du.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * bootstrap for Spring Boot 
 * @author wschipp
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement//transaction management for both Spring Batch and JPA
@PropertySource("classpath:integration.properties")//integration/external properties
@ImportResource("classpath:/META-INF/spring/integration/file-context.xml")//integration configuration
public class Application {

	/**
	 * entry point for the application
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);//run the app
	}
	
	
	/**
	 * simple async task executor
	 * @return
	 */
	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

}
