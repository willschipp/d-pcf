package io.pivotal.poc.du.batch.job;

import io.pivotal.poc.du.batch.domain.BCBSEntity;
import io.pivotal.poc.du.batch.domain.BCBSEntityRepository;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.batch.item.data.RepositoryItemWriter;
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

@Configuration
@EnableBatchProcessing
public class AsciiJob {

	@Autowired
	private BCBSEntityRepository bcbsEntityRepository;
	
	@Autowired
	private JobRegistry jobRegistry;
	
	@Bean
	public Job insertJob(JobBuilderFactory factory,Step step1) {
		return factory.get("insertJob").incrementer(new RunIdIncrementer()).flow(step1).end().build();
	}
	
	@Bean
	public Step step1(StepBuilderFactory factory,ItemReader<BCBSEntity> reader,ItemWriter<BCBSEntity> writer) {
		return factory.get("step1").<BCBSEntity,BCBSEntity> chunk(10).reader(reader).writer(writer).build();
	}
	
	@Bean
	@StepScope
	public FlatFileItemReader<BCBSEntity> reader(@Value("#{jobParameters['sourceFile']}") String source) throws Exception {
		FlatFileItemReader<BCBSEntity> reader = new FlatFileItemReader<BCBSEntity>();
		reader.setResource(new UrlResource(source));
		 reader.setLineMapper(new DefaultLineMapper<BCBSEntity>() {{
	            setLineTokenizer(new DelimitedLineTokenizer() {{
	                setNames(new String[] { "id", "msisdn","planId","createddate","updateddate","amount" });
	            }});
	            setFieldSetMapper(mapper());
	        }});
		return reader;
	}
	
	@Bean
	public FieldSetMapper mapper() {
		BeanWrapperFieldSetMapper<BCBSEntity> mapper = new BeanWrapperFieldSetMapper<BCBSEntity>();
		mapper.setTargetType(BCBSEntity.class);
		Map<String,PropertyEditor> editors = new HashMap<String,PropertyEditor>();
		editors.put("java.util.Date",propertyEditor());
		mapper.setCustomEditors(editors);
		return mapper;
	}
	
	@Bean
	public PropertyEditor propertyEditor() {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-mm-dd"), true);
		return editor;
	}
	
	@Bean
	public ItemWriter<BCBSEntity> writer() {
		RepositoryItemWriter writer = new RepositoryItemWriter();
		writer.setRepository(bcbsEntityRepository);
		writer.setMethodName("save");
		return writer;
	}
	
	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
		JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
		postProcessor.setJobRegistry(jobRegistry);
		return postProcessor;
	}
	
}
