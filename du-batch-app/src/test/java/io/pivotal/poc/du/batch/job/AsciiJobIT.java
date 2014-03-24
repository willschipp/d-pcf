package io.pivotal.poc.du.batch.job;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import io.pivotal.poc.du.batch.Application;
import io.pivotal.poc.du.batch.domain.BCBSEntityRepository;

import java.io.FileWriter;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes=Application.class)
public class AsciiJobIT {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private BCBSEntityRepository repository;
	
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper;
	
	@Before
	public void before() {
		mockMvc = webAppContextSetup(context).build();
		objectMapper = new ObjectMapper();
	}
	
	@After
	public void after() {
		repository.deleteAllInBatch();//clean up
	}
	
	@Test
	public void test() throws Exception {
		//launch the system --> automatic
		//write a file to the target specified directory
		FileWriter file = new FileWriter("/tmp/data/source.ascii");
		file.write("10000,1234567890,001,2014-03-23,2014-03-23,100.00");
		file.write("\n");
		file.flush();
		file.close();
		//watch it execute
		Thread.sleep(1000);
		//query through restful service
		MvcResult result = mockMvc.perform(get("/jobs/{jobName}/instances","insertJob").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		List<JobInstance> jobInstances = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List>() {
		});
		assertTrue(jobInstances.size() == 1);
		//retrieve all executions
		result = mockMvc.perform(get("/jobs/{jobName}/executions","insertJob").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();		
		List<JobExecution> jobExecutions = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List>() {});
		assertTrue(jobExecutions.size() == 1);
		//check in the database
		assertTrue(repository.count() >= 1);
	}

}
