package io.pivotal.poc.du.batch.domain;

import java.util.Date;

import org.springframework.batch.core.JobExecution;

public class JobExecutionWrapper {

	private Date createTime;
	
	private Date endTime;
	
	private Date lastUpdated;
	
	private Date startTime;
	
	private Long id;
	
	private String exitStatus;
	
	private String batchStatus;
	
	public JobExecutionWrapper(JobExecution jobExecution) {
		this.createTime = jobExecution.getCreateTime();
		this.endTime = jobExecution.getEndTime();
		this.lastUpdated = jobExecution.getLastUpdated();
		this.startTime = jobExecution.getStartTime();
		this.id = jobExecution.getId();
		this.exitStatus = jobExecution.getExitStatus().getExitCode();
		this.batchStatus = jobExecution.getStatus().name();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Long getId() {
		return id;
	}

	public String getExitStatus() {
		return exitStatus;
	}

	public String getBatchStatus() {
		return batchStatus;
	}
	
}
