package io.pivotal.poc.du.batch.web;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class JobServiceEndpoint {

	@Autowired
	private JobExplorer jobExplorer;
	
	@Autowired
	private JobRegistry jobRegistry;
	
	@RequestMapping(method=RequestMethod.GET)
	public Collection<String> jobs() {
		return jobRegistry.getJobNames();
	}
	
	@RequestMapping(value="/{jobName}/running",method=RequestMethod.GET)
	public Set<JobExecution> running(@PathVariable("jobName") String jobName) {
		return jobExplorer.findRunningJobExecutions(jobName);
	}
	
	@RequestMapping(value="/{jobName}/instances",method=RequestMethod.GET)
	public List<JobInstance> jobInstances(@PathVariable("jobName") String jobName) {
		return jobExplorer.getJobInstances(jobName, 0, Integer.MAX_VALUE);
	}
	
	@RequestMapping(value="/{id}/execution",method=RequestMethod.GET)
	public JobExecution getJobExecution(@PathVariable("id") Long executionId) {
		return jobExplorer.getJobExecution(executionId);
	}
}
