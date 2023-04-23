package com.upaxer.serverless.models.domain;



import lombok.Data;

@Data
public class RequestData {

	private String to;
    private String subject;
    private String body;
	public RequestData(String to, String subject, String body) {
		super();
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
    
    
}
