package io.pivotal.poc.du.loyalty.s4.domain;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableJpaRepositories
@EnableTransactionManagement
public class JpaConfiguration {

	@Autowired
	private DataSource dataSource;
	
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setPackagesToScan("io.pivotal.poc.du.loyalty.s4.domain");
		factory.setDataSource(dataSource);
		factory.setJpaVendorAdapter(jpaVendorAdapter());
		factory.afterPropertiesSet();
		//return
		return factory.getObject();
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setGenerateDdl(true);
		return jpaVendorAdapter;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory());
		transactionManager.setDataSource(dataSource);
		return transactionManager;
	}
}
