package com.distributed.jobprocessing.model;

/**
 * Represents the lifecycle statuses of a job in the processing system.
 */
public enum JobStatus {
    QUEUED,
    PROCESSING,
    COMPLETED,
    FAILED
}
