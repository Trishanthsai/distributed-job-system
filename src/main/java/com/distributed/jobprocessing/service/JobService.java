package com.distributed.jobprocessing.service;

import com.distributed.jobprocessing.dto.CreateJobRequest;
import com.distributed.jobprocessing.dto.JobResponse;
import com.distributed.jobprocessing.exception.JobNotFoundException;
import com.distributed.jobprocessing.model.Job;
import com.distributed.jobprocessing.model.JobStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service managing job submission and retrieval using a thread-safe in-memory store.
 */
@Service
public class JobService {

    private final ConcurrentMap<Long, Job> jobStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Submits and registers a new job in QUEUED status.
     *
     * @param request the job submission payload
     * @return JobResponse containing job confirmation details
     */
    public JobResponse createJob(CreateJobRequest request) {
        long id = idGenerator.getAndIncrement();
        Instant now = Instant.now();

        Job job = new Job(
            id,
            request.getType(),
            request.getPayload(),
            JobStatus.QUEUED,
            now,
            now
        );

        jobStore.put(id, job);
        return JobResponse.accepted(job);
    }

    /**
     * Retrieves an existing job by ID.
     *
     * @param id the job ID
     * @return JobResponse containing the job details
     * @throws JobNotFoundException if no job exists with the given ID
     */
    public JobResponse getJobById(Long id) {
        Job job = jobStore.get(id);
        if (job == null) {
            throw new JobNotFoundException(id);
        }
        return JobResponse.from(job);
    }

    /**
     * Lists all submitted jobs sorted by job ID.
     *
     * @return list of JobResponse objects
     */
    public List<JobResponse> getAllJobs() {
        return jobStore.values().stream()
            .sorted(Comparator.comparing(Job::getJobId))
            .map(JobResponse::from)
            .toList();
    }

    /**
     * Resets the in-memory store (useful for clean state during testing).
     */
    public void resetStore() {
        jobStore.clear();
        idGenerator.set(1);
    }
}
