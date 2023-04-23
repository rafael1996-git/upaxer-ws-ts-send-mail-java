package com.upaxer.serverless.utilities.enums;

public enum CodeResponse {
    FAILED_TO_CONNECT_TO_DB(13L, "Error al conectar a BD.", "500", "Error Interno."),
    FAILED_TO_GET_DATA(13L, "Error al obtener datos de BD.", "500", "Error Interno."),
    FAILED_TO_SAVE_DATA(13L, "Error al guardar datos de BD.", "500", "Error Interno.");

    CodeResponse(
        Long id,
        String message,
        String code,
        String description
    ) {
        this.id = id;
        this.message = message;
        this.code = code;
        this.description = description;
    }

    private Long id;
    private String message;
    private String code;
    private String description;

    public Long getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

}