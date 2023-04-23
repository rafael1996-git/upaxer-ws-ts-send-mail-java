package com.upaxer.serverless.models.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Request {

	private String to;
    private String subject;
    private String body;
}
