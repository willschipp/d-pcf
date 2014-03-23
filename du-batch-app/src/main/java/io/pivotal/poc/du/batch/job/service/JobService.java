package io.pivotal.poc.du.batch.job.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	
	public void start(String payload) throws Exception {
		//create job parameters
		JobParameters jobParameters = new JobParametersBuilder().addString("sourceFile", "file:///Users/wschipp/development/workspace-du/du-batch-app/src/test/resources/sample.ascii").toJobParameters();
		//start the job
		JobExecution jobExecution = jobLauncher.run(job, jobParameters);
	}
	
}
