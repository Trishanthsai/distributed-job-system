package com.distributed.jobprocessing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

/**
 * Request payload for creating a new job.
 */
public class CreateJobRequest {

    @NotBlank(message = "Job type is required and cannot be blank")
    private String type;

    @NotNull(message = "Payload is required and cannot be null")
    private Map<String, Object> payload;

    public CreateJobRequest() {
    }

    public CreateJobRequest(String type, Map<String, Object> payload) {
        this.type = type;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }
}
