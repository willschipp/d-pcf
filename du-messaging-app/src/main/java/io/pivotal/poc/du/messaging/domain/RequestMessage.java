package io.pivotal.poc.du.messaging.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RequestMessage implements Serializable {

	@Id
	private String id;
	
	@Column
	private String source;
	
	@Column
	private String to;
	
	@Column
	private String payload;
	
	public RequestMessage() { }
	
	public RequestMessage(String payload) {
		super();
		this.payload = payload;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	
}
