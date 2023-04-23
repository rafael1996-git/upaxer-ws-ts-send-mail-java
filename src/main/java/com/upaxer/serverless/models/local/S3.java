package com.upaxer.serverless.models.local;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class S3 {
    @SerializedName("bucket")
    private Bucket bucket;

    @SerializedName("object")
    private ObjectData objectData;
}
