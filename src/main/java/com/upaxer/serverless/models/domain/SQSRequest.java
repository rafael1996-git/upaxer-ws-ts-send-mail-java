package com.upaxer.serverless.models.domain;

import java.util.List;

import com.google.gson.annotations.SerializedName;


import lombok.Data;
@Data
public class SQSRequest {
    @SerializedName("Records")
	private List<Record> records;


}
