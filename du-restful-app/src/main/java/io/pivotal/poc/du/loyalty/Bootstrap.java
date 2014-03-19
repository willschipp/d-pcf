package io.pivotal.poc.du.loyalty;

import javax.sql.DataSource;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
public class Bootstrap {

	public static void main(String[] args) {
		SpringApplication.run(Bootstrap.class, args);
	}


	@Bean
	@Profile("default")
	public DataSource dataSource() {
		PGPoolingDataSource source = new PGPoolingDataSource();
		source.setServerName("localhost");
		source.setDatabaseName("wschipp");
		source.setUser("wschipp");
		source.setPassword("");
		source.setMaxConnections(10);
		
		return source;
	}
	
	@Bean
	@Profile("default")
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setGenerateDdl(true);
		return jpaVendorAdapter;
	}
	
}
