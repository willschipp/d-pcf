package io.pivotal.poc.du.batch.job;

import io.pivotal.poc.du.batch.domain.BCBSEntity;
import io.pivotal.poc.du.batch.domain.BCBSEntityRepository;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;

/**
 * job configuration
 * 
 * has one step which, in chunks, reads lines from the file;
 * converts them to BCBSEntity objects
 * and then writes them (in blocks of 10) to the target database
 * 
 * @author wschipp
 *
 */
@Configuration
@EnableBatchProcessing
public class AsciiJob {
	
	@Autowired
	private JobRegistry jobRegistry;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Bean
	public Job insertJob(JobBuilderFactory factory,Step step1) {
		return factory.get("insertJob").incrementer(new RunIdIncrementer()).flow(step1).end().build();
	}
	
	@Bean
	public Step step1(StepBuilderFactory factory,ItemReader<BCBSEntity> reader,ItemWriter<BCBSEntity> writer) {
		return factory.get("step1").<BCBSEntity,BCBSEntity> chunk(10).reader(reader).writer(writer).build();
	}
	
	/**
	 * gets the "source" of the file as a job parameter -- initiated by Spring Integration
	 * @param source
	 * @return
	 * @throws Exception
	 */
	@Bean
	@StepScope
	public FlatFileItemReader<BCBSEntity> reader(@Value("#{jobParameters['sourceFile']}") String source) throws Exception {
		FlatFileItemReader<BCBSEntity> reader = new FlatFileItemReader<BCBSEntity>();
		reader.setResource(new UrlResource(source));
		 reader.setLineMapper(new DefaultLineMapper<BCBSEntity>() {{
	            setLineTokenizer(new DelimitedLineTokenizer() {{
	                setNames(new String[] { "id", "msisdn","planId","createddate","updateddate","amount" });//mapping to the setter names of the bean
	            }});
	            setFieldSetMapper(mapper());
	        }});
		return reader;
	}
	
	/**
	 * mapper with date/time specific converter for the YYYY-MM-DD format of the source file
	 * @return
	 */
	@Bean
	public FieldSetMapper<BCBSEntity> mapper() {
		BeanWrapperFieldSetMapper<BCBSEntity> mapper = new BeanWrapperFieldSetMapper<BCBSEntity>();
		mapper.setTargetType(BCBSEntity.class);
		Map<String,PropertyEditor> editors = new HashMap<String,PropertyEditor>();
		editors.put("java.util.Date",propertyEditor());
		mapper.setCustomEditors(editors);
		return mapper;
	}
	
	/**
	 * date format editor
	 * @return
	 */
	@Bean
	public PropertyEditor propertyEditor() {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-mm-dd"), true);
		return editor;
	}
	
	/**
	 * configuration of writer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Bean
	public ItemWriter<BCBSEntity> writer() {
		JpaItemWriter<BCBSEntity> writer = new JpaItemWriter<BCBSEntity>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}
	
	/**
	 * job registry to support the servlet component
	 * @return
	 */
	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
		JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
		postProcessor.setJobRegistry(jobRegistry);
		return postProcessor;
	}
	
}
