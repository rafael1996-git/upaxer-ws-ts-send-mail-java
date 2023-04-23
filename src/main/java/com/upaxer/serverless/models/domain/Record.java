package com.upaxer.serverless.models.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Record {
	@JsonProperty("messageId")
	private String messageId;
	@JsonProperty("body")
	private String body;
}
