package com.distributed.jobprocessing.model;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

/**
 * Domain model representing a submitted job.
 */
public class Job {

    private Long jobId;
    private String type;
    private Map<String, Object> payload;
    private JobStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public Job() {
    }

    public Job(Long jobId, String type, Map<String, Object> payload, JobStatus status, Instant createdAt, Instant updatedAt) {
        this.jobId = jobId;
        this.type = type;
        this.payload = payload != null ? Collections.unmodifiableMap(payload) : Collections.emptyMap();
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload != null ? Collections.unmodifiableMap(payload) : Collections.emptyMap();
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
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
