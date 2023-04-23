package com.upaxer.serverless.models.local;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Correo {
    @SerializedName("asunto")
    private String asunto;
    @SerializedName("cuerpo")
    private String cuerpo;
}
