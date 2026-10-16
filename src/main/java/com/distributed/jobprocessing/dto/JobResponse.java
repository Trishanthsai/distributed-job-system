package com.distributed.jobprocessing.dto;

import com.distributed.jobprocessing.model.Job;
import com.distributed.jobprocessing.model.JobStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Map;

/**
 * Standard response object for job operations.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobResponse {

    private Long jobId;
    private String type;
    private JobStatus status;
    private String message;
    private Map<String, Object> payload;
    private Instant createdAt;
    private Instant updatedAt;

    public JobResponse() {
    }

    public JobResponse(Long jobId, String type, JobStatus status, String message) {
        this.jobId = jobId;
        this.type = type;
        this.status = status;
        this.message = message;
    }

    public JobResponse(Long jobId, String type, JobStatus status, String message,
                       Map<String, Object> payload, Instant createdAt, Instant updatedAt) {
        this.jobId = jobId;
        this.type = type;
        this.status = status;
        this.message = message;
        this.payload = payload;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Factory method for creating an acceptance response upon job submission.
     */
    public static JobResponse accepted(Job job) {
        return new JobResponse(
            job.getJobId(),
            job.getType(),
            job.getStatus(),
            "Job accepted"
        );
    }

    /**
     * Factory method for creating a full job representation for retrieval and listing.
     */
    public static JobResponse from(Job job) {
        return new JobResponse(
            job.getJobId(),
            job.getType(),
            job.getStatus(),
            null,
            job.getPayload(),
            job.getCreatedAt(),
            job.getUpdatedAt()
        );
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
