package com.upaxer.serverless.models.local;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    @SerializedName("eventName")
    private String eventName;
    
    @SerializedName("s3")
    private S3 s3;
}
