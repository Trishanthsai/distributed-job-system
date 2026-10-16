package com.distributed.jobprocessing.exception;

/**
 * Exception thrown when a requested job cannot be found in the store.
 */
public class JobNotFoundException extends RuntimeException {

    private final Long jobId;

    public JobNotFoundException(Long jobId) {
        super("Job not found with id: " + jobId);
        this.jobId = jobId;
    }

    public Long getJobId() {
        return jobId;
    }
}
